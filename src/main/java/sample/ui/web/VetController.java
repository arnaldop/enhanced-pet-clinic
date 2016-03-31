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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import sample.ui.model.Vets;
import sample.ui.service.ClinicService;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Arnaldo Piccinelli
 */
@Controller
@RequestMapping("/vets")
@Slf4j
public class VetController {

	@Autowired
	private ClinicService clinicService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showVets(Model model) {
		return new ModelAndView("redirect:/vets/list.html", model.asMap());
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String showVetList(Model model) {
		// Here we are returning an object of type 'Vets' rather than a
		// collection of Vet objects so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		model.addAttribute("vets", vets);

		log.info("In showVetList: " + model);

		return "vets/vetList";
	}
}
