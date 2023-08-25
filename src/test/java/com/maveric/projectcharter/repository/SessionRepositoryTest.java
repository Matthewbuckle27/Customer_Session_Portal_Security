package com.maveric.projectcharter.repository;

import com.maveric.projectcharter.entity.Session;
import com.maveric.projectcharter.entity.SessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SessionRepositoryTest {

    @Mock
    private SessionRepository sessionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByStatus() {
        SessionStatus status = SessionStatus.A;
        int offset = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(offset, pageSize);
        Session session1 = Session.builder()
                .sessionId("Session0001")
                .sessionName("Test Session")
                .status(SessionStatus.A)
                .build();
        Session session2 = Session.builder()
                .sessionId("Session0002")
                .sessionName("Test Session")
                .status(SessionStatus.A)
                .build();
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session1);
        sessionList.add(session2);
        Page<Session> mockSessionPage = new PageImpl<>(sessionList, pageable, sessionList.size());
        when(sessionRepository.findByStatus(status, pageable)).thenReturn(mockSessionPage);

        Page<Session> result = sessionRepository.findByStatus(status, pageable);
        assertEquals(sessionList.size(), result.getContent().size());
    }
}
