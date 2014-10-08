/*
 * Copyright 2002-2014 the original author or authors.
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
package sample.ui.config;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Class containing all security methods and beans.
 *
 * @author Arnaldo Piccinelli
 */
@Configuration
@EnableWebMvcSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MultiHttpSecurityConfig {

    private static final String[] UNSECURED_RESOURCE_LIST = new String[] { "/test.html", "/",
            "/resources/**", "/assets/**", "/css/**", "/webjars/**", "/images/**",
            "/dandelion-assets/**", "/unauthorized", "/error*" };

    @Configuration
    @Profile({ "intdb" })
    protected static class InMemoryPersistentTokenRememberMeSetup {
        @Value("${rememberMeToken}")
        private String rememberMeToken;

        @Value("${rememberMeParameter}")
        private String rememberMeParameter;

        @Bean
        public RememberMeServices getRememberMeServices() {
            PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices(
                    rememberMeToken, new BasicRememberMeUserDetailsService(),
                    new InMemoryTokenRepositoryImpl());
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
    @Profile({ "extdb" })
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
    @Profile({ "intdb" })
    protected static class InternalAuthenticationSecurity extends
            GlobalAuthenticationConfigurerAdapter {
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            //@formatter:off
            auth
                .inMemoryAuthentication()
                    .withUser("user").password("password")
                        .roles("USER")
                .and()
                    .withUser("admin").password("password")
                        .roles("USER", "ADMIN")
            ;
            //@formatter:on
        }
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    @Profile({ "extdb" })
    protected static class ExternalAuthenticationSecurity extends
            GlobalAuthenticationConfigurerAdapter {
        @Autowired
        private DataSource dataSource;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            //@formatter:off
            JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
            userDetailsService.setDataSource(dataSource);
            PasswordEncoder encoder = new BCryptPasswordEncoder();

            auth
                .userDetailsService(userDetailsService)
                    .passwordEncoder(encoder);
            auth
                .jdbcAuthentication()
                    .dataSource(dataSource);
            //@formatter:on
        }
    }

    @Configuration
    @Order(1)
    @Profile({ "live" })
    public static class ClosedActuatorWebSecurityConfigurationAdapter extends
            WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .antMatcher("/manage/**")
                    .authorizeRequests()
                        .anyRequest()
                            .hasRole("ADMIN")
                .and()
                    .httpBasic();
            //@formatter:on
        }
    }

    @Configuration
    @Order(1)
    @Profile({ "test" })
    public static class OpenActuatorWebSecurityConfigurationAdapter extends
            WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .antMatcher("/manage/**")
                    .authorizeRequests()
                        .anyRequest()
                            .permitAll()
                .and()
                    .httpBasic();
            //@formatter:on
        }
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Value("${rememberMeToken}")
        private String rememberMeToken;

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
                    .httpStrictTransportSecurity()
                    .frameOptions()
                    .xssProtection()
                .and()
                    .authorizeRequests()
                        .anyRequest()
                            .authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .permitAll()
                .and()
                    .rememberMe()
//                        .useSecureCookie(true)
                        .tokenValiditySeconds(60 * 60 * 24 * 10) // 10 days
                        .rememberMeServices(rememberMeServices)
                        .key(rememberMeToken)
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                .and()
                    .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    // TODO change to maxSessionsPreventLogin
                    // .maxSessionsPreventsLogin(true)
                    .expiredUrl("/login?expired");
            // @formatter:on
        }
    }
}
