package com.nestbank.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerRequest {
    private String name;
    private String email;
    private List<AccountRequest> accounts;
}
