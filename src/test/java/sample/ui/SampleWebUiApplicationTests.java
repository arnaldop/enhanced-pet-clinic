/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Basic integration tests for demo application.
 *
 * @author Dave Syer
 * @author Arnaldo Piccinelli
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleWebUiApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SampleWebUiApplicationTests extends BaseTests {

	@After
	public void cleanSession() throws Exception {
		ResponseEntity<String> page = sendRequest("http://localhost:" + this.port, HttpMethod.GET);

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("_csrf", csrfValue);
		httpHeaders.set("X-CSRF-TOKEN", csrfValue);

		page = sendRequest("http://localhost:" + this.port + "/logout", HttpMethod.GET, form);

		assertEquals(HttpStatus.FOUND, page.getStatusCode());

		if (page.getStatusCode() == HttpStatus.FOUND) {
			page = sendRequest(page.getHeaders().getLocation(), HttpMethod.GET);
		}

		httpHeaders = null;
		csrfValue = null;
	}

	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> page = sendRequest("http://localhost:" + this.port, HttpMethod.GET);

		assertEquals(HttpStatus.OK, page.getStatusCode());
		assertTrue("Wrong body (title doesn't match):\n" + page.getBody(),
				page.getBody().contains(getMessageBundleText("app.title")));
		assertTrue("Wrong body (did not find heading):\n" + page.getBody(),
				page.getBody().contains(getMessageBundleText("welcome")));
	}

	@Test
	public void testLoginPage() throws Exception {
		ResponseEntity<String> page = sendRequest("http://localhost:" + this.port + "/login", HttpMethod.GET);

		assertEquals(HttpStatus.OK, page.getStatusCode());
		assertTrue("Wrong content:\n" + page.getBody(), page.getBody().contains("_csrf"));
	}

	@Test
	public void testLogin() throws Exception {
		executeLogin("user", "user");
	}

	@Test
	public void testVeterinariansPage() throws Exception {
		ResponseEntity<String> page = executeLogin("user", "user");

		page = getPage("http://localhost:" + this.port + "/vets/list.html");

		assertEquals(HttpStatus.OK, page.getStatusCode());

		String body = page.getBody();
		assertNotNull("Page body is null.", body);
		assertTrue("Wrong page", body.contains("<h2>Veterinarians</h2>"));

		String vetList = body.substring(body.indexOf("<tbody>"));
		vetList = vetList.substring(0, vetList.indexOf("</tbody>"));
		assertTrue("Missing expected data.", StringUtils.countMatches(vetList, "<tr>") == 6);
	}

	@Test
	public void testCreateUserAndLogin() throws Exception {
		ResponseEntity<String> page = getPage("http://localhost:" + this.port + "/users");

		assertTrue("Client or server error.",
				!page.getStatusCode().is4xxClientError() && !page.getStatusCode().is5xxServerError());

		if (page.getStatusCode() == HttpStatus.FOUND) {
			page = getPage(page.getHeaders().getLocation());
		}

		String body = page.getBody();
		assertNotNull("Body was null", body);

		String username = "newuser";
		String password = "password";
		String formAction = getFormAction(page);

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("username", username);
		form.set("email", "newuser@newuser.org");
		form.set("name", "New User");
		form.set("uiPassword", password);
		form.set("verifyPassword", password);
		form.set("_eventId_saveUser", "Create User");
		form.set("_csrf", csrfValue);

		httpHeaders.set("X-CSRF-TOKEN", csrfValue);

		page = postPage(formAction, form);

		if (page.getStatusCode() == HttpStatus.FOUND) {
			page = getPage(page.getHeaders().getLocation());
		}

		assertEquals(HttpStatus.OK, page.getStatusCode());
		body = page.getBody();
		assertNotNull("Body was null", body);
		assertTrue("User not created:\n" + body, body.contains("User " + username + " saved"));

		executeLogin(username, password);
	}

	@Test
	public void testCss() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate()
				.getForEntity("http://localhost:" + this.port + "/css/main.css", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertTrue("Wrong body:\n" + entity.getBody(), entity.getBody().contains("body"));
	}
}
