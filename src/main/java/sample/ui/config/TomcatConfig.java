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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

/**
 * Configuration class that allows for Tomcat access via HTTP (test only) and
 * HTTPS.
 *
 * @author Arnaldo Piccinelli
 */
@Configuration
@EnableConfigurationProperties
public class TomcatConfig {

	@Profile({ "test" })
	public static class MultiTomcatConfig {

		@Value("${ssl.keystore.file}")
		private String sslKeystoreFile;

		@Value("${ssl.keystore.password}")
		private String sslKeystorePassword;

		@Value("${ssl.keystore.type}")
		private String sslKeystoreType;

		@Value("${ssl.keystore.alias}")
		private String sslKeystoreAlias;

		@Value("${tls.port}")
		private int tlsPort;

		@Bean
		public EmbeddedServletContainerFactory getServletContainer() {
			TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
			tomcat.addAdditionalTomcatConnectors(createSslConnector());
			return tomcat;
		}

		private Connector createSslConnector() {
			Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
			Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
			try {
				connector.setScheme("https");
				connector.setSecure(true);
				connector.setPort(tlsPort);

				File keystore = getKeyStoreFile();
				File truststore = keystore;

				protocol.setSSLEnabled(true);
				protocol.setKeystoreFile(keystore.getAbsolutePath());
				protocol.setKeystorePass(sslKeystorePassword);
				protocol.setTruststoreFile(truststore.getAbsolutePath());
				protocol.setTruststorePass(sslKeystorePassword);
				protocol.setKeyAlias(sslKeystoreAlias);
				return connector;
			} catch (IOException ex) {
				throw new IllegalStateException(
						"can't access keystore: [" + "keystore" + "] or truststore: [" + "keystore" + "]", ex);
			}
		}

		private File getKeyStoreFile() throws IOException {
			ClassPathResource resource = new ClassPathResource(sslKeystoreFile);
			try {
				return resource.getFile();
			} catch (Exception ex) {
				File temp = File.createTempFile("keystore", ".tmp");
				FileCopyUtils.copy(resource.getInputStream(), new FileOutputStream(temp));
				return temp;
			}
		}

	}
}
