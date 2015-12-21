package sample.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class BaseTests {
	protected static final String FORM_ACTION_PATTERN = "<form [^>]*action=\"([^\"]+).*";
	protected static final String CSRF_VALUE_PATTERN = "(?s).*name=\"_csrf\".*?value=\"([^\"]+).*";

	@Autowired
	private MessageSource messageSource;

	@Value("${local.server.port}")
	protected int port;

	protected String csrfValue = null;
	protected HttpHeaders httpHeaders = null;

	public String getMessageBundleText(String key) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, new Object[0], locale);
	}

	@Before
	public void setUpHeaders() {
		httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.TEXT_HTML));
	}

	protected ResponseEntity<String> getPage(String url) {
		ResponseEntity<String> page = new TestRestTemplate().exchange(url, HttpMethod.GET,
				new HttpEntity<Void>(httpHeaders), String.class);
		saveCSRFAndCookieValues(page);
		return page;
	}

	protected ResponseEntity<String> getPage(URI uri) {
		ResponseEntity<String> page = new TestRestTemplate().exchange(uri, HttpMethod.GET,
				new HttpEntity<Void>(httpHeaders), String.class);
		saveCSRFAndCookieValues(page);
		return page;
	}

	protected ResponseEntity<String> postPage(String formAction, MultiValueMap<String, String> form) {
		ResponseEntity<String> page = new TestRestTemplate().exchange("http://localhost:" + this.port + formAction,
				HttpMethod.POST, new HttpEntity<MultiValueMap<String, String>>(form, httpHeaders), String.class);
		saveCSRFAndCookieValues(page);
		return page;
	}

	protected ResponseEntity<String> executeLogin(String username, String password) throws Exception {
		String url = "http://localhost:" + this.port + "/login";

		ResponseEntity<String> page = sendRequest(url, HttpMethod.GET);
		saveCSRFAndCookieValues(page);

		if (page.getStatusCode() == HttpStatus.FOUND) {
			page = sendRequest(page.getHeaders().getLocation(), HttpMethod.POST);
		}
		String body = page.getBody();
		assertNotNull("Body was null", body);

		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("username", username);
		form.set("password", password);
		form.set("_csrf", csrfValue);

		page = sendRequest("http://localhost:" + this.port + "/login", HttpMethod.POST, form);

		assertEquals(HttpStatus.FOUND, page.getStatusCode());
		assertTrue("Login redirected to error page.", !page.getHeaders().getLocation().toString().contains("error"));

		return page;
	}

	protected void saveCSRFValue(ResponseEntity<String> page) {
		String pageBody = page.getBody();
		if (pageBody != null) {
			Matcher matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*").matcher(pageBody);
			if (matcher.find()) {
				csrfValue = matcher.group(1);
				return;
			}
		}
		csrfValue = null;
	}

	protected String getFormAction(ResponseEntity<String> page) {
		String pageBody = page.getBody();
		if (pageBody != null) {
			Matcher matcher = Pattern.compile(FORM_ACTION_PATTERN).matcher(pageBody);
			if (matcher.find()) {
				return matcher.group(1);
			}
		}
		return null;
	}

	protected void saveCookieValue(ResponseEntity<String> page) {
		String cookie = page.getHeaders().getFirst("Set-Cookie");
		if (StringUtils.isNotEmpty(cookie)) {
			httpHeaders.set("Cookie", cookie);
		}
	}

	protected void saveCSRFAndCookieValues(ResponseEntity<String> page) {
		saveCSRFValue(page);
		saveCookieValue(page);
	}

	protected ResponseEntity<String> sendRequest(URI uri, HttpMethod method) {
		ResponseEntity<String> page = new TestRestTemplate().exchange(uri, method, new HttpEntity<Void>(httpHeaders),
				String.class);
		saveCSRFAndCookieValues(page);

		return page;
	}

	protected ResponseEntity<String> sendRequest(String url, HttpMethod method) {
		ResponseEntity<String> page = new TestRestTemplate().exchange(url, method, new HttpEntity<Void>(httpHeaders),
				String.class);
		saveCSRFAndCookieValues(page);

		return page;
	}

	protected ResponseEntity<String> sendRequest(String url, HttpMethod method, MultiValueMap<String, String> form) {
		ResponseEntity<String> page = new TestRestTemplate().exchange(url, method,
				new HttpEntity<MultiValueMap<String, String>>(form, httpHeaders), String.class);
		saveCSRFAndCookieValues(page);

		return page;
	}
}
