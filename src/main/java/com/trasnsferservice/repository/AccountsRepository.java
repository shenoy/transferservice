package com.trasnsferservice.repository;
import com.trasnsferservice.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account,Long> {
    List<Account> findAll();
    Optional<Account> findById(Long id);
    Optional<Account> findByAccountNumber(Long accountNumber);
}
