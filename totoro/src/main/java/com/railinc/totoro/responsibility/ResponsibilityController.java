package com.railinc.totoro.responsibility;

import static com.google.common.collect.Collections2.transform;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Function;
import com.railinc.totoro.domain.IdentityType;
import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.sourcesystem.SourceSystemService;
import com.railinc.totoro.util.PagedCollection;
import com.railinc.totoro.util.WebFormConstants;
import com.railinc.totoro.web.FlashMessages;

@Controller
@RequestMapping("/admin/responsibility")
public class ResponsibilityController {

	@Autowired
	private ResponsibilityService service;
	
	@Autowired
	private SourceSystemService sourceSystemService;
	
	
	private static final Function<Responsibility, ResponsibilityForm> TO_FORM = new Function<Responsibility, ResponsibilityForm>() {
		@Override
		public ResponsibilityForm apply(Responsibility arg0) {
			ResponsibilityForm f = new ResponsibilityForm();
			f.setAuditData(arg0.getAuditData());
			f.setDeleted(arg0.isDeleted());
			f.setId(arg0.getId());
			f.setPerson(arg0.getResponsiblePersonId());
			f.setPersonType(arg0.getResponsiblePersonType());
			f.setRuleNumber(arg0.getRuleNumber());
			f.setPrecedence(arg0.getPrecedence());
			f.setSourceSystem(arg0.getSourceSystem());
			f.setVersion(arg0.getVersion());
			f.setNote(arg0.getNote().getText());
			return f;
		}
	};
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, WebFormConstants.timestampPropertyEditor());
		
		binder.registerCustomEditor(IdentityType.class, WebFormConstants.identityTypeEditor());
		
		binder.registerCustomEditor(SourceSystem.class, new PropertyEditorSupport() {
			
			@Override
			public String getAsText() {
				return String.valueOf(getValue());
			}

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(sourceSystemService.get(text));
			}
		});
	}
	
	@ModelAttribute("sourceSystems")
	public Collection<SourceSystem> sourceSystems() {
		return sourceSystemService.active();
	}
	
	@ModelAttribute("personTypes")
	public Collection<String> personTypes() {
		return transform(Arrays.asList(IdentityType.values()), new Function<IdentityType,String>(){
			@Override
			public String apply(IdentityType input) {
				return input.toString();
			}});
	}

	@RequestMapping(value="/list")
	public String list(Model model, @ModelAttribute("responsibilitysearch") @Valid ResponsibilitySearchForm form, BindingResult result) {
		PagedCollection<Responsibility> all = service.all(form.getCriteria());
		form.setResults(new PagedCollection<ResponsibilityForm>(transform(all, TO_FORM), all.getPaging()));
		
		
		return "responsibility/list";
	}
	
	@RequestMapping(value="/new",method=RequestMethod.GET)
	public String newForm(Model model) {
		model.addAttribute("responsibility", new ResponsibilityForm());
		return "responsibility/new";
	}

	@RequestMapping(value="/new",method=RequestMethod.POST,params="_cancel")
	public String cancelNewForm(HttpServletRequest request, @ModelAttribute("responsibility") @Valid ResponsibilityForm form, BindingResult result) {
		return "redirect:list";
	}
	

	@RequestMapping(value="/new",method=RequestMethod.POST,params="_save")
	public String submitNewForm(HttpServletRequest request, @ModelAttribute("responsibility") @Valid ResponsibilityForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "responsibility/new";
		}
		
		Responsibility ss = new Responsibility();
		ss.setResponsiblePersonId(form.getPerson());
		ss.setRuleNumberType(form.getRuleNumberType());
		ss.setRuleNumberFrom(form.getRuleNumberFrom());
		ss.setRuleNumberThru(form.getRuleNumberThru());
		ss.setPrecedence(form.getPrecedence());
		ss.setSourceSystem(form.getSourceSystem());
		ss.setResponsiblePersonType(form.getPersonType());
		ss.setNoteText(form.getNote());
		service.save(ss);
		
		FlashMessages.add(request, "responsibility.succesfullyadded", 
				new Object[]{ss.getId(), ss.getRuleNumber()}, 
				"Successfully added the Responsibility");

		
		return "redirect:list";
	}


	@RequestMapping(value="/{id}/delete",method=RequestMethod.GET)
	public String delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) {
		Responsibility ss = this.service.get(id);
		this.service.delete(ss);
		
		FlashMessages.add(request, "responsibility.succesfullydeleted", 
				new Object[]{ss.getId(), ss.getRuleNumber()}, 
				"Successfully deleted the Responsibility");

		
		
		return "redirect:../list";
	}
	
	@RequestMapping(value="/{id}/undelete",method=RequestMethod.GET)
	public String undelete(HttpServletRequest request, Model model, @PathVariable("id") Long id) {
		Responsibility ss = this.service.get(id);
		this.service.undelete(ss);
		
		FlashMessages.add(request, "responsibility.succesfullydeleted", 
				new Object[]{ss.getId(), ss.getRuleNumber()}, 
				"Successfully deleted the Responsibility");
		
		return "redirect:../list";
	}
	

	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String editForm(Model model, @PathVariable("id") Long id) {
		Responsibility ss = this.service.get(id);
		service.save(ss);
		model.addAttribute("responsibility", TO_FORM.apply(ss));
		return "responsibility/edit";
	}
	
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.POST,params="_cancel")
	public String cancelEditForm(HttpServletRequest request, @ModelAttribute("responsibility") @Valid ResponsibilityForm form, BindingResult result, @PathVariable("id") String id) {
		return "redirect:list";
	}

	@RequestMapping(value="/{id}",method=RequestMethod.POST,params="_save")
	public String submitEditForm(HttpServletRequest request, @Valid @ModelAttribute("responsibility") ResponsibilityForm form, BindingResult result, @PathVariable("id") Long id) {
		if (result.hasErrors()) {
			return "responsibility/edit";
		}
		
		Responsibility ss = this.service.get(id);
		ss.setSourceSystem(form.getSourceSystem());
		ss.setRuleNumberType(form.getRuleNumberType());
		ss.setRuleNumberFrom(form.getRuleNumberFrom());
		ss.setRuleNumberThru(form.getRuleNumberThru());
		ss.setPrecedence(form.getPrecedence());
		ss.setResponsiblePersonId(form.getPerson());
		ss.setResponsiblePersonType(form.getPersonType());
		ss.setNoteText(form.getNote());
		this.service.save(ss);
		
		FlashMessages.add(request, "responsibility.succesfullyupdated", 
				new Object[]{ss.getId(), ss.getRuleNumber()}, 
				"Successfully updated the Responsibility");

		return "redirect:list";
	}

}
