/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sample.ui.message.InMemoryMessageRespository;
import sample.ui.message.Message;
import sample.ui.message.MessageRepository;

@Configuration
@ComponentScan
@Controller
@ControllerAdvice
@EnableAutoConfiguration
public class SampleWebUiApplication {

	@RequestMapping("/")
	String index() {
		return "welcome";
	}

	@Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRespository();
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest req, Exception e) {
		System.out.println("-------------------");
		// mappings.put("org.springframework.dao.DataAccessException",
		// "exception");
		// e.printStackTrace();
		// return "exception";

		System.out.println("Request: " + req.getRequestURL() + " raised " + e);

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("exception");
		return mav;
	}

	// @Bean
	// public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
	// SimpleMappingExceptionResolver b = new SimpleMappingExceptionResolver();
	// Properties mappings = new Properties();
	// mappings.put("org.springframework.dao.DataAccessException", "exception");
	// b.setExceptionMappings(mappings);
	// return b;
	// }

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleWebUiApplication.class, args);
	}

}
