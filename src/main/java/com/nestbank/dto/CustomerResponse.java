package com.nestbank.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private List<AccountResponse> accounts;
}
