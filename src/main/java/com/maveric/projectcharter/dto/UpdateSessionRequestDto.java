package com.maveric.projectcharter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSessionRequestDto {

    @NotBlank(message = "Session name is required")
    @Size(min = 4, message = "Session name must have at least 4 characters")
    private String sessionName;
    @NotBlank(message = "Notes are required")
    @Size(min = 4, message = "Notes must be at least 4 characters")
    private String remarks;
}
