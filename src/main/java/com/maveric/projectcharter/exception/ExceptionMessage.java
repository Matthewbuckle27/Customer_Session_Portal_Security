package com.maveric.projectcharter.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage {

    private String type;
    private String message;
    private HttpStatus httpStatus;
}
