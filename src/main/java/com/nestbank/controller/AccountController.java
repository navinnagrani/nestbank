package com.nestbank.controller;

import com.nestbank.dto.AccountRequest;
import com.nestbank.dto.AccountResponse;
import com.nestbank.entities.Account;
import com.nestbank.entities.Customer;
import com.nestbank.repository.AccountRepository;
import com.nestbank.repository.CustomerRepository;
import com.nestbank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {



    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponse create(@RequestBody AccountRequest account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> getAllAccounts(@PathVariable Long customerId) {
        return  accountService.getAllAccounts(customerId);
    }
}
