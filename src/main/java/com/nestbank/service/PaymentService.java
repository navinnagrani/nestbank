package com.nestbank.service;

import com.nestbank.dto.CardPaymentRequest;
import com.nestbank.entities.Account;
import com.nestbank.entities.Transaction;
import com.nestbank.repository.AccountRepository;
import com.nestbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public String payWithCard(CardPaymentRequest req) {

        // 🔐 Basic validations
        if (req.getCardNumber().length() != 16) {
            throw new RuntimeException("Invalid card number");
        }

        if (req.getCvv().length() != 3) {
            throw new RuntimeException("Invalid CVV");
        }

        if (req.getAmount() <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 💰 Add money
        account.setBalance(account.getBalance() + req.getAmount());

        // 🧾 Save transaction (card → account)
        Transaction tx = new Transaction(
                null, // from external card
                req.getAccountId(),
                req.getAmount(),
                account.getCustomer().getId()
        );

        transactionRepository.save(tx);

        return "Payment successful";
    }
}