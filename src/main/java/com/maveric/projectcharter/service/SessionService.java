package com.maveric.projectcharter.service;

import com.maveric.projectcharter.dto.DeleteArchiveResponse;
import com.maveric.projectcharter.dto.SessionRequestDTO;
import com.maveric.projectcharter.dto.SessionResponseDTO;
import com.maveric.projectcharter.dto.UpdateSessionRequestDto;
import com.maveric.projectcharter.entity.SessionStatus;
import org.springframework.data.domain.Page;

public interface SessionService {
    SessionResponseDTO saveSession(SessionRequestDTO sessionRequestDTO);
    Page<SessionResponseDTO> getSessions(String status, int offset, int pagesize);
    SessionResponseDTO updateSession(String sessionId, UpdateSessionRequestDto updateSessionRequestDto);
    DeleteArchiveResponse deleteSession(String sessionId);
    DeleteArchiveResponse archiveSession(String sessionId);
}
