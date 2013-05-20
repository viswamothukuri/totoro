package com.railinc.totoro.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.railinc.totoro.web.FlashMessages;



/**
 * @author sdtxs01
 *
 */
@Controller
@RequestMapping("/support/logging")
public class LoggingController extends SupportControllerBaseImpl 
	implements MessageSourceAware {

	private MessageSource messageSource;

	@RequestMapping("update")
	public String updateLogging(HttpServletRequest request, @RequestParam(value="n",required=true) String name,
			@RequestParam(value="l",required=true) String level, ModelMap map) {
		
		if (StringUtils.isNotBlank(name)) {
			Logger logger = "root".equals(name) ? 
					LogManager.getRootLogger() : 
						LogManager.getLogger(name);
			if (logger != null) {
				logger.setLevel(Level.toLevel(level));
				FlashMessages.add(request, "support.logging.controller.level.updated", 
						new Object[]{name,level}, 
						"Successfully updated");
			}
		}
		return logging(map);
	}

	@RequestMapping
	public String landing(ModelMap model) {
		model.clear();
		return "redirect:/support/logging/list";
	}
	
	@RequestMapping("list")
	public String logging(ModelMap map) {
		Enumeration<?> currentLoggers = LogManager.getCurrentLoggers();
		ArrayList<Logger> arrayList = new ArrayList<Logger>();
		while (currentLoggers.hasMoreElements()) {
			arrayList.add((Logger) currentLoggers.nextElement());
		}
		
		Collections.sort(arrayList, 
				new Comparator<Logger>() {
					public int compare(Logger o1, Logger o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
		arrayList.add(0,LogManager.getRootLogger());
		map.addAttribute("currentLoggers", arrayList);
		return "support/logging";
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
		
	}
}
