package com.maveric.projectcharter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer-id")
    @GenericGenerator(name = "customer-id", strategy = "com.maveric.projectcharter.generator.CustomerIdGenerator")
    @Column(updatable = false, nullable = false)
    private String customerId;
    private String name;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Session> sessions;

}
