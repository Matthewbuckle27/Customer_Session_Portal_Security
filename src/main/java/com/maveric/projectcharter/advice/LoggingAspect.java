package com.maveric.projectcharter.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.maveric.projectcharter.controller.*.*(..))")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        logger.info("Executing: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.maveric.projectcharter.controller.*.*(..))", returning = "result")
    public void logAfterControllerMethod(Object result) {
        logger.info("Response: " + result.toString());
    }

    @AfterThrowing(pointcut = "execution(* com.maveric.projectcharter.controller.*.*(..))", throwing = "ex")
    public void logExceptionInController(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in: " + joinPoint.getSignature().toShortString() + ". Exception: " + ex.getMessage());
    }

    @Before("execution(* com.maveric.projectcharter.service.*.*(..))")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        logger.info("Executing: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.maveric.projectcharter.service.*.*(..))", returning = "result")
    public void logAfterServiceMethod(Object result) {
        logger.info("Response: " + result.toString());
    }

    @AfterThrowing(pointcut = "execution(* com.maveric.projectcharter.service.*.*(..))", throwing = "ex")
    public void logExceptionInService(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in: " + joinPoint.getSignature().toShortString() + ". Exception: " + ex.getMessage());
    }

    private void logMethodArguments(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info("Request Argument: " + arg.toString());
        }
    }
}
