package com.nestbank.service;

import com.nestbank.dto.TransactionResponse;
import com.nestbank.entities.Transaction;
import com.nestbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionResponse> getTransactionsByCustomerId(Long customerId) {

        List<Transaction> transactions =
                transactionRepository.findByCustomerId(customerId);

        return transactions.stream()
                .map(tx -> new TransactionResponse(
                        tx.getId(),
                        tx.getFromAccount(),
                        tx.getToAccount(),
                        tx.getAmount(),
                        tx.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
