package com.maveric.projectcharter.service;

import com.maveric.projectcharter.dto.*;
import com.maveric.projectcharter.entity.Customer;
import com.maveric.projectcharter.entity.Session;
import com.maveric.projectcharter.entity.SessionHistory;
import com.maveric.projectcharter.entity.SessionStatus;
import com.maveric.projectcharter.exception.ApiRequestException;
import com.maveric.projectcharter.repository.CustomerRepository;
import com.maveric.projectcharter.repository.SessionHistoryRepository;
import com.maveric.projectcharter.repository.SessionRepository;
import com.maveric.projectcharter.service.impl.SessionServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SessionServiceTest {
    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SessionHistoryRepository sessionHistoryRepository;

    @Mock
    private DeleteArchiveResponse deleteArchiveResponse;

    @Spy
    private ModelMapper modelMapper;


    @Value("${maximumDormantDays}")
    private int maximumDormantDays;

    @Value("${sortSessionsBy}")
    private String sortSessionsBy;

    Customer customer = Customer.builder()
            .customerId("CB00001")
            .name("Matthew")
            .email("matthew@gmail.com")
            .build();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSession() {
        SessionRequestDTO sessionRequestDTO = SessionRequestDTO.builder()
                .sessionName("Test Session")
                .customerId("CB00001")
                .remarks("Open RD")
                .createdBy("Gowtham")
                .build();
        Session session = Session.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .customer(customer)
                .status(SessionStatus.A)
                .build();
        SessionResponseDTO sessionResponseDTO = SessionResponseDTO.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .customerName("Matthew")
                .status(SessionStatus.A)
                .archiveFlag(ArchiveFlag.N)
                .build();
        when(modelMapper.map(sessionRequestDTO, Session.class)).thenReturn(session);
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(sessionRepository.save(session)).thenReturn(session);
        when(modelMapper.map(session, SessionResponseDTO.class)).thenReturn(sessionResponseDTO);
        SessionResponseDTO result = sessionService.saveSession(sessionRequestDTO);
        assertNotNull(result);
        assertEquals(ArchiveFlag.N, result.getArchiveFlag());
    }

    @Test
    void testSaveSession_CustomerNotFound() {
        SessionRequestDTO sessionRequestDTO = SessionRequestDTO.builder()
                .sessionName("Test Session")
                .customerId("CB00001")
                .remarks("Open RD")
                .createdBy("Gowtham")
                .build();
        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> sessionService.saveSession(sessionRequestDTO));
    }

    @Test
    void testUpdateSession() {
        UpdateSessionRequestDto updateSessionRequestDto = UpdateSessionRequestDto.builder()
                .sessionName("Test Updated Session")
                .remarks("Open RD")
                .build();
        Session session = Session.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .customer(customer)
                .status(SessionStatus.A)
                .build();
        SessionResponseDTO sessionResponseDTO = SessionResponseDTO.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .status(SessionStatus.A)
                .archiveFlag(ArchiveFlag.N)
                .build();
        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session));
        when(sessionRepository.save(session)).thenReturn(session);
        when(modelMapper.map(session, SessionResponseDTO.class)).thenReturn(sessionResponseDTO);
        SessionResponseDTO dto = sessionService.updateSession(anyString(), updateSessionRequestDto);
        assertNotNull(dto);
        verify(sessionRepository, times(1)).save(any());
    }

    @Test
    void testUpdateSession_SessionNotFound() {
        UpdateSessionRequestDto updateSessionRequestDto = UpdateSessionRequestDto.builder()
                .sessionName("Test Updated Session")
                .remarks("Open RD")
                .build();
        when(sessionRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> sessionService.updateSession(anyString(), updateSessionRequestDto));
    }

    @Test
    void testUpdateSession_CannotUpdate() {
        UpdateSessionRequestDto updateSessionRequestDto = UpdateSessionRequestDto.builder()
                .sessionName("Test Updated Session")
                .remarks("Open RD")
                .build();
        Session session = Session.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .customer(customer)
                .status(SessionStatus.X)
                .build();
        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session));
        assertThrows(ApiRequestException.class, () -> sessionService.updateSession(anyString(), updateSessionRequestDto));
    }

    @Test
    void testDeleteSession() {
        Session session = Session.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .customer(customer)
                .status(SessionStatus.A)
                .build();
        SessionHistory sessionHistory = SessionHistory.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .customer(customer)
                .remarks("Open RD")
                .createdBy("Gowtham")
                .build();

        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session));
        when(modelMapper.map(session, SessionHistory.class)).thenReturn(sessionHistory);
        DeleteArchiveResponse deleteResponse = sessionService.deleteSession(session.getSessionId());
        assertNotNull(deleteResponse);
        assertEquals(SessionStatus.D, session.getStatus());
        verify(sessionHistoryRepository, times(1)).save(any());
    }

    @Test
    void testDeleteSession_SessionNotFound() {
        when(sessionRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> sessionService.deleteSession(anyString()));
    }

/*    @Test
    void testDeleteSession_ServiceException(){
        doThrow(new ServiceException("Failed to connect to database")).when(sessionRepository.findById(anyString()));
        assertThrows(ServiceException.class, () -> sessionService.deleteSession(anyString()));
    }*/

    @Test
    void testDeleteSession_CannotDelete() {
        Session session = Session.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .customer(customer)
                .remarks("Open RD")
                .createdBy("Gowtham")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .status(SessionStatus.X)
                .build();
        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session));
        assertThrows(ApiRequestException.class, () -> sessionService.deleteSession(anyString()));
    }

    @Test
    void testArchiveSession() {
        Session session = Session.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .updatedOn(LocalDateTime.parse("2023-08-12T14:52:01.733428"))
                .customer(customer)
                .status(SessionStatus.A)
                .build();
        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session));
        DeleteArchiveResponse archiveResponse = sessionService.archiveSession(session.getSessionId());
        assertNotNull(archiveResponse);
        assertEquals(SessionStatus.X, session.getStatus());
        verify(sessionRepository, times(1)).save(any());
    }

    @Test
    void testArchiveSession_NotEligible() {
        Session session = Session.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .updatedOn(LocalDateTime.parse("2023-08-28T14:52:01.733428"))
                .customer(customer)
                .status(SessionStatus.A)
                .build();
        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session));
        assertThrows(ApiRequestException.class, () -> sessionService.archiveSession(anyString()));
    }

    @Test
    void testArchiveSession_SessionNotFound() {
        when(sessionRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> sessionService.archiveSession(anyString()));
    }

    @Test
    void testArchiveSession_SessionAlreadyArchived() {
        Session session = Session.builder()
                .sessionId("Session000123")
                .sessionName("Test Session")
                .customer(customer)
                .status(SessionStatus.X)
                .build();
        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session));
        assertThrows(ApiRequestException.class, () -> sessionService.archiveSession(anyString()));
    }
}
