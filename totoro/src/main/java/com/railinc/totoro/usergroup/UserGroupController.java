package com.railinc.totoro.usergroup;

import static com.google.common.collect.Collections2.transform;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Function;
import com.railinc.totoro.domain.UserGroup;
import com.railinc.totoro.domain.UserGroupMember;
import com.railinc.totoro.util.WebFormConstants;
import com.railinc.totoro.web.FlashMessages;

@Controller
@RequestMapping("/admin/usergroup")
public class UserGroupController {

	@Autowired
	private UserGroupService service;
	
	
	private static final Function<UserGroup, UserGroupForm> TO_FORM = new Function<UserGroup, UserGroupForm>() {
		@Override
		public UserGroupForm apply(UserGroup arg0) {
			UserGroupForm f = new UserGroupForm();
			f.setId(arg0.getIdentifier());
			f.setDeleted(arg0.isDeleted());
			f.setName(arg0.getName());
			f.setVersion(arg0.getVersion());
			f.setAuditData(arg0.getAuditData());
			f.setUserIds(arg0.getUserIds());
			return f;
		}
	};
	
	@InitBinder
	public void initBinder(WebDataBinder b) {
		b.registerCustomEditor(Date.class, WebFormConstants.timestampPropertyEditor());
	}
	

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model, @RequestParam(value="q",required=false) String q) {
		model.addAttribute("results", transform(service.all(q), TO_FORM));
		return "usergroup/list";
	}
	
	@RequestMapping(value="/new",method=RequestMethod.GET)
	public String newForm(Model model) {
		model.addAttribute("usergroup", new UserGroupForm());
		return "usergroup/new";
	}

	@RequestMapping(value="/new",method=RequestMethod.POST,params="_cancel")
	public String cancelNewForm(HttpServletRequest request, @ModelAttribute("usergroup") @Valid UserGroupForm form, BindingResult result) {
		return "redirect:list";
	}
	

	@RequestMapping(value="/new",method=RequestMethod.POST,params="_save")
	public String submitNewForm(HttpServletRequest request, @ModelAttribute("usergroup") @Valid UserGroupForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "usergroup/new";
		}
		
		UserGroup ss = new UserGroup();
		ss.setIdentifier(form.getId());
		ss.setName(form.getName());
		ss.setUserIds(form.getUserIds());
		service.save(ss);
		
		FlashMessages.add(request, "usergroup.succesfullyadded", 
				new Object[]{ss.getIdentifier(), ss.getName()}, 
				"Successfully added the User Group");

		
		return "redirect:list";
	}


	@RequestMapping(value="/{id}/delete",method=RequestMethod.GET)
	public String delete(HttpServletRequest request, Model model, @PathVariable("id") String id) {
		UserGroup ss = this.service.get(id);
		this.service.delete(ss);
		
		FlashMessages.add(request, "usergroup.succesfullydeleted", 
				new Object[]{ss.getIdentifier(), ss.getName()}, 
				"Successfully deleted the User Group");

		
		
		return "redirect:../list";
	}
	
	@RequestMapping(value="/{id}/undelete",method=RequestMethod.GET)
	public String undelete(HttpServletRequest request, Model model, @PathVariable("id") String id) {
		UserGroup ss = this.service.get(id);
		this.service.undelete(ss);
		
		FlashMessages.add(request, "usergroup.succesfullydeleted", 
				new Object[]{ss.getIdentifier(), ss.getName()}, 
				"Successfully deleted the User Group");

		
		
		return "redirect:../list";
	}
	

	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String editForm(Model model, @PathVariable("id") String id) {
		UserGroup ss = this.service.get(id);
		service.save(ss);
		model.addAttribute("usergroup", TO_FORM.apply(ss));
		return "usergroup/edit";
	}
	
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.POST,params="_cancel")
	public String cancelEditForm(HttpServletRequest request, @ModelAttribute("usergroup") @Valid UserGroupForm form, BindingResult result, @PathVariable("id") String id) {
		return "redirect:list";
	}

	@RequestMapping(value="/{id}",method=RequestMethod.POST,params="_save")
	public String submitEditForm(HttpServletRequest request, @ModelAttribute("usergroup") @Valid UserGroupForm form, BindingResult result, @PathVariable("id") String id) {
		if (result.hasErrors()) {
			return "usergroup/edit";
		}
		UserGroup ss = this.service.get(id);
		ss.setName(form.getName());
		ss.setUserIds(form.getUserIds());
		this.service.save(ss);
		
		FlashMessages.add(request, "usergroup.succesfullyupdated", 
				new Object[]{ss.getIdentifier(), ss.getName()}, 
				"Successfully updated the User Group");

		return "redirect:list";
	}

}
