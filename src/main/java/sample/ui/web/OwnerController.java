/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.ui.web;

import java.util.Collection;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import sample.ui.model.Owner;
import sample.ui.service.ClinicService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Arnaldo Piccinelli
 */
@Controller
@SessionAttributes(types = Owner.class)
@RequestMapping(value = "/owners")
public class OwnerController {

	@Autowired
	private ClinicService clinicService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String initCreationForm(Model model) {
		Owner owner = new Owner();
		model.addAttribute(owner);
		return "owners/ownerForm";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String processCreationForm(@Valid Owner owner, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return "owners/ownerForm";
		} else {
			this.clinicService.saveOwner(owner);
			status.setComplete();
			return "redirect:/owners/" + owner.getId();
		}
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String initFindForm(Model model) {
		model.addAttribute("owner", new Owner());
		return "owners/findOwners";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showOwners(Model model) {
		return new ModelAndView("redirect:/owners/list.html", model.asMap());
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String processFindForm(Owner owner, BindingResult result, Model model, HttpSession session) {
		Collection<Owner> results = null;

		// find owners by last name
		if (StringUtils.isEmpty(owner.getLastName())) {
			// allow parameterless GET request for /owners to return all records
			results = this.clinicService.findOwners();
		} else {
			results = this.clinicService.findOwnerByLastName(owner.getLastName());
		}

		if (results.size() < 1) {
			// no owners found
			result.rejectValue("lastName", "notFound", new Object[] { owner.getLastName() }, "not found");
			return "owners/findOwners";
		}

		session.setAttribute("searchLastName", owner.getLastName());

		if (results.size() > 1) {
			// multiple owners found
			model.addAttribute("owners", results);
			return "owners/ownersList";
		} else {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}
	}

	@RequestMapping(value = "/{ownerId}/edit", method = RequestMethod.GET)
	public String initUpdateOwner(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.clinicService.findOwnerById(ownerId);
		model.addAttribute(owner);
		return "owners/ownerForm";
	}

	@RequestMapping(value = "/{ownerId}/edit", method = RequestMethod.POST)
	public String processUpdateOwner(@Valid Owner owner, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return "owners/ownerForm";
		} else {
			this.clinicService.saveOwner(owner);
			status.setComplete();
			return "redirect:/owners/{ownerId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param ownerId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping("/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(this.clinicService.findOwnerById(ownerId));
		return mav;
	}
}
