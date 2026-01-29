package com.nestbank.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(Long id) {
        super("Account not found with id: "+id);
    }
}
