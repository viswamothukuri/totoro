package com.railinc.totoro.support.messaging;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.railinc.totoro.integration.Queue;
import com.railinc.totoro.web.FlashMessages;

@Controller
@RequestMapping("/support/message")
public class MessageSubmissionController {

	@Autowired
	private Queue queue;

	@RequestMapping
	public String landing() {
		return "redirect:message/form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String showSubmissionForm(
			@ModelAttribute("form") MessageSubmissionForm form,
			BindingResult result) {
		return "support/message/form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST, params = "_cancel")
	public String cancelNewForm(HttpServletRequest request,
			@ModelAttribute("form") MessageSubmissionForm form,
			BindingResult result) {
		
		return "contextRelative:/";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST, params = "_submit")
	public String submitNewForm(HttpServletRequest request,
			@ModelAttribute("form") @Valid MessageSubmissionForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "support/message/form";
		}
		for (int i=0;i<form.getTimes();i++) {
			queue.sendMessage(form.getData());
		}

		FlashMessages.add(request, "support.message.successfullysent",new Object[]{form.getData(), form.getTimes()}, "Successfully sent the message.");

		return "support/message/form";
	}

}
