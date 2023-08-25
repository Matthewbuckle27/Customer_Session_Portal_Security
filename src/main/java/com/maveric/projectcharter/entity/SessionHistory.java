package com.maveric.projectcharter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "session_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionHistory {

    @Id
    @Column(name = "Session_Id")
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
    @Column(name = "Deleted_On")
    private LocalDateTime deletedOn;
    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private SessionStatus status;
}
