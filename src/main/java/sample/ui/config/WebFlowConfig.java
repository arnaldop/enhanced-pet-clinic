package sample.ui.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

import sample.ui.flow.UsersFlowHandler;

@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

	@Autowired
	private WebMvcConfig webMvcConfig;

	@Autowired
	private List<ViewResolver> viewResolvers;

	// @Autowired
	// private MvcViewFactoryCreator mvcViewFactoryCreator;

	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(flowRegistry()).addFlowExecutionListener(new SecurityFlowExecutionListener(), "*")
				.build();
	}

	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder(flowBuilderServices()).setBasePath("classpath*:/templates")
				.addFlowLocationPattern("/**/*-flow.xml").build();
	}

	@Bean
	public FlowBuilderServices flowBuilderServices() {
		return getFlowBuilderServicesBuilder().setViewFactoryCreator(mvcViewFactoryCreator())
				// .setValidator(validator())
				.setDevelopmentMode(true).build();
	}

	@Bean
	public MvcViewFactoryCreator mvcViewFactoryCreator() {
		viewResolvers.add(this.webMvcConfig.ajaxThymeleafViewResolver());

		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
		factoryCreator.setViewResolvers(viewResolvers);
		factoryCreator.setUseSpringBeanBinding(true);
		return factoryCreator;
	}

	// @Bean
	// public LocalValidatorFactoryBean validator() {
	// return new LocalValidatorFactoryBean();
	// }

	@Bean(name = "users/createUser")
	public UsersFlowHandler usersFlowHandler() {
		return new UsersFlowHandler();
	}
}
