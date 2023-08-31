package com.maveric.projectcharter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maveric.projectcharter.config.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = Constants.TABLE_SESSION_HISTORY)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionHistory {

    @Id
    @Column(name = Constants.SESSION_ID)
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
    @Column(name = Constants.DELETED_ON)
    private LocalDateTime deletedOn;
    @Enumerated(EnumType.STRING)
    @Column(name = Constants.STATUS)
    private SessionStatus status;
}
