/*
 * Copyright 2012-2013 the original author or authors.
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

import java.io.File;
import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import sample.ui.message.InMemoryMessageRespository;
import sample.ui.message.Message;
import sample.ui.message.MessageRepository;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class SampleWebUiApplication {

    @Bean
    public MessageRepository messageRepository() {
        return new InMemoryMessageRespository();
    }

    @Bean
    public Converter<String, Message> messageConverter() {
        return new Converter<String, Message>() {
            @Override
            public Message convert(String id) {
                return messageRepository().findMessage(Long.valueOf(id));
            }
        };
    }

    public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(
				SampleWebUiApplication.class);
		springApplication.addListeners(new ApplicationPidListener());
		springApplication.run(args);
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> getHttpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

//    @Profile("production")
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer(
//            @Value("${ssl.keystore.file}") final String keystoreFile,
//            @Value("${ssl.keystore.alias}") final String keystoreAlias,
//            @Value("${ssl.keystore.type}") final String keystoreType,
//            @Value("${ssl.keystore.pass}") final String keystorePass,
//            @Value("${tls.port}") final int tlsPort
//    ) {
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer factory) {
//                if(factory instanceof TomcatEmbeddedServletContainerFactory) {
//                    customizeTomcat((TomcatEmbeddedServletContainerFactory) factory);
//                }
//            }
//
//            public void customizeTomcat(TomcatEmbeddedServletContainerFactory factory) {
//                factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//                    @Override
//                    public void customize(Connector connector) {
//                        connector.setPort(tlsPort);
//                        connector.setSecure(true);
//                        connector.setScheme("https");
//                        connector.setAttribute("keyAlias", keystoreAlias);
//                        connector.setAttribute("keystorePass", keystorePass);
//                        try {
//                            connector.setAttribute("keystoreFile",
//                                ResourceUtils.getFile(keystoreFile).getAbsolutePath());
//                        } catch (FileNotFoundException e) {
//                            throw new IllegalStateException("Cannot load keystore", e);
//                        }
//                        connector.setAttribute("clientAuth", "false");
//                        connector.setAttribute("sslProtocol", "TLS");
//                        connector.setAttribute("SSLEnabled", true);
//                    }
//                });
//            }
//        };
//    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            File keystore = new ClassPathResource("ssl/keystore").getFile();
//            File truststore = new ClassPathResource("keystore").getFile();
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(8443);

            protocol.setSSLEnabled(true);
            protocol.setKeystoreFile(keystore.getAbsolutePath());
            protocol.setKeystorePass("password");
//            protocol.setTruststoreFile(truststore.getAbsolutePath());
//            protocol.setTruststorePass("changeit");
            protocol.setKeyAlias("camp5");
            return connector;
        }
        catch (IOException ex) {
            throw new IllegalStateException("can't access keystore: [" + "keystore"
                    + "] or truststore: [" + "keystore" + "]", ex);
        }
    }

//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer factory) {
//                if (factory instanceof TomcatEmbeddedServletContainerFactory) {
//                    TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
//                    containerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//
//                        @Override
//                        public void customize(Connector connector) {
//                            connector.setPort(tlsPort);
//                            connector.setSecure(true);
//                            connector.setScheme("https");
//                            connector.setAttribute("keyAlias", keystoreAlias);
//                            connector.setAttribute("keystorePass", keystorePass);
//                            String absoluteKeystoreFile;
//                            try {
//                                absoluteKeystoreFile = ResourceUtils.getFile(keystoreFile).getAbsolutePath();
//                                connector.setAttribute("keystoreFile", absoluteKeystoreFile);
//                            } catch (IOException e) {
//                                throw new IllegalStateException("Cannot load keystore", e);
//                            }
//                            connector.setAttribute("clientAuth", "false");
//                            connector.setAttribute("sslProtocol", "TLS");
//                            connector.setAttribute("SSLEnabled", true);
//
//                            Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
//                            proto.setSSLEnabled(true);
//                            // proto.setClientAuth();
//                            // uncomment this to require the
//                            // client to authenticate. Then, you can use X509 support in Spring Security
//                            proto.setKeystoreFile(absoluteKeystoreFile);
//                            proto.setKeystorePass(keystorePass);
//                            proto.setKeystoreType(keystoreType);
//                            proto.setKeyAlias(keystoreAlias);
//                        }
//                    });
//
//                }
//            }
//        };
//    }
}
