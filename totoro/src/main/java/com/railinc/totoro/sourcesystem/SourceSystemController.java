package com.railinc.totoro.sourcesystem;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.web.FlashMessages;

@Controller
@RequestMapping("/admin/sourcesystem")
public class SourceSystemController {

	@Autowired
	private SourceSystemService service;
	
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model, @RequestParam(value="q",required=false) String q) {
		model.addAttribute("results", service.all(q));
		return "sourcesystem/list";
	}
	
	@RequestMapping(value="/new",method=RequestMethod.GET)
	public String newForm(Model model) {
		model.addAttribute("sourcesystem", new SourceSystemForm());
		return "sourcesystem/new";
	}

	@RequestMapping(value="/new",method=RequestMethod.POST,params="_cancel")
	public String cancelNewForm(HttpServletRequest request, @ModelAttribute("sourcesystem") @Valid SourceSystemForm form, BindingResult result) {
		return "redirect:list";
	}
	

	@RequestMapping(value="/new",method=RequestMethod.POST,params="_save")
	public String submitNewForm(HttpServletRequest request, @ModelAttribute("sourcesystem") @Valid SourceSystemForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "sourcesystem/new";
		}
		
		SourceSystem ss = new SourceSystem();
		ss.setIdentifier(form.getId());
		ss.setName(form.getName());
		service.save(ss);
		
		FlashMessages.add(request, "sourcesystem.succesfullyadded", 
				new Object[]{ss.getIdentifier(), ss.getName()}, 
				"Successfully added the Source System");

		
		return "redirect:list";
	}


	@RequestMapping(value="/{id}/delete",method=RequestMethod.GET)
	public String delete(HttpServletRequest request, Model model, @PathVariable("id") String id) {
		SourceSystem ss = this.service.get(id);
		this.service.delete(ss);
		
		FlashMessages.add(request, "sourcesystem.succesfullydeleted", 
				new Object[]{ss.getIdentifier(), ss.getName()}, 
				"Successfully deleted the Source System");

		
		
		return "redirect:../list";
	}
	
	@RequestMapping(value="/{id}/undelete",method=RequestMethod.GET)
	public String undelete(HttpServletRequest request, Model model, @PathVariable("id") String id) {
		SourceSystem ss = this.service.get(id);
		this.service.undelete(ss);
		
		FlashMessages.add(request, "sourcesystem.succesfullydeleted", 
				new Object[]{ss.getIdentifier(), ss.getName()}, 
				"Successfully deleted the Source System");

		
		
		return "redirect:../list";
	}
	

	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String editForm(Model model, @PathVariable("id") String id) {
		SourceSystem ss = this.service.get(id);
		model.addAttribute("sourcesystem", new SourceSystemForm(ss));
		return "sourcesystem/edit";
	}
	
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.POST,params="_cancel")
	public String cancelEditForm(HttpServletRequest request, @ModelAttribute("sourcesystem") @Valid SourceSystemForm form, BindingResult result, @PathVariable("id") String id) {
		return "redirect:list";
	}

	@RequestMapping(value="/{id}",method=RequestMethod.POST,params="_save")
	public String submitEditForm(HttpServletRequest request, @ModelAttribute("sourcesystem") @Valid SourceSystemForm form, BindingResult result, @PathVariable("id") String id) {
		SourceSystem ss = this.service.get(id);
		ss.setName(form.getName());
		this.service.save(ss);
		
		FlashMessages.add(request, "sourcesystem.succesfullyupdated", 
				new Object[]{ss.getIdentifier(), ss.getName()}, 
				"Successfully updated the Source System");

		return "redirect:list";
	}

}
