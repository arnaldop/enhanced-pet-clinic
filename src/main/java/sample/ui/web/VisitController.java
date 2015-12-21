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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import sample.ui.model.Pet;
import sample.ui.model.Visit;
import sample.ui.service.ClinicService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes("visit")
public class VisitController {

	@Autowired
	private ClinicService clinicService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/owners/*/pets/{petId}/visits/new", method = RequestMethod.GET)
	public String initNewVisitForm(@PathVariable("petId") int petId, Model model) {
		Pet pet = this.clinicService.findPetById(petId);
		Visit visit = new Visit();
		pet.addVisit(visit);
		model.addAttribute("visit", visit);
		return "pets/visitForm";
	}

	@RequestMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new", method = RequestMethod.POST)
	public String processNewVisitForm(@Valid Visit visit, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return "pets/visitForm";
		} else {
			this.clinicService.saveVisit(visit);
			status.setComplete();
			return "redirect:/owners/{ownerId}";
		}
	}

	@RequestMapping(value = "/owners/*/pets/{petId}/visits", method = RequestMethod.GET)
	public ModelAndView showVisits(@PathVariable int petId) {
		ModelAndView mav = new ModelAndView("visitList");
		mav.addObject("visits", this.clinicService.findPetById(petId).getVisits());
		return mav;
	}

}
