package com.trasnsferservice.service;

import com.trasnsferservice.domain.Account;
import com.trasnsferservice.domain.Transaction;
import com.trasnsferservice.repository.AccountsRepository;
import com.trasnsferservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountsRepository accountsRepository;


    public Account save(Account account) {
        System.out.println("SAVING NEW ACCOUNT");
       return accountsRepository.save(account);
    }

    public Account getAccountById(Long id) {
        Optional<Account> accountOptional = accountsRepository.findById(id);
        return accountOptional.isPresent()?accountOptional.get():null;
    }

    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        Optional<Account> account1 =  accountsRepository.findByAccountNumber(transaction.getSourceAccountNumber());
        Optional<Account> account2 =  accountsRepository.findByAccountNumber(transaction.getDestinationAccountNumber());
        if(account1.isPresent()&&account2.isPresent()){
            makeTransfer(account1.get(), account2.get(), transaction.getAmount());
            transactionRepository.save(transaction);
            return transaction;
        }
        return null;
    }

    @Transactional
    public void makeTransfer(Account source, Account destination, BigDecimal amount){
        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));
        accountsRepository.save(source);
        accountsRepository.save(destination);
    }

    @Transactional
    public void makeTransfer(Long sourceAccountNumber, Long destinationAccountNumber, BigDecimal amount){
        Optional<Account> source = accountsRepository.findByAccountNumber(sourceAccountNumber);
        Optional<Account> destination = accountsRepository.findByAccountNumber(destinationAccountNumber);
        if(source.isPresent() && destination.isPresent()){
            makeTransfer(source.get(),destination.get(),amount);
            transactionRepository.save(new Transaction(source.get().getAccountNumber(), destination.get().getAccountNumber(), amount));
        }
    }
}
