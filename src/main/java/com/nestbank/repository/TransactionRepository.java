package com.nestbank.repository;

import com.nestbank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByCustomerId(Long customerId);
}
