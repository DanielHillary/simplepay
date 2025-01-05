package com.simbrella.simplepay.transaction_management.repository;

import com.simbrella.simplepay.transaction_management.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    Page<Transaction> findByUserId(Long userId, Pageable pageable);
}
