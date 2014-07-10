package sample.ui.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

@Component
@Configuration
public class WebXmlConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {

		// servletContext.addFilter("dandelionFilter", DandelionFilter.class)
		// .addMappingForUrlPatterns(null, false, "/*");
		//
		// // Register the Dandelion servlet
		// ServletRegistration.Dynamic dandelionServlet = servletContext
		// .addServlet("dandelionServlet", new DandelionServlet());
		// dandelionServlet.setLoadOnStartup(2);
		// dandelionServlet.addMapping("/dandelion-assets/*");

		// // Register the Dandelion filter
		// FilterRegistration.Dynamic dandelionFilter =
		// servletContext.addFilter("dandelionFilter", new DandelionFilter());
		// dandelionFilter.addMappingForUrlPatterns(null, false, "/*");
		//
		// // Register the Dandelion servlet
		// ServletRegistration.Dynamic dandelionServlet =
		// servletContext.addServlet("dandelionServlet", new
		// DandelionServlet());
		// dandelionServlet.setLoadOnStartup(2);
		// dandelionServlet.addMapping("/dandelion-assets/*");
	}

}
