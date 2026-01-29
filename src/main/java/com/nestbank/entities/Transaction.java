package com.nestbank.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromAccount;
    private Long toAccount;
    private Double amount;

    private LocalDateTime timestamp;

    private Long customerId;   // owner of transaction

    public Transaction() {}

    public Transaction(Long fromAccount,
                       Long toAccount,
                       Double amount,
                       Long customerId) {

        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.customerId = customerId;
        this.timestamp = LocalDateTime.now();
    }
}
