package com.nestbank.controller;

import com.nestbank.dto.TransactionResponse;
import com.nestbank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getTransactions(
            @PathVariable Long customerId,
            Authentication authentication) {

        List<TransactionResponse> transactions =
                transactionService.getTransactionsByCustomerId(customerId);

        return ResponseEntity.ok(transactions);
    }
}
