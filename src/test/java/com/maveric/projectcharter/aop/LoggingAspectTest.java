package com.maveric.projectcharter.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class LoggingAspectTest {
    @InjectMocks
    private LoggingAspect loggingAspect;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testLogBeforeControllerMethod() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        String method = "Mock Controller Method";
        when(joinPoint.getSignature()).thenReturn(mock(Signature.class));
        when(joinPoint.getSignature().toShortString()).thenReturn(method);
        loggingAspect.logBeforeControllerMethod(joinPoint);
        verify(logger).info("Executing: "+method);
    }
    @Test
    void testLogAfterControllerMethod() {
        Object object = mock(Object.class);
        String response = "Mock Response Object";
        when(object.toString()).thenReturn(response);
        loggingAspect.logAfterControllerMethod(object);
        verify(logger).info("Response: "+response);
    }
    @Test
    void testLogExceptionInController(){
        JoinPoint joinPoint = mock(JoinPoint.class);
        String method = "Mock Controller Method";
        Exception exception = mock(Exception.class);
        String message = "API Request Exception Message";
        when(joinPoint.getSignature()).thenReturn(mock(Signature.class));
        when(joinPoint.getSignature().toShortString()).thenReturn(method);
        when(exception.getMessage()).thenReturn(message);
        loggingAspect.logExceptionInController(joinPoint,exception);
        verify(logger).error("Exception in: " + method + ". Exception: " + message);
    }
    @Test
    void testLogBeforeServiceMethod() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        String method = "Mock Service Method";
        when(joinPoint.getSignature()).thenReturn(mock(Signature.class));
        when(joinPoint.getSignature().toShortString()).thenReturn(method);
        loggingAspect.logBeforeServiceMethod(joinPoint);
        verify(logger).info("Executing: "+method);
    }
    @Test
    void testLogAfterServiceMethod() {
        Object object = mock(Object.class);
        String response = "Mock Response Object";
        when(object.toString()).thenReturn(response);
        loggingAspect.logAfterServiceMethod(object);
        verify(logger).info("Response: "+response);
    }
    @Test
    void testLogExceptionInService(){
        JoinPoint joinPoint = mock(JoinPoint.class);
        String method = "Mock Service Method";
        Exception exception = mock(Exception.class);
        String message = "API Request Exception Message";
        when(joinPoint.getSignature()).thenReturn(mock(Signature.class));
        when(joinPoint.getSignature().toShortString()).thenReturn(method);
        when(exception.getMessage()).thenReturn(message);
        loggingAspect.logExceptionInService(joinPoint,exception);
        verify(logger).error("Exception in: " + method + ". Exception: " + message);
    }
    @Test
    void testLogMethodArguments() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Object[] args = new Object[]{"Session123", 123, true};
        when(joinPoint.getArgs()).thenReturn(args);
        loggingAspect.logMethodArguments(joinPoint);
        verify(logger).info("Request Argument: Session123");
        verify(logger).info("Request Argument: 123");
        verify(logger).info("Request Argument: true");
    }
}