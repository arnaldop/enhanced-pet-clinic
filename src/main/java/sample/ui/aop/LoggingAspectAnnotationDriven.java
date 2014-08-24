package sample.ui.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy
@Component
@Configuration
public class LoggingAspectAnnotationDriven {

    private static Log logger = LogFactory
            .getLog(LoggingAspectAnnotationDriven.class);

    @Before("execution(* sample.ui.web.Pet*.*(..))")
    public void beforePet(JoinPoint jp) {
        logger.info("(beforePet) PET Before executing '" + jp.getSignature().toLongString() + "'");
        System.out.println("(beforePet) PET Before executing '" + jp.getSignature().toLongString() + "'");
    }

    @After("execution(* sample.ui.web.Pet*.*(..))")
    public void afterPet(JoinPoint jp) {
        logger.info("(afterPet) PET After executing '" + jp.getSignature().toLongString() + "'");
        System.out.println("(afterPet) PET After executing '" + jp.getSignature().toLongString() + "'");
    }

    @Before("execution(* sample.ui.web.*.*(*))")
    public void beforeAdviceWeb(JoinPoint jp) {
        logger.info("(beforeAdviceWeb) Before executing '" + jp.getSignature().toLongString() + "'");
    }

    @After("execution(* sample.ui.web.*.*(*))")
    public void afterAdviceWeb(JoinPoint jp) {
        logger.info("(afterAdviceWeb) After executing '" + jp.getSignature().toLongString() + "'");
    }

    @Before("execution(* sample.ui.service.*.*(*))")
    public void beforeAdviceService(JoinPoint jp) {
        logger.info("(beforeAdviceService) Before executing '" + jp.getSignature().toLongString() + "'");
    }

    @After("execution(* sample.ui.service.*.*(*))")
    public void afterAdviceService(JoinPoint jp) {
        logger.info("(afterAdviceService) After executing '" + jp.getSignature().toLongString() + "'");
    }

    @Before("execution(* sample.ui.config.*.*(*))")
    public void beforeAdviceConfig(JoinPoint jp) {
        logger.info("(beforeAdviceConfig) Before executing '" + jp.getSignature().toLongString() + "'");
    }

    @After("execution(* sample.ui.config.*.*(*))")
    public void afterAdviceConfig(JoinPoint jp) {
        logger.info("(afterAdviceConfig) After executing '" + jp.getSignature().toLongString() + "'");
    }

}
