package com.trasnsferservice.controller;


import com.trasnsferservice.domain.Transaction;
import com.trasnsferservice.form.TransferForm;
import com.trasnsferservice.repository.AccountsRepository;
import com.trasnsferservice.repository.TransactionRepository;
import com.trasnsferservice.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    TransferService transferService;


    @GetMapping("/transactions")
    public String getIndex(Model model){

        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactions";
    }

    @PostMapping("/transfer/source/{accountNumber1}/destination/{accountNumber2}/amount/{amount}")
    @ResponseBody
    public ResponseEntity<Transaction> doTransfer(@PathVariable("accountNumber1") Long accountNumber1,
                                                  @PathVariable("accountNumber2") Long accountNumber2, @PathVariable("amount") BigDecimal amount){
        Transaction transaction = new Transaction(accountNumber1,accountNumber2,amount);
        return  new ResponseEntity<Transaction>(transferService.saveTransaction(transaction), HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<Transaction> doTransfer(@RequestBody Transaction transaction){
        return  new ResponseEntity<Transaction>(transferService.saveTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/transferForm")
    public String getTransferForm(Model model){
        model.addAttribute("accounts", accountsRepository.findAll());
        model.addAttribute("form", new TransferForm());
        return "makeTransfer";
    }

    @PostMapping("/transferForm")
    public String postTransferForm(TransferForm form){
        transferService.makeTransfer(form.getSourceAccountNumber(),form.getDestinationAccountNumber(),form.getAmount());
        return "redirect:/home";
    }
}
