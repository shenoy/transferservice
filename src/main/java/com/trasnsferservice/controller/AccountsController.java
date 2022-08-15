package com.trasnsferservice.controller;

import com.trasnsferservice.domain.Account;
import com.trasnsferservice.repository.AccountsRepository;
import com.trasnsferservice.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class AccountsController {

    @Autowired
    TransferService transferService;

    @Autowired
    AccountsRepository accountsRepository;

    @GetMapping({"/","/home"})
    public String getIndex(Model model){
        model.addAttribute("accounts", accountsRepository.findAll());
        return "accounts";
    }

    @PostMapping("/account")
    @ResponseBody
    public Account create(@RequestParam("accountNumber") Long accountNumber, @RequestParam("balance")BigDecimal balance){
        Account account = new Account(accountNumber, balance);
        return accountsRepository.save(account);
    }

    @GetMapping("/account/{id}")
    public Account getAccountById(@PathVariable("id")Long id){
        return transferService.getAccountById(id);
    }
}
