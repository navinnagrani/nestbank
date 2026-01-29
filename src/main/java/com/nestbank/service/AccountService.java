package com.nestbank.service;

import com.nestbank.dto.AccountRequest;
import com.nestbank.dto.AccountResponse;
import com.nestbank.entities.Account;
import com.nestbank.entities.Customer;
import com.nestbank.exception.AccountNotFoundException;
import com.nestbank.exception.CustomerNotFoundException;
import com.nestbank.repository.AccountRepository;
import com.nestbank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id).
        orElseThrow(() -> new AccountNotFoundException(id));
        return mapToAccountResponse(account);
    }

    public List<AccountResponse> getAllAccounts(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(customerId));
        return customer.getAccounts().stream().map(this::mapToAccountResponse).collect(Collectors.toList());
    }

    public AccountResponse createAccount(AccountRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Account account = new Account();
        account.setBalance(request.getBalance());
        account.setCustomer(customer);
        Account savedAccount = accountRepository.save(account);

        AccountResponse response = new AccountResponse();
        response.setId(savedAccount.getId());
        response.setBalance(savedAccount.getBalance());
        return response;
    }


    private AccountResponse mapToAccountResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setBalance(account.getBalance());
        return response;
    }
}
