package sample.ui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 11)
//@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] UNSECURED_RESOURCE_LIST = new String[] {"/", "/resources/**", "/assets/**", "/css/**", "/webjars/**", "/images/**", "/dandelion-assets/**", "/unauthorized", "/error*"};

    @Autowired
    private SecurityProperties security;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
                .antMatchers(UNSECURED_RESOURCE_LIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .authorizeRequests()
                .antMatchers(UNSECURED_RESOURCE_LIST)
                    .permitAll()
                .antMatchers("/owners/**", "/vets/**", "/vets*").hasRole("USER")
                .antMatchers("/manage/**").hasRole("ADMIN")
//                .anyRequest().hasRole("USER")
//                .anyRequest()
//                    .authenticated()
                .anyRequest()
//                    .anonymous()
                    .permitAll()
//                .anyRequest()
//                    .hasAuthority("BASIC_PERMISSION")
                    //.fullyAuthenticated()
                    //.permitAll()
            .and()
                .formLogin()
                    .loginPage("/login")
                        .failureUrl("/login?error")
                        .permitAll()
            .and()
                .logout()
//                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll()
//            .and()
//                .rememberMe()
//                    .rememberMeServices(rememberMeServices()).key("password")
            .and()
                .rememberMe()
//                    .rememberMeServices(rememberMeServices())
//                        .key("password")
            .and()
                .requiresChannel()
                    .antMatchers("/login", "/owners/**", "/vets/**", "/vets*", "/manage/**")
                        .requiresSecure()
            .and()
                .exceptionHandling()
                    .accessDeniedPage("/router?q=unauthorized")
            .and()
                .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredUrl("/login?expired")
            ;

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

//    @Bean
//    public RememberMeServices rememberMeServices() {
//        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices();//"password", userDetailsService);
//        rememberMeServices.setCookieName("cookieName");
//        rememberMeServices.setParameter("rememberMe");
//        return rememberMeServices;
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //Configures form login
//                .formLogin()
//                    .loginPage("/login")
//                    .loginProcessingUrl("/login/authenticate")
//                    .failureUrl("/login?error=bad_credentials")
//                //Configures the logout function
//                .and()
//                    .logout()
//                        .deleteCookies("JSESSIONID")
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                //Configures url based authorization
//                .and()
//                    .authorizeRequests()
//                        //Anyone can access the urls
//                        .antMatchers(
//                                "/auth/**",
//                                "/login",
//                                "/signin/**",
//                                "/signup/**",
//                                "/user/register/**"
//                        ).permitAll()
//                        //The rest of the our application is protected.
//                        .antMatchers("/**").hasRole("USER")
//                //Adds the SocialAuthenticationFilter to Spring Security's filter chain.
//                .and()
//                    .apply(new SpringSocialConfigurer());
//    }

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
                    .roles("USER", "ADMIN")
        ;
        //@formatter:on
    }

    // @Profile("security")
//    @ConditionalOnExpression('${ssl.enabled:false}')
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer(
//            @Value("${ssl.keystore.file}") final String keystoreFile,
//            @Value("${ssl.keystore.pass}") final String keystorePassword,
//            @Value("${ssl.keystore.type}") final String keystoreType,
//            @Value("${ssl.keystore.alias}") final String keystoreAlias)
//            throws FileNotFoundException {
//
//        final String absoluteKeystoreFile = ResourceUtils.getFile(keystoreFile)
//                .getAbsolutePath();
//
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer factory) {
//                if (factory instanceof TomcatEmbeddedServletContainerFactory) {
//                    TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
//                    containerFactory
//                            .addConnectorCustomizers(new TomcatConnectorCustomizer() {
//                                @Override
//                                public void customize(Connector connector) {
//                                    connector.setPort(8443);
//                                    connector.setSecure(true);
//                                    connector.setScheme("https");
//                                    connector.setProtocol("TLS");
//
//                                    Http11NioProtocol proto = (Http11NioProtocol) connector
//                                            .getProtocolHandler();
//                                    proto.setSSLEnabled(true);
//                                    proto.setClientAuth("false");
//                                    proto.setKeystoreFile(absoluteKeystoreFile);
//                                    proto.setKeystorePass(keystorePassword);
//                                    proto.setKeystoreType(keystoreType);
//                                    proto.setKeyAlias(keystoreAlias);
//                                }
//                            });
//                }
//            }
//        };
//    }
}
