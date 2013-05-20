package com.railinc.totoro.support;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.management.Attribute;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Throwables;


@Controller
public class JmxController extends SupportControllerBaseImpl {
	/**
	 * invokes an operation on a jmx bean
	 * @param response
	 * @param op the operation to perform
	 * @param name the name of hte jmx object
	 * @param params the params to pass to the operation
	 * @param sig the types of the operation
	 * @throws Exception
	 */
	@RequestMapping(value="/support/jmx/invoke",method=RequestMethod.POST)
	public void executeOperation(
			Model model,
			HttpServletResponse response, 
			@RequestParam(value="op", required=true) final String op,
			@RequestParam(value="n",defaultValue="",required=false) String name,
			@RequestParam(value="p",required=false) String[] params,
			@RequestParam(value="sig",required=false) String[] sig) throws Exception{

		String output = null;
		
		try {

			final ObjectName nm = new ObjectName(name);
			final MBeanServer server = MBeanServerFactory.findMBeanServer(null).get(0);
			MBeanInfo mBeanInfo = server.getMBeanInfo(nm);

			// find the matching operation
			final MBeanOperationInfo opinfo = match(
					new Function<MBeanOperationInfo, MBeanOperationInfo>() {
						public MBeanOperationInfo apply(MBeanOperationInfo i) throws Exception {
							String operationName = i.getName();
							return operationName.equals(op) ? i : null;
						}},	mBeanInfo.getOperations());

			
			// convert the inbound string params to the appropriate
			// object types. sorry for the fancy 'each' method....
			final MBeanParameterInfo[] paraminfo = opinfo.getSignature();
			final Object[] xformedParams = new Object[params == null ? 0 : params.length];

			// applys the function over each string parameter
			each(new IndexedFunction<String, Object>() {
				public Object apply(int idx, String object) throws Exception {
					return xformedParams[idx] = convertToType(paraminfo[idx].getType(), object);
				}
			}, params);

			output = humanConsumableString(server.invoke(nm, op, xformedParams, sig));
			
			if (opinfo.getReturnType().equals("void")) {
				output = "Success!";
			}
			
		} catch (Exception e) {
			if (e.getCause() != null) {
				output = e.getCause().toString();
			} else {
				output = e.toString();
			}
		} 
		PrintWriter writer = response.getWriter();
		writer.write(output);
		writer.close();
	}
	
	

	@SuppressWarnings({ "unused", "rawtypes" })
	private String humanConsumableString(Object invoke) {
		if (invoke == null) {
			return ("(null)");
		}

		if (invoke.getClass().isArray()) {
			Class arrayClass = invoke.getClass();
			Object[] arr = (Object[]) invoke;
			List<String> arrayList = new ArrayList<String>(arr.length);
			for (int i=0;i < arr.length;i++) {
				arrayList.add(humanConsumableString(arr[i]));
			}
			return arrayList.toString();
		}
		if (invoke.getClass().isPrimitive()) {
			return invoke.toString();
		}
		if (invoke.getClass().getPackage() == String.class.getPackage()) {
			return invoke.toString();
		}
		if (invoke.getClass().getPackage() == Map.class.getPackage()) {
			return invoke.toString();
		}
		
		String out = ToStringBuilder.reflectionToString(invoke,ToStringStyle.MULTI_LINE_STYLE);
		out = out.replaceAll("\\n", "<br/>");
		return out;

	}



