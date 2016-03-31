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
package sample.ui.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Class used to house all aspect-related method.
 *
 * @author arnaldopiccinelli
 */
@Aspect
@EnableAspectJAutoProxy
@Component
@Configuration
@Slf4j
public class LoggingAspectAnnotationDriven {

	@Before("execution(* sample.ui.web.GeneralController.handleException(..))")
	public void beforeExceptionHandler(JoinPoint jp) {
		log.info("(beforeExceptionHandler) Before executing '" + jp.getSignature().toLongString() + "'");
	}

	@After("execution(* sample.ui.web.GeneralController.handleException(..))")
	public void afterExceptionHandler(JoinPoint jp) {
		log.info("(afterExceptionHandler) Before executing '" + jp.getSignature().toLongString() + "'");
	}

	@Before("execution(* sample.ui.config.MultiHttpSecurityConfig.*.*(..))")
	public void beforeMultiHttpSecurityConfigSubClasses(JoinPoint jp) {
		log.info("(beforeMultiHttpSecurityConfigSubClasses) Before executing '" + jp.getSignature().toLongString()
				+ "'");
	}

	@Before("execution(* sample.ui.config.MultiHttpSecurityConfig.*(..))")
	public void beforeMultiHttpSecurityConfig(JoinPoint jp) {
		log.info("(beforeMultiHttpSecurityConfig) Before executing '" + jp.getSignature().toLongString() + "'");
	}

	@Before("execution(* sample.ui.SampleWebUiApplication.*(..))")
	public void beforeSampleWebUiApplication(JoinPoint jp) {
		log.info("(beforeSampleWebUiApplication) Before executing '" + jp.getSignature().toLongString() + "'");
	}

	@After("execution(* sample.ui.SampleWebUiApplication.*(..))")
	public void afterSampleWebUiApplication(JoinPoint jp) {
		log.info("(afterSampleWebUiApplication) After executing '" + jp.getSignature().toLongString() + "'");
	}

	@Before("execution(* sample.ui.service.ClinicServiceImpl.*(..))")
	public void beforeClinicServiceImpl(JoinPoint jp) {
		log.info("(beforeClinicServiceImpl) Before executing '" + jp.getSignature().toLongString() + "'");
	}

	@After("execution(* sample.ui.service.ClinicServiceImpl.*(..))")
	public void afterClinicServiceImpl(JoinPoint jp) {
		log.info("(afterClinicServiceImpl) After executing '" + jp.getSignature().toLongString() + "'");
	}

	// @Before("execution(* sample.ui.web.Pet*.*(..))")
	// public void beforePet(JoinPoint jp) {
	// log.info("(beforePet) PET Before executing '" +
	// jp.getSignature().toLongString() + "'");
	// }
	//
	// @After("execution(* sample.ui.web.Pet*.*(..))")
	// public void afterPet(JoinPoint jp) {
	// log.info("(afterPet) PET After executing '" +
	// jp.getSignature().toLongString() + "'");
	// }
	//
	// @Before("execution(* sample.ui.web.*.*(*))")
	// public void beforeAdviceWeb(JoinPoint jp) {
	// log.info("(beforeAdviceWeb) Before executing '" +
	// jp.getSignature().toLongString() + "'");
	// }
	//
	// @After("execution(* sample.ui.web.*.*(*))")
	// public void afterAdviceWeb(JoinPoint jp) {
	// log.info("(afterAdviceWeb) After executing '" +
	// jp.getSignature().toLongString() + "'");
	// }
	//
	// @Before("execution(* sample.ui.service.*.*(*))")
	// public void beforeAdviceService(JoinPoint jp) {
	// log.info("(beforeAdviceService) Before executing '" +
	// jp.getSignature().toLongString() + "'");
	// }
	//
	// @After("execution(* sample.ui.service.*.*(*))")
	// public void afterAdviceService(JoinPoint jp) {
	// log.info("(afterAdviceService) After executing '" +
	// jp.getSignature().toLongString() + "'");
	// }
	//
	// @Before("execution(* sample.ui.config.*.*(*))")
	// public void beforeAdviceConfig(JoinPoint jp) {
	// log.info("(beforeAdviceConfig) Before executing '" +
	// jp.getSignature().toLongString() + "'");
	// }
	//
	// @After("execution(* sample.ui.config.*.*(*))")
	// public void afterAdviceConfig(JoinPoint jp) {
	// log.info("(afterAdviceConfig) After executing '" +
	// jp.getSignature().toLongString() + "'");
	// }

}
