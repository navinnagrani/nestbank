package com.nestbank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferResponse {
    private String message;
    private Long fromAccount;
    private Long toAccount;
    private Double amount;
}
