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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import sample.ui.model.Owner;
import sample.ui.model.Pet;
import sample.ui.model.PetType;
import sample.ui.service.ClinicService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Arnaldo Piccinelli
 */
@Controller
@SessionAttributes("pet")
public class PetController {

	@Autowired
	private ClinicService clinicService;

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.clinicService.findPetTypes();
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/owners/{ownerId}/pets/new", method = RequestMethod.GET)
	public String initCreationForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.clinicService.findOwnerById(ownerId);
		Pet pet = new Pet();
		owner.addPet(pet);
		model.addAttribute("pet", pet);
		return "pets/petForm";
	}

	@RequestMapping(value = "/owners/{ownerId}/pets/new", method = RequestMethod.POST)
	public String processCreationForm(@Valid Pet pet, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return "pets/petForm";
		} else {
			this.clinicService.savePet(pet);
			status.setComplete();
			return "redirect:/owners/{ownerId}";
		}
	}

	@RequestMapping(value = "/owners/*/pets/{petId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("petId") int petId, Model model) {
		Pet pet = this.clinicService.findPetById(petId);
		model.addAttribute("pet", pet);
		return "pets/petForm";
	}

	@RequestMapping(value = "/owners/{ownerId}/pets/{petId}/edit", method = RequestMethod.POST)
	public String processUpdateForm(@Valid Pet pet, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return "pets/petForm";
		} else {
			this.clinicService.savePet(pet);
			status.setComplete();
			return "redirect:/owners/{ownerId}";
		}
	}

}
