package com.railinc.totoro.support.messaging;

import static com.google.common.collect.Collections2.transform;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Function;
import com.railinc.totoro.domain.RawInboundMessage;
import com.railinc.totoro.util.PagedCollection;
import com.railinc.totoro.web.FlashMessages;

@Controller
@RequestMapping("/support/message")
public class MessageController {

	private static final String VIEW_MESSAGE_SUBMISSION_FORM = "support/message/form";
	private static final String VIEW_MESSAGE_SEARCH_FORM = "support/message/list";
	
	@Autowired
	MessageService service;

	
	public static final String redirectToSearch() {
		return "redirect:message/list";
	}

	
	
	@RequestMapping
	public String landing() {
		return redirectToSearch();
	}
	
	
	private static final Function<RawInboundMessage,MessageForm> TO_FORM = new Function<RawInboundMessage, MessageForm>() {
		public MessageForm apply(RawInboundMessage m) {
			MessageForm f = new MessageForm();
			f.setAuditData(m.getAuditData());
			f.setData(m.getData());
			f.setIdentifier(m.getIdentifier());
			f.setProcessed(m.isProcessed());
			f.setSource(m.getSource().toString());
			return f;
		}
	};
	


	@RequestMapping(value = "/list")
	public String showSearchForm(
			@Valid
			@ModelAttribute MessageSearchForm form,
			BindingResult result) {
		PagedCollection<RawInboundMessage> all = this.service.all(form.getCriteria());
		form.setResults(new PagedCollection<MessageForm>(transform(all, TO_FORM), all.getPaging()));
		return VIEW_MESSAGE_SEARCH_FORM;
	}
	
	

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String showSubmissionForm(
			@ModelAttribute MessageSubmissionForm form,
			BindingResult result) {
		return VIEW_MESSAGE_SUBMISSION_FORM;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "_cancel")
	public String cancelNewForm(HttpServletRequest request,
			@ModelAttribute("form") MessageSubmissionForm form,
			BindingResult result) {
		
		return redirectToSearch();
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "_submit")
	public String submitNewForm(HttpServletRequest request,
			@ModelAttribute @Valid MessageSubmissionForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return VIEW_MESSAGE_SUBMISSION_FORM;
		}
		for (int i=0;i<form.getTimes();i++) {
			service.sendMessage(form.getData());
		}

		FlashMessages.add(request, "support.message.successfullysent",new Object[]{form.getData(), form.getTimes()}, "Successfully sent the message.");

		return VIEW_MESSAGE_SUBMISSION_FORM;
	}

	

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		MessageSearchForm.initBinder(binder);
	}
}
