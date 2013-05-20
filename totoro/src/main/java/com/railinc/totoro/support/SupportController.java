package com.railinc.totoro.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/support")
public class SupportController extends SupportControllerBaseImpl  {
	
	/*
	@ModelAttribute("remlinks")
	public Collection<String> monitoredcomponents() {
		Map<String, MonitoredComponent> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(ctx, MonitoredComponent.class);
		return newArrayList(transform(beans.values(), 
				new Function<MonitoredComponent,String>(){
					@Override
					public String apply(MonitoredComponent from) {
						return from.getName();
					}}));
	}*/
	
	@RequestMapping("snoop")
	public String snoop(HttpServletRequest request) {
		return "support/snoop";
	}
	
	@RequestMapping
	public String list(Model map) {
		return "support/support";
	}
	
	@RequestMapping("properties")
	public String properties(ModelMap model) {
		model.addAttribute("properties", System.getProperties().entrySet());
		return "support/properties";
	}

}
