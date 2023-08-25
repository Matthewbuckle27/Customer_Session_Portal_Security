package com.maveric.projectcharter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionRequestDTO {

    @NotBlank(message = "Session name is required")
    private String sessionName;
    @NotBlank(message = "Customer Id is required")
    private String customerId;
    @NotBlank(message = "Remarks are required")
    private String remarks;
    @NotNull(message = "Created By RM NOT found")
    private String createdBy;

}
