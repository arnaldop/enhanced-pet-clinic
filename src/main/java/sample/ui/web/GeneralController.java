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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller that handles the home page and exceptional cases.
 *
 * @author Arnaldo Piccinelli
 */
@Controller
@ControllerAdvice
@Slf4j
public class GeneralController {

	@RequestMapping("/")
	public String index() {
		return "welcome";
	}

	@RequestMapping("/router")
	public String accessDeniedRouter(@RequestParam("q") String resource) {
		log.debug("In accessDeniedRouter resource = " + resource);

		return "redirect:/" + resource;
	}

	@RequestMapping("/unauthorized")
	public ModelAndView accessDenied() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("timestamp", new Date());
		mav.setViewName("unauthorized");
		return mav;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest req, Exception e) {
		log.warn("In handleException", e);

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("timestamp", new Date());
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("exception");
		return mav;
	}

	@RequestMapping(value = "/oups", method = RequestMethod.GET)
	public String triggerException() {
		throw new RuntimeException("Expected: controller used to showcase what happens when an exception is thrown");
	}
}
