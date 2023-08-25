package com.maveric.projectcharter.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session-id")
    @GenericGenerator(name = "session-id", strategy = "com.maveric.projectcharter.generator.SessionIdGenerator")
    @Column(updatable = false, nullable = false)
    private String sessionId;
    @Column(name = "Session_Name")
    private String sessionName;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
    @Column(name = "Remarks")
    private String remarks;
    @Column(name = "created_By")
    private String createdBy;
    @Column(name = "Created_On")
    private LocalDateTime createdOn;
    @Column(name = "Updated_On")
    private LocalDateTime updatedOn;
    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private SessionStatus status;

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
        status = SessionStatus.A;
    }
}
