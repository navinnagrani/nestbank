package com.nestbank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    private Long customerId;
    private Double balance;
}
