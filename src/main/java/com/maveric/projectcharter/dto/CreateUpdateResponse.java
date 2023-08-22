package com.maveric.projectcharter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
@NoArgsConstructor
public class CreateUpdateResponse {

    private String message;
    private HttpStatus httpStatus;
    private SessionResponseDTO sessionResponseDTO;

}

