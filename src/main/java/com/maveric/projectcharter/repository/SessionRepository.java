package com.maveric.projectcharter.repository;

import com.maveric.projectcharter.entity.Session;
import com.maveric.projectcharter.entity.SessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
    Page<Session> findByStatus(SessionStatus status, Pageable pageable);

}
