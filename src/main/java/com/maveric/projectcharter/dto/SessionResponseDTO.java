package com.maveric.projectcharter.dto;

import com.maveric.projectcharter.entity.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionResponseDTO {

    private String sessionId;
    private String sessionName;
    private String customerName;
    private String remarks;
    private String createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private SessionStatus status;
    private ArchiveFlag archiveFlag;
}
