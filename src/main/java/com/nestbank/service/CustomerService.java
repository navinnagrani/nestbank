package com.nestbank.service;

import com.nestbank.dto.AccountResponse;
import com.nestbank.dto.CustomerRequest;
import com.nestbank.dto.CustomerResponse;
import com.nestbank.entities.Account;
import com.nestbank.entities.Customer;
import com.nestbank.exception.CustomerNotFoundException;
import com.nestbank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return mapToCustomerResponse(customer);

    }
    private CustomerResponse mapToCustomerResponse(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());
        customerResponse.setEmail(customer.getEmail());

        List<AccountResponse> accountResponses =
                customer.getAccounts().stream()
                        .map(this::mapToAccountResponse)
                        .collect(Collectors.toList());
        customerResponse.setAccounts(accountResponses);
        return customerResponse;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());

        if (request.getAccounts() != null) {
            List<Account> accounts = request.getAccounts().stream()
                    .map(req -> {
                        Account account = new Account();
                        account.setBalance(req.getBalance());
                        account.setCustomer(customer); // 🔥 VERY IMPORTANT
                        return account;
                    })
                    .toList();

            customer.setAccounts(accounts);
        }

        Customer savedCustomer = customerRepository.save(customer);

        return mapToCustomerResponse(savedCustomer);
    }

    private AccountResponse mapToAccountResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setBalance(account.getBalance());
        return response;
    }
}
