package com.maveric.projectcharter.dto;

import com.maveric.projectcharter.config.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionRequestDTO {

    @NotBlank(message = Constants.SESSION_NAME_REQUIRED)
    @Size(min = 4, message = Constants.SESSION_NAME_LENGTH)
    private String sessionName;
    @NotBlank(message = Constants.CUSTOMER_ID_REQUIRED)
    private String customerId;
    @NotBlank(message = Constants.REMARKS_REQUIRED)
    @Size(min = 4, message = Constants.REMARKS_LENGTH)
    private String remarks;
    @NotNull(message = Constants.CREATED_BY_REQUIRED)
    private String createdBy;

}
