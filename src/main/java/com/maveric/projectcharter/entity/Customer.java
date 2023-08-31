package com.maveric.projectcharter.entity;

import com.maveric.projectcharter.config.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = Constants.TABLE_CUSTOMER)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.CUSTOMER_ID)
    @GenericGenerator(name = Constants.CUSTOMER_ID, strategy = Constants.CUSTOMER_STRATEGY)
    @Column(updatable = false, nullable = false)
    private String customerId;
    private String name;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = Constants.TABLE_CUSTOMER, cascade = CascadeType.ALL)
    private List<Session> sessions;

}
