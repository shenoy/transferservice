package com.trasnsferservice.repository;

import com.trasnsferservice.domain.Account;
import com.trasnsferservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAll();
    Optional<Transaction> findById(Long id);
}
