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
package sample.ui.config;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Class containing all security methods and beans.
 *
 * @author Arnaldo Piccinelli
 */
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MultiHttpSecurityConfig {

	private static final String[] UNSECURED_RESOURCE_LIST = new String[] { "/resources/**", "/assets/**", "/css/**",
			"/webjars/**", "/images/**", "/dandelion/**", "/js/**" };

	private static final String[] UNAUTHORIZED_RESOURCE_LIST = new String[] { "/test.html", "/", "/unauthorized*",
			"/error*", "/users*", "/accessDenied" };

	@Configuration
	@Profile({ "dev" })
	protected static class InMemoryPersistentTokenRememberMeSetup {
		@Value("${rememberMeToken}")
		private String rememberMeToken;

		@Value("${rememberMeParameter}")
		private String rememberMeParameter;

		@Bean
		public RememberMeServices getRememberMeServices() {
			PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices(
					rememberMeToken, new BasicRememberMeUserDetailsService(), new InMemoryTokenRepositoryImpl());
			services.setParameter(rememberMeParameter);
			return services;
		}

		public class BasicRememberMeUserDetailsService implements UserDetailsService {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return new User(username, "", Collections.<GrantedAuthority> emptyList());
			}
		}
	}

	@Configuration
	@Profile({ "test", "live" })
	protected static class JdbcPersistentTokenRememberMeSetup {
		@Value("${rememberMeToken}")
		private String rememberMeToken;

		@Value("${rememberMeParameter}")
		private String rememberMeParameter;

		@Autowired
		private DataSource dataSource;

		@Bean
		public RememberMeServices getRememberMeServices() {
			JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
			jdbcUserDetailsManager.setDataSource(dataSource);

			JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
			jdbcTokenRepositoryImpl.setDataSource(dataSource);

			PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices(
					rememberMeToken, jdbcUserDetailsManager, jdbcTokenRepositoryImpl);
			services.setParameter(rememberMeParameter);
			return services;
		}
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	protected static class ExternalAuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {
		@Autowired
		private DataSource dataSource;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			//@formatter:off
			String authoritiesByUsernameQuery = "select username, authority from user_authorities " +
					"inner join users on user_authorities.user_id = users.id " +
					"inner join authorities on user_authorities.authority_id = authorities.id " +
					"where username = ?";

			JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
			userDetailsService.setDataSource(dataSource);
			userDetailsService.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			auth
				.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder)
				.and()
					.jdbcAuthentication()
						.authoritiesByUsernameQuery(authoritiesByUsernameQuery)
						.passwordEncoder(passwordEncoder)
						.dataSource(dataSource)
			;
			//@formatter:on
		}
	}

	@Configuration
	@Order(1)
	@Profile({ "live" })
	public static class LiveWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Value("${rememberMeToken}")
		private String rememberMeToken;

		@Value("${spring.profiles.active}")
		private String activeProfile;

		@Autowired
		RememberMeServices rememberMeServices;

		@Override
		public void configure(WebSecurity web) throws Exception {
			//@formatter:off
			web
				.ignoring()
					.antMatchers(UNSECURED_RESOURCE_LIST);
			//@formatter:on
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//@formatter:off
			http
				.headers()
					.frameOptions()
						.sameOrigin()
			.and()
				.authorizeRequests()
					.antMatchers(UNAUTHORIZED_RESOURCE_LIST)
						.permitAll()
					.antMatchers("/git", "/manage", "/manage/**")
						.hasRole("ADMIN")
					.anyRequest()
						.authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
			.and()
				.headers()
					.cacheControl()
				.and()
					.frameOptions()
						.deny()
			.and()
				.exceptionHandling()
					.accessDeniedPage("/access?error")
			.and()
				.rememberMe()
					.useSecureCookie(true)
					.tokenValiditySeconds(60 * 60 * 24 * 10) // 10 days
					.rememberMeServices(rememberMeServices)
					.key(rememberMeToken)
			.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/?logout")
			.and()
				.sessionManagement()
					.maximumSessions(1)
					.expiredUrl("/login?expired");
			// @formatter:on
		}
	}

	@Configuration
	@Order(1)
	@Profile({ "dev", "test" })
	public static class NonLiveWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Value("${rememberMeToken}")
		private String rememberMeToken;

		@Value("${spring.profiles.active}")
		private String activeProfile;

		@Autowired
		RememberMeServices rememberMeServices;

		@Override
		public void configure(WebSecurity web) throws Exception {
			//@formatter:off
			web
				.ignoring()
					.antMatchers(UNSECURED_RESOURCE_LIST);
			//@formatter:on
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//@formatter:off
			http
				.headers()
					.frameOptions()
						.sameOrigin()
			.and()
				.authorizeRequests()
					.antMatchers(UNAUTHORIZED_RESOURCE_LIST)
						.permitAll()
					.antMatchers("/git", "/manage", "/manage/**")
						.permitAll()
					.anyRequest()
						.authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
			.and()
				.headers()
					.cacheControl()
				.and()
					.frameOptions()
						.deny()
			.and()
				.exceptionHandling()
					.accessDeniedPage("/access?error")
			.and()
				.rememberMe()
					.useSecureCookie(true)
					.tokenValiditySeconds(60 * 60 * 24 * 10) // 10 days
					.rememberMeServices(rememberMeServices)
					.key(rememberMeToken)
			.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/?logout")
			.and()
				.sessionManagement()
					.maximumSessions(1)
					.expiredUrl("/login?expired");
			// @formatter:on
		}
	}

	// Register HttpSessionEventPublisher
	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
}
