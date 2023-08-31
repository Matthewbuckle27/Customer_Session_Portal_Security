package com.maveric.projectcharter.dto;

import com.maveric.projectcharter.config.Constants;
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

    @NotBlank(message = Constants.CUSTOMER_NAME_REQUIRED)
    @Size(min = 3, message = Constants.CUSTOMER_NAME_LENGTH)
    private String name;
    @NotBlank(message = Constants.CUSTOMER_EMAIL_REQUIRED)
    private String email;

}
