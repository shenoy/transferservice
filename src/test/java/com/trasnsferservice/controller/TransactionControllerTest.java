package com.trasnsferservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trasnsferservice.domain.Account;
import com.trasnsferservice.domain.Transaction;
import com.trasnsferservice.repository.AccountsRepository;
import com.trasnsferservice.repository.TransactionRepository;
import com.trasnsferservice.service.TransferService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AccountsRepository accountsRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TransferService transferService;



    @Test
    void whenValidInput_thenReturns200()  throws Exception {
             mockMvc.perform(post("/transfer/source/{accountNumber1}/destination/{accountNumber2}/amount/{amount}",
                        12345678, 87654321,200)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenNullValue_thenReturns400() throws Exception {
        mockMvc.perform(post("/transfer/source/{accountNumber1}/destination/{accountNumber2}/amount/{amount}",
                        12345678, 87654321,"null")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidInput_thenReturnsTransaction() throws Exception {

        Transaction transaction =  new Transaction(12345678l, 87654321l,BigDecimal.valueOf(200));
        transaction.setId(1l);

        when(transferService.saveTransaction(any())).thenReturn(transaction);

        MvcResult result = mockMvc.perform(post("/transfer/source/{accountNumber1}/destination/{accountNumber2}/amount/{amount}",
                        12345678, 87654321,200)
                        .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assertions.assertEquals(content, objectMapper.writeValueAsString(transaction));

    }

    @Test
    public void makeTransactionAPI() throws Exception
    {

        Transaction transaction =  new Transaction(12345678l, 87654321l,BigDecimal.valueOf(200));
        transaction.setId(1l);

        when(transferService.saveTransaction(any())).thenReturn(transaction);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/transfer")
                        .content(objectMapper.writeValueAsString(new Transaction( 12345678l, 87654321l, BigDecimal.valueOf(200))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assertions.assertEquals(content, objectMapper.writeValueAsString(transaction));
    }

}