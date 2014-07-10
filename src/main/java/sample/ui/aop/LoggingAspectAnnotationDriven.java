package sample.ui.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectAnnotationDriven {

	private static Log logger = LogFactory
			.getLog(LoggingAspectAnnotationDriven.class);

	@Before("execution(* sample.ui.web..*.*(*))")
	public void beforeAdviceWeb(JoinPoint jp) {
		logger.info("Before executing '" + jp.getSignature().toLongString() + "'");
	}

	@After("execution(* sample.ui.web..*.*(*))")
	public void afterAdviceWeb(JoinPoint jp) {
		logger.info("After executing '" + jp.getSignature().toLongString() + "'");
	}

	@Before("execution(* sample.ui.service..*.*(*))")
	public void beforeAdviceService(JoinPoint jp) {
		logger.info("Before executing '" + jp.getSignature().toLongString() + "'");
	}

	@After("execution(* sample.ui.service..*.*(*))")
	public void afterAdviceService(JoinPoint jp) {
		logger.info("After executing '" + jp.getSignature().toLongString() + "'");
	}

	@Before("execution(* sample.ui.config..*.*(*))")
	public void beforeAdviceConfig(JoinPoint jp) {
		logger.info("Before executing '" + jp.getSignature().toLongString() + "'");
	}

	@After("execution(* sample.ui.config..*.*(*))")
	public void afterAdviceConfig(JoinPoint jp) {
		logger.info("After executing '" + jp.getSignature().toLongString() + "'");
	}

}
