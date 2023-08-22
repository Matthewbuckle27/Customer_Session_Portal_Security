package com.maveric.projectcharter.service;

import com.maveric.projectcharter.dto.ArchiveFlag;
import com.maveric.projectcharter.dto.DeleteArchiveResponse;
import com.maveric.projectcharter.dto.SessionRequestDTO;
import com.maveric.projectcharter.dto.SessionResponseDTO;
import com.maveric.projectcharter.entity.Customer;
import com.maveric.projectcharter.entity.Session;
import com.maveric.projectcharter.entity.SessionHistory;
import com.maveric.projectcharter.entity.SessionStatus;
import com.maveric.projectcharter.exception.ApiRequestException;
import com.maveric.projectcharter.repository.CustomerRepository;
import com.maveric.projectcharter.repository.SessionHistoryRepository;
import com.maveric.projectcharter.repository.SessionRepository;
import com.maveric.projectcharter.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SessionHistoryRepository sessionHistoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SessionResponseDTO sessionResponseDTO;

    @Mock
    private DeleteArchiveResponse deleteArchiveResponse;

    private SessionServiceImpl sessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sessionService = new SessionServiceImpl(sessionRepository, customerRepository, sessionHistoryRepository, modelMapper, deleteArchiveResponse, maximumDormantDays,sortSessionsBy);
    }
    @Value("${sortSessionsBy}")
    private String sortSessionsBy;
    @Value("${maximumDormantDays}")
    private int maximumDormantDays;


    @Test
    void testSaveSession() {
        SessionRequestDTO sessionRequestDTO = mock(SessionRequestDTO.class);
        Session session = mock(Session.class);
        Customer customer = mock(Customer.class);
        when(modelMapper.map(sessionRequestDTO, Session.class)).thenReturn(session);
        when(customerRepository.findById(sessionRequestDTO.getCustomerId())).thenReturn(Optional.of(customer));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(modelMapper.map(session, SessionResponseDTO.class)).thenReturn(sessionResponseDTO);
        SessionResponseDTO result = sessionService.saveSession(sessionRequestDTO);
        assertNotNull(result);
    }

    @Test
    void testSaveSession_CustomerNotFound() {
        SessionRequestDTO sessionRequestDTO = new SessionRequestDTO();
        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> sessionService.saveSession(sessionRequestDTO));
    }

 /*   @Test
    public void testGetSession() {
        SessionStatus sessionStatus = SessionStatus.A;
        Session session = mock(Session.class);
        Session session2 = mock(Session.class);
     //   session1.setUpdatedOn(LocalDateTime.now());
     //   session2.setUpdatedOn(LocalDateTime.now());

        int offset = 10;
        int pageSize = 10;

*//*
        List<Session> mockSessions = new ArrayList<>();
        mockSessions.add(session1);
        mockSessions.add(session2);*//*
   //     mockSessions.add(mock(Session.class));
        // Add mock session objects to the list

        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by("updatedOn").descending());
        Page<Session> mockSessionPage = new PageImpl<>(List.of(session), pageable,1);

        when(sessionRepository.findByStatus(sessionStatus,pageable)).thenReturn(mockSessionPage);
//        when(mockSessionPage.isEmpty()).thenReturn(false);

   //     List<SessionResponseDTO> dtos = new ArrayList<>();
      //  dtos.add(mock(SessionResponseDTO.class));
    //    dtos.add(mock(SessionResponseDTO.class));
     //   dtos.add(mock(SessionResponseDTO.class));
        // Add mock session objects to the list

        //Pageable pageable1 = PageRequest.of(offset, pageSize, Sort.by("updatedOn").descending());
        //Page<SessionResponseDTO> sessionResponseDTOS = new PageImpl<>(dtos, pageable1, dtos.size());
 //       when(mockSessionPage.map(session1 -> modelMapper.map(Session.class, SessionResponseDTO.class))).thenReturn(sessionResponseDTOS);
//        when(sessionService.getSessions(SessionStatus.A, offset, pageSize)).thenReturn(sessionResponseDTOS);\
        SessionResponseDTO mockSessionResponseDTO = new SessionResponseDTO();
        when(modelMapper.map(Session.class, SessionResponseDTO.class)).thenReturn(mockSessionResponseDTO);
       // when(sessionResponseDTO.f);
        Page<SessionResponseDTO> result = sessionService.getSessions(SessionStatus.A, offset, pageSize);
        System.out.println("------------------");
        System.out.println(result.getContent().stream().toString());
        assertNotNull(result);
   //     assertEquals(mockSessions.size(), result.getContent().size());
        // You can add more assertions based on the expected behavior
    }
*/
    @Test
    void testGetSessions_SessionNotFound() {
        SessionStatus status = SessionStatus.A;
        int offset = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by("updatedOn").descending());
        Page<Session> sessionPage = mock(Page.class);
        when(sessionRepository.findByStatus(status, pageable)).thenReturn(sessionPage);
        when(sessionPage.isEmpty()).thenReturn(true);
        assertThrows(ApiRequestException.class, () -> sessionService.getSessions(status, offset, pageSize));
    }

    @Test
    void testUpdateSession() {
        String sessionId = "session123";
        SessionRequestDTO sessionRequestDTO = mock(SessionRequestDTO.class);
        Session session = mock(Session.class);
        Customer customer = mock(Customer.class);
        sessionResponseDTO.setArchiveFlag(ArchiveFlag.N);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(customerRepository.findById(sessionRequestDTO.getCustomerId())).thenReturn(Optional.of(customer));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(modelMapper.map(session, SessionResponseDTO.class)).thenReturn(sessionResponseDTO);
        SessionResponseDTO result = sessionService.updateSession(sessionId, sessionRequestDTO);
        assertNotNull(result);
    }

    @Test
    void testUpdateSession_SessionNotFound() {
        String sessionId = "session123";
        SessionRequestDTO sessionRequestDTO = mock(SessionRequestDTO.class);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> sessionService.updateSession(sessionId, sessionRequestDTO));
    }

    @Test
    void testDeleteSession() {
        String sessionId = "session123";
        Session session = mock(Session.class);
        SessionHistory sessionHistory = mock(SessionHistory.class);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(modelMapper.map(session, SessionHistory.class)).thenReturn(sessionHistory);
        DeleteArchiveResponse result = sessionService.deleteSession(sessionId);
        assertNotNull(result);
        verify(sessionHistoryRepository, times(1)).save(sessionHistory);
    }

    @Test
    void testDeleteSession_SessionNotFound() {
        String sessionId = "session123";
        Session session = mock(Session.class);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> sessionService.deleteSession(sessionId));
        verify(sessionRepository, never()).save(session);
    }

    @Test
    void testArchiveSession() {
        String sessionId = "session123";
        Session session = mock(Session.class);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        DeleteArchiveResponse result = sessionService.archiveSession(sessionId);
        System.out.println("Result"+result.getMessage());
        assertNotNull(result);
    }

    @Test
    void testArchiveSession_SessionNotFound() {
        String sessionId = "session123";
        Session session = mock(Session.class);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> sessionService.archiveSession(sessionId));
        verify(sessionRepository, never()).save(session);
    }
}
