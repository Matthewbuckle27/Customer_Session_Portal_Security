package com.maveric.projectcharter.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApiExceptionHandlerTest {

    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    @Mock
    private ApiRequestException apiRequestException;

    @Mock
    private ServiceException serviceException;

    @Mock
    private NullPointerException nullPointerException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleApiRequestException() {
        String exceptionMessage = "API Exception message";
        when(apiRequestException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity = apiExceptionHandler.handleApiRequestException(apiRequestException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testHandleServiceException() {
        String exceptionMessage = "Service Exception message";
        when(serviceException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity = apiExceptionHandler.handleServiceException(serviceException);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testHandleNullPointerException() {
        String exceptionMessage = "NullPointerException message";
        when(nullPointerException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity = apiExceptionHandler.handleNullPointerException(nullPointerException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleInvalidArgument() {
        FieldError error1 = new FieldError("objectName1", "sessionName", "Session Name required");
        FieldError error2 = new FieldError("objectName2", "remarks", "Remarks are required");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(error1, error2));
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        Map<String, String> errorMap = apiExceptionHandler.handleInvalidArgument(methodArgumentNotValidException);

        assertEquals(2, errorMap.size());
        assertEquals("Session Name required", errorMap.get("sessionName"));
        assertEquals("Remarks are required", errorMap.get("remarks"));
    }
}
