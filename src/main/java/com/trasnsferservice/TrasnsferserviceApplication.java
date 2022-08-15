package com.trasnsferservice;

import com.trasnsferservice.domain.Account;
import com.trasnsferservice.domain.Transaction;
import com.trasnsferservice.repository.AccountsRepository;
import com.trasnsferservice.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;

@SpringBootApplication
@EnableTransactionManagement
public class TrasnsferserviceApplication implements CommandLineRunner {

	@Autowired
	AccountsRepository accountsRepository;

	@Autowired
	TransferService transferService;

	public static void main(String[] args) {

		SpringApplication.run(TrasnsferserviceApplication.class, args);
	}

	public void createAccountsAndTransfer(){
		accountsRepository.save(new Account(93232343l, BigDecimal.valueOf(1000)));
		accountsRepository.save(new Account(45678903l, BigDecimal.valueOf(100)));
		transferService.saveTransaction(new Transaction(93232343l,45678903l,BigDecimal.valueOf(100)));
	}

	@Override
	public void run(String... args) throws Exception {
		createAccountsAndTransfer();
	}
}
