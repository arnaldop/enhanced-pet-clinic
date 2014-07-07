package sample.ui.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Configuration
public class LoggingAspectAnnotationDriven {

	// @Around("execution(* *(..))")
	@Before("execution(* find*(*))")
	public void beforeAdvice(JoinPoint jp) {
		System.out.println("Before executing " + jp.getSignature().getName()
				+ "()");
	}

	@Pointcut("execution(* find*(*))")
	public void pointcutAdvice(ProceedingJoinPoint pjp) {
		System.out.println("Pointcut executing " + pjp.getSignature().getName()
				+ "()");
	}

	@Pointcut("within(sample.ui..")
	public void withinAdvice(JoinPoint jp) {
		System.out.println("Within executing " + jp.getSignature().getName()
				+ "()");
	}

	// @AfterReturning("execution(* *(..))")
	// public void logServiceAccess(JoinPoint joinPoint) {
	// System.out.println("Completed: " + joinPoint);
	// }

	// @AfterReturning("execution(* *..*Service.*(..))")
	// public void logServiceAccess(JoinPoint joinPoint) {
	// System.out.println("Completed: " + joinPoint);
	// }

}
