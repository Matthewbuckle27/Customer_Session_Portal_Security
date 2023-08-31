package com.maveric.projectcharter.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maveric.projectcharter.config.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = Constants.TABLE_SESSION)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.SESSION_ID)
    @GenericGenerator(name = Constants.SESSION_ID, strategy = Constants.SESSION_STRATEGY)
    @Column(updatable = false, nullable = false)
    private String sessionId;
    @Column(name = Constants.SESSION_NAME)
    private String sessionName;
    @ManyToOne
    @JoinColumn(name = Constants.CUSTOMER_ID)
    @JsonIgnore
    private Customer customer;
    @Column(name = Constants.REMARKS)
    private String remarks;
    @Column(name = Constants.CREATED_BY)
    private String createdBy;
    @Column(name = Constants.CREATED_ON)
    private LocalDateTime createdOn;
    @Column(name = Constants.UPDATE)
    private LocalDateTime updatedOn;
    @Enumerated(EnumType.STRING)
    @Column(name = Constants.STATUS)
    private SessionStatus status;

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
        status = SessionStatus.A;
    }
}
