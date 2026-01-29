package com.nestbank.controller;

import com.nestbank.dto.CustomerRequest;
import com.nestbank.dto.CustomerResponse;
import com.nestbank.entities.Customer;
import com.nestbank.repository.CustomerRepository;
import com.nestbank.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerResponse create(@RequestBody CustomerRequest customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    //INSERT INTO CUSTOMERS (name,email) VALUES ('Navin','nav@gmail.com');
    //INSERT INTO ACCOUNT (balance,customer_id) VALUES ('300',1);
}
