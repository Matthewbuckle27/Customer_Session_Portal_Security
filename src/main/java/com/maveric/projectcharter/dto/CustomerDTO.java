package com.maveric.projectcharter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {

    @NotBlank(message = "Customer name is required")
    @Size(min = 3, message = "Customer name must have at least 3 characters")
    private String name;
    @NotBlank(message = "Email is required")
    private String email;

}