	private <T> T match(Function<T, T> function, T ... against) throws Exception {
		T ret = null;
		for (T t : against) {
			ret = function.apply(t);
			if (ret != null) break;
		}
		return ret;
	}
	
	
	/**
	 * Updates a JMX attribute
	 * @param response
	 * @param attribute
	 * @param value
	 * @param name
	 * @throws Exception
	 */
	@RequestMapping(value="/support/jmx",method=RequestMethod.POST)
	public void updateValue(
			Model model,			
			HttpServletResponse response, 
			@RequestParam(value="element_id", required=true) final String attribute,
			@RequestParam(value="update_value", required=true) final String value,
			@RequestParam(value="n",defaultValue="",required=false) String name) throws Exception{
		
		String output = null;
		
		try {
			final ObjectName nm = new ObjectName(name);
			final MBeanServer server = MBeanServerFactory.findMBeanServer(null).get(0);
			final ObjectInstance object = server.getObjectInstance(nm);

			// applies the function to attribute that matches the name given in 'attribute'
			output = String.valueOf(matchAttributeByName(server.getMBeanInfo(nm).getAttributes(), 
					attribute, 
					new Function<MBeanAttributeInfo, Object>() {
						@Override
						public Object apply(MBeanAttributeInfo i) throws Exception {
							Object v = convertToType(i.getType(), value);
							if (v == null) {
								throw new Exception("Unable to convert " + value);
							}
							server.setAttribute(object.getObjectName(),new Attribute(attribute, v));
							return v;
						}
					}));
		} catch (Exception e) {
			output = e.getMessage();
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(output);
		writer.close();
	}
	/**
	 * GET - loads up the object and domains and puts them in the model
	 * @param model
	 * @param name
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	@RequestMapping(value="/support/jmx",method=RequestMethod.GET)
	public String domain(Model model, @RequestParam(value="n",defaultValue="",required=false) String name) throws MalformedObjectNameException, NullPointerException {
		if (StringUtils.isBlank(name)) {
			name="railinc:*";
		}

		MBeanServer server = MBeanServerFactory.findMBeanServer(null).get(0);
		ObjectName nm = new ObjectName(name);
		
		try {
			MBeanInfo mBeanInfo = server.getMBeanInfo(new ObjectName(name));
			model.addAttribute("mbean", mBeanInfo);
			
			Collection<MBeanOperationInfo> operations = collect(
					new Function<MBeanOperationInfo, MBeanOperationInfo>() {
						public MBeanOperationInfo apply(MBeanOperationInfo opinfo) throws Exception {
							MBeanParameterInfo[] signature = opinfo.getSignature();
							if (signature == null) return opinfo;
							for (int i=0;i < signature.length;i++) {
								String t = signature[i].getType();
								if (isNotSimpleType(t)) {
									return null;	
								}
							}
							return opinfo;
						}
					}, mBeanInfo.getOperations());
			operations = collect(new Function<MBeanOperationInfo, MBeanOperationInfo>() {
				public MBeanOperationInfo apply(MBeanOperationInfo i) throws Exception {
					return (i.getReturnType().startsWith("java.io")) ? null : i;
				}
				
				},operations.toArray(new MBeanOperationInfo[0]));
			model.addAttribute("operations", operations);
		} catch (Exception e) {
		}
		
		model.addAttribute("name", nm);
		model.addAttribute("server",server);
		model.addAttribute("names",StringUtils.isNotBlank(name) ? sort(server.queryNames(nm,null)) : null);
		model.addAttribute("domains", server.getDomains());
		return "support/jmx";
	}
	
	

	private <T> Collection<T> collect(
			Function<T, T> function,
			T ... set) throws Exception {
		Collection<T> ts = new ArrayList<T>();
		for (int i=0;set != null && i<set.length;i++)  {
			T ret = function.apply(set[i]);
			if (ret != null) ts.add(ret);
		}
		return ts;
	}



	private Object matchAttributeByName(MBeanAttributeInfo[] attrs, String name, Function<MBeanAttributeInfo,Object> function) throws Exception {
		for (MBeanAttributeInfo attr : attrs) {
			if (name.equals(attr.getName())) {
				return function.apply(attr);
			}
		}
		return null;
	}
	
	private boolean isNotSimpleType(String jmxType) {
		return ! isSimpleType(jmxType);
	}	
	private boolean isSimpleType(String jmxType) {
		if ("boolean".equals(jmxType)) {
			return true;
		} else if ("int".equals(jmxType)) {
			return true;
		} else if ("long".equals(jmxType)) {
			return true;
		} else if ("java.lang.String".equals(jmxType)) {
			return true;
		} else if ("[Ljava.lang.String;".equals(jmxType)) { //String array
			return true;
		} else if ("java.lang.Long".equals(jmxType)) {
			return true;
		} else if ("java.util.Date".equals(jmxType)) {
			return true;
		} else {
			return false;
		}
	}
	private Object convertToType(String jmxType, String value) {
		if ("boolean".equals(jmxType)) {
			return Boolean.valueOf(value);
		} else if ("int".equals(jmxType)) {
			return Integer.valueOf(value);
		} else if ("long".equals(jmxType)) {
			return Long.valueOf(value);
		} else if ("java.lang.String".equals(jmxType)) {
			return value;
		} else if ("[Ljava.lang.String;".equals(jmxType)) { //String array
			return new String[]{value};
		} else if ("java.lang.Long".equals(jmxType)) {
			return Long.valueOf(value);
		} else if ("java.util.Date".equals(jmxType)) {
			try {
				return new SimpleDateFormat("yyyyMMdd").parse(value);
			} catch (ParseException e) {
				throw Throwables.propagate(e);
			}
		} else {
			return null;
		}
	}

	private interface Function<I,O> {
		O apply(I i) throws Exception;
	}
	private interface IndexedFunction<I,O> {
		O apply(int idx, I i) throws Exception;
	}
	private <I,O> void each(IndexedFunction<I, O> function, I ... set) throws Exception {
		for (int i=0;set != null && i<set.length;i++)  {
			function.apply(i, set[i]);
		}
	}

	private <T extends Comparable<T>> List<T> sort(Collection<T> queryNames) {
		List<T> tmp = new ArrayList<T>(queryNames);
		Collections.sort(tmp);
		return tmp;
	}	
}
