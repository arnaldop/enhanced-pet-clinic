package sample.ui.config;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.github.dandelion.core.web.DandelionFilter;
import com.github.dandelion.core.web.DandelionServlet;
import com.github.dandelion.datatables.thymeleaf.dialect.DataTablesDialect;
import com.github.dandelion.thymeleaf.dialect.DandelionDialect;

@Component
@Configuration
public class DandelionConfig {

    // @Bean
    // public ServletContextTemplateResolver templateResolver() {
    // ServletContextTemplateResolver resolver = new
    // ServletContextTemplateResolver();
    // resolver.setPrefix("/WEB-INF/views/");
    // resolver.setSuffix(".html");
    // resolver.setTemplateMode("HTML5");
    // resolver.setCacheable(false);
    // return resolver;
    // }

    // @Bean
    // public SpringTemplateEngine templateEngine() {
    // SpringTemplateEngine engine = new SpringTemplateEngine();
    // engine.setTemplateResolver(templateResolver());
    // engine.addDialect(new DandelionDialect());
    // engine.addDialect(new DataTablesDialect());
    // return engine;
    // }

    // @Bean
    // public ThymeleafViewResolver thymeleafViewResolver() {
    // ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    // resolver.setTemplateEngine(templateEngine());
    // return resolver;
    // }

    @Bean
    public DataTablesDialect dataTablesDialect() {
        return new DataTablesDialect();
    }

    @Bean
    public DandelionDialect dandelionDialect() {
        return new DandelionDialect();
    }

    @Bean
    public FilterRegistrationBean dandelionFilterRegistrationBean() {
        return new FilterRegistrationBean(new DandelionFilter());
    }

    @Bean
    public ServletRegistrationBean dandelionServletRegistrationBean() {
        return new ServletRegistrationBean(new DandelionServlet(),
                "/dandelion-assets/*");
    }
}
