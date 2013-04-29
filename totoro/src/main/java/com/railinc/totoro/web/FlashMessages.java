package com.railinc.totoro.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

public class FlashMessages {

		
		public static final String FLASH_MESSAGE_SESSION_ATTR = "org.springframework.validation.BindingResult.flash";

		public static final void add(HttpServletRequest request, String errorCode, Object[] errorArgs, String defaultMessage) {
			HttpSession session = request.getSession();
			
			
			BindingResult obj = (BindingResult) session.getAttribute("org.springframework.validation.BindingResult.flash");
			if (obj == null) {
				obj = new BeanPropertyBindingResult(new Object(), "flash");
			}
			obj.reject(errorCode, errorArgs, defaultMessage);
			session.setAttribute("org.springframework.validation.BindingResult.flash", obj);
		}


	
	
}
