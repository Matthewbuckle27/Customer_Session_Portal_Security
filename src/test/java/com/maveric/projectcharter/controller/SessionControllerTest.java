package com.maveric.projectcharter.controller;

import com.maveric.projectcharter.dto.*;
import com.maveric.projectcharter.entity.SessionStatus;
import com.maveric.projectcharter.exception.ApiRequestException;
import com.maveric.projectcharter.service.SessionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    @Mock
    private CreateUpdateResponse createUpdateResponse;

    @Mock
    private SessionResponseDTO sessionResponseDTO;

    @Mock
    private DeleteArchiveResponse deleteArchiveResponse;

    @Test
    void testCreateSession_Success() {
        SessionRequestDTO sessionRequestDTO = mock(SessionRequestDTO.class);
        when(sessionService.saveSession(sessionRequestDTO)).thenReturn(sessionResponseDTO);
        ResponseEntity<CreateUpdateResponse> result = sessionController.createSession(sessionRequestDTO);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void testCreateSession_CustomerNotFound() {
        SessionRequestDTO sessionRequestDTO = mock(SessionRequestDTO.class);
        doThrow(new ApiRequestException("Customer is not found")).when(sessionService).saveSession(sessionRequestDTO);
        assertThrows(ApiRequestException.class, () -> sessionController.createSession(sessionRequestDTO));
        verify(createUpdateResponse, never()).setMessage("Session created successfully");
        verify(createUpdateResponse, never()).setHttpStatus(HttpStatus.CREATED);
    }

    /*@Test
    void testGetSessions() {
        String status = "A";
        int offset = 0;
        int pageSize = 10;
        SessionStatus sessionStatus = SessionStatus.A;
        Page<SessionResponseDTO> sessionPage = mock(Page.class);
        when(sessionService.getSessions(sessionStatus, offset, pageSize)).thenReturn(sessionPage);
        ResponseEntity<Page<SessionResponseDTO>> result = sessionController.getSessions(status, offset, pageSize);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(sessionPage, result.getBody());
        assertNotNull(result);
    }*/

    @Test
    void testGetSessions_SessionNotFound() {
        String status = "A";
        int offset = 0;
        int pageSize = 10;
        SessionStatus sessionStatus = SessionStatus.A;
        doThrow(new ApiRequestException("No session found")).when(sessionService).getSessions(sessionStatus, offset, pageSize);
        assertThrows(ApiRequestException.class, () -> sessionController.getSessions(status, offset, pageSize));
    }

    @Test
    void testUpdateSession() {
        String sessionId = "session123";
        SessionRequestDTO sessionRequestDTO = mock(SessionRequestDTO.class);
        when(sessionService.updateSession(sessionId,sessionRequestDTO)).thenReturn(sessionResponseDTO);
        ResponseEntity<CreateUpdateResponse> result = sessionController.updateSession(sessionId, sessionRequestDTO);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void testUpdateSession_ApiRequestException() {
        String sessionId = "session123";
        SessionRequestDTO sessionRequestDTO = mock(SessionRequestDTO.class);
        doThrow(new ApiRequestException("No session found")).when(sessionService).updateSession(sessionId,sessionRequestDTO);
        assertThrows(ApiRequestException.class, () -> sessionController.updateSession(sessionId, sessionRequestDTO));
        verify(createUpdateResponse, never()).setMessage("Session Updated");
        verify(createUpdateResponse, never()).setHttpStatus(HttpStatus.OK);
    }

    @Test
    void testDeleteSession() {
        String sessionId = "session123";
        when(sessionService.deleteSession(sessionId)).thenReturn(deleteArchiveResponse);
        ResponseEntity<DeleteArchiveResponse> result = sessionController.deleteSession(sessionId);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(deleteArchiveResponse, result.getBody());
        assertNotNull(result);
    }

    @Test
    void testDeleteSession_ApiRequestException() {
        String sessionId = "session123";
        doThrow(new ApiRequestException("No session found")).when(sessionService).deleteSession(sessionId);
        assertThrows(ApiRequestException.class, () -> sessionController.deleteSession(sessionId));
        verify(deleteArchiveResponse, never()).setMessage("Session Deleted");
        verify(createUpdateResponse, never()).setHttpStatus(HttpStatus.OK);
    }

    @Test
    void testArchiveSession() {
        String sessionId = "session123";
        when(sessionService.archiveSession(sessionId)).thenReturn(deleteArchiveResponse);
        ResponseEntity<DeleteArchiveResponse> result = sessionController.archiveSession(sessionId);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(deleteArchiveResponse, result.getBody());
        assertNotNull(result);
    }

    @Test
    void testArchiveSession_ApiRequestException() {
        String sessionId = "session123";
        doThrow(new ApiRequestException("No session found")).when(sessionService).archiveSession(sessionId);
        assertThrows(ApiRequestException.class, () -> sessionController.archiveSession(sessionId));
        verify(deleteArchiveResponse, never()).setMessage("Session Archived");
        verify(deleteArchiveResponse, never()).setHttpStatus(HttpStatus.OK);
    }
}
