/*
 * Copyright 2012-2014 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext
public class SampleWebUiApplicationTests {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testHome() throws Exception {
        ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + this.port, String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue("Wrong body (title doesn't match):\n" + entity.getBody(), entity
                .getBody().contains("<title>PetClinic"));
        assertTrue("Wrong body (did not find heading):\n" + entity.getBody(), entity
                .getBody().contains("<h2>Welcome</h2>"));
    }

    @Test
    public void testLoginPage() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        ResponseEntity<String> entity = new TestRestTemplate().exchange(
                "http://localhost:" + this.port + "/login", HttpMethod.GET,
                new HttpEntity<Void>(headers), String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue("Wrong content:\n" + entity.getBody(),
                entity.getBody().contains("_csrf"));
    }

    @Test
    public void testLogin() throws Exception {
        executeLogin("user", "password");
    }

    private void executeLogin(String username, String password) throws Exception {
        HttpHeaders headers = getHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("username", username);
        form.set("password", password);

        ResponseEntity<String> entity = new TestRestTemplate().exchange(
                "http://localhost:" + this.port + "/login", HttpMethod.POST,
                new HttpEntity<MultiValueMap<String, String>>(form, headers),
                String.class);
        assertEquals(HttpStatus.FOUND, entity.getStatusCode());
        assertTrue("Wrong location:\n" + entity.getHeaders(), entity.getHeaders()
                .getLocation().toString().endsWith(port + "/"));
        assertNotNull("Missing cookie:\n" + entity.getHeaders(),
                entity.getHeaders().get("Set-Cookie"));
    }

    @Test
    public void testCreateUserAndLogin() throws Exception {
        // get Create User page
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        String url = "http://localhost:" + this.port + "/users";

        ResponseEntity<String> entity = new TestRestTemplate().exchange(
                url, HttpMethod.GET, new HttpEntity<Void>(headers), String.class);

        if (entity.getStatusCode() == HttpStatus.FOUND) {
            entity = new TestRestTemplate().exchange(
                entity.getHeaders().getLocation(), HttpMethod.POST,
                new HttpEntity<Void>(headers), String.class);
        }
        String body = entity.getBody();
        assertNotNull("Body was null", body);

        String actionHTML = "<form action=\"";
        String username = "newuser";
        String password= "password";
        String formAction = body.substring(body.indexOf(actionHTML) + actionHTML.length());
        formAction = formAction.substring(0, formAction.indexOf("\""));

        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("username", username);
        form.set("email", "newuser@newuser.org");
        form.set("name", "New User");
        form.set("uiPassword", "password");
        form.set("verifyPassword", "password");
        form.set("_eventId_saveUser", "Create User");

        entity = new TestRestTemplate().exchange(
                "http://localhost:" + this.port + formAction, HttpMethod.POST,
                new HttpEntity<MultiValueMap<String, String>>(form, headers),
                String.class);

        if (entity.getStatusCode() == HttpStatus.FOUND) {
            entity = new TestRestTemplate().exchange(
                entity.getHeaders().getLocation(), HttpMethod.POST,
                new HttpEntity<Void>(headers), String.class);
        }

        if (entity.getStatusCode() == HttpStatus.FOUND) {
            entity = new TestRestTemplate().exchange(
                entity.getHeaders().getLocation(), HttpMethod.POST,
                new HttpEntity<Void>(headers), String.class);
        }

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        body = entity.getBody();
        assertNotNull("Body was null", body);
        assertTrue("User not created:\n" + body,
                body.contains("User " + username + " saved"));

        executeLogin(username, password);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> page = new TestRestTemplate().getForEntity(
                "http://localhost:" + this.port + "/login", String.class);
        assertEquals(HttpStatus.OK, page.getStatusCode());
        String cookie = page.getHeaders().getFirst("Set-Cookie");
        headers.set("Cookie", cookie);
        Matcher matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*")
                .matcher(page.getBody());
        assertTrue("No csrf token: " + page.getBody(), matcher.matches());
        headers.set("X-CSRF-TOKEN", matcher.group(1));
        return headers;
    }

    @Test
    public void testCss() throws Exception {
        ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + this.port + "/css/camptracker.css", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue("Wrong body:\n" + entity.getBody(), entity.getBody().contains("body"));
    }
}
