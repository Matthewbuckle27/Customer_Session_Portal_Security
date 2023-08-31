package com.maveric.projectcharter.aop;

import com.maveric.projectcharter.config.Constants;
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

    @Before(Constants.CONTROLLER)
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        logger.info(Constants.EXECUTING + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = Constants.CONTROLLER, returning = Constants.RESULT)
    public void logAfterControllerMethod(Object result) {
        logger.info(Constants.RESPONSE + result.toString());
    }

    @AfterThrowing(pointcut = Constants.CONTROLLER, throwing = Constants.EX)
    public void logExceptionInController(JoinPoint joinPoint, Exception ex) {
        logger.error(Constants.EXCEPTION_IN + joinPoint.getSignature().toShortString() + Constants.EXCEPTION + ex.getMessage());
    }

    @Before(Constants.SERVICE)
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        logger.info(Constants.EXECUTING + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = Constants.SERVICE, returning = Constants.RESULT)
    public void logAfterServiceMethod(Object result) {
        logger.info(Constants.RESPONSE + result.toString());
    }

    @AfterThrowing(pointcut = Constants.SERVICE, throwing = Constants.EX)
    public void logExceptionInService(JoinPoint joinPoint, Exception ex) {
        logger.error(Constants.EXCEPTION_IN + joinPoint.getSignature().toShortString() + Constants.EXCEPTION + ex.getMessage());
    }

    public void logMethodArguments(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info(Constants.ARGUMENT + arg.toString());
        }
    }
}
