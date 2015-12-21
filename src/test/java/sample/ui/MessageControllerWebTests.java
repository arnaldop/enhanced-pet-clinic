/*
 * Copyright 2012-2014 the original author or authors.
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

package sample.ui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * A Basic Spring MVC Test for the Sample Controller"
 *
 * @author Biju Kunjummen
 * @author Doo-Hwan, Kwak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleWebUiApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class MessageControllerWebTests extends BaseTests {

	@Test
	public void testCreate() throws Exception {
		ResponseEntity<String> page = executeLogin("user", "user");
		page = getPage("http://localhost:" + this.port + "/messages?form");

		String body = page.getBody();
		assertNotNull("Body was null", body);

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("summary", "summary");
		form.set("messageText", "messageText");
		form.set("_csrf", csrfValue);

		String formAction = getFormAction(page);

		page = postPage(formAction, form);
		body = page.getBody();

		assertTrue("Error creating message.", body == null || body.contains("alert alert-danger"));
		assertTrue("Status was not FOUND (redirect), means message was not created properly.",
				page.getStatusCode() == HttpStatus.FOUND);

		page = getPage(page.getHeaders().getLocation());

		body = page.getBody();
		assertTrue("Error creating message.", body.contains("Successfully created a new message"));
	}
}
