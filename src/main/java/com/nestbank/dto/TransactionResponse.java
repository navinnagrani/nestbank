package com.nestbank.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private Long fromAccount;
    private Long toAccount;
    private Double amount;
    private LocalDateTime timestamp;

    public TransactionResponse(Long id, Long fromAccount, Long toAccount, Double amount, LocalDateTime timestamp) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
