package sample.ui.config;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
// @Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	// @Bean
	// public Filter dandelionFilter() {
	// return new DandelionFilter();
	// }
	//
	// // @Bean
	// // public FilterRegistrationBean dandelionFilterRegistrationBean() {
	// // return new FilterRegistrationBean(new DandelionFilter());
	// // }
	//
	// @Bean
	// public ServletRegistrationBean dandelionServletRegistrationBean() {
	// return new ServletRegistrationBean(new DandelionServlet(),
	// "/dandelion-assets/*");
	// }
}
