package com.trasnsferservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trasnsferservice.domain.Account;
import com.trasnsferservice.domain.Transaction;
import com.trasnsferservice.repository.AccountsRepository;
import com.trasnsferservice.repository.TransactionRepository;
import com.trasnsferservice.service.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountsController.class)
class AccountsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AccountsRepository accountsRepository;

    @MockBean
    TransferService transferService;



    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void whenValidInput_thenReturns200()  throws Exception {
        mockMvc.perform(post("/account")
                        .param("accountNumber", "12345678")
                        .param("balance", "10000")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidInput_thenReturnsAccount()  throws Exception {

        Account account =  new Account(12345678l,
                 BigDecimal.valueOf(200));

        when(accountsRepository.save(any())).thenReturn(account);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .param("accountNumber", "12345678")
                        .param("balance", "10000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(content, objectMapper.writeValueAsString(account));

    }

    @Test
    void whenNullValue_thenReturns400() throws Exception {
        mockMvc.perform(post("/account")
                        .param("accountNumber", "12345678")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAccountById() {
    }
}