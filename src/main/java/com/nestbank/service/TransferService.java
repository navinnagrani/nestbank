package com.nestbank.service;

import com.nestbank.dto.TransferRequest;
import com.nestbank.dto.TransferResponse;
import com.nestbank.entities.Account;
import com.nestbank.entities.Transaction;
import com.nestbank.exception.AccountNotFoundException;
import com.nestbank.exception.InsufficientBalanceException;
import com.nestbank.repository.AccountRepository;
import com.nestbank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        Account from = accountRepository.findById(request.getFromAccount()).orElseThrow(()->new AccountNotFoundException(request.getFromAccount()));
        Account to = accountRepository.findById(request.getToAccount()).orElseThrow(()->new AccountNotFoundException(request.getToAccount()));

        if(from.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException();
        }

        from.setBalance(from.getBalance() - request.getAmount());
        to.setBalance(to.getBalance() + request.getAmount());

        Long customerId = from.getCustomer().getId();
        Transaction tx = new Transaction(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount(),
                customerId
        );

        transactionRepository.save(tx);

        TransferResponse response = new TransferResponse();
        response.setMessage("Transfer successful");
        response.setFromAccount(from.getId());
        response.setToAccount(to.getId());
        response.setAmount(request.getAmount());

        return response;


    }
}
