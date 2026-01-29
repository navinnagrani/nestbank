package com.nestbank.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Long customerId;
    private Double balance;

    @ManyToOne
    @JoinColumn(name="customer_id",nullable = false)
    //@JsonBackReference
    private Customer customer;
}
