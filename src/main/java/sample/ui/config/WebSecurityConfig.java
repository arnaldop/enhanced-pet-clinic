package sample.ui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties security;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off

//		http
//			.authorizeRequests()
//				.antMatchers("/", "/css/**", "/webjars/**", "/images/**", "/logout", "/dandelion-assets/**")
//					.permitAll()
//				.antMatchers("/vets/**").hasRole("ADMIN")
//				.antMatchers("/vets*").hasRole("ADMIN")
//				.antMatchers("/owners/**").hasRole("USER")
////				.anyRequest().hasRole("USER")
////				.anyRequest()
////					.authenticated()
//				.anyRequest().fullyAuthenticated()
//			.and()
//				.formLogin()
//					.loginPage("/login")
//						.failureUrl("/login?error")
//						.permitAll()
//			.and()
//				.logout()
//					.permitAll()
//					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//					.logoutSuccessUrl("/login?logout=success")
////					.logoutUrl("/logout.html")
//				
		;

//        http
//        	.authorizeRequests()
//            	.antMatchers("/", "/home").permitAll()
//            	.anyRequest().authenticated();

//        http
//        	.formLogin()
//            	.loginPage("/login")
//            	.permitAll()
//            .and()
//            .logout()
//            	.permitAll();
    
    // http
		// .authorizeRequests()
		// .antMatchers("/resources/**", "/signup", "/about").permitAll()
		// .antMatchers("/admin/**").hasRole("ADMIN")
		// .antMatchers("/db/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_DBA')")
		// .anyRequest().authenticated()
		// .and()
		// // ...
		// .formLogin();
		//@formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		//@formatter:off
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password")
					.roles("USER")
			.and()
				.withUser("admin").password("password")
					.roles("ADMIN")
		;
		//@formatter:on
	}
}
