package org.mql.microservices.accounts_service.accounts_service.controllers;

import org.junit.jupiter.api.Test;
import org.mql.microservices.accounts_service.accounts_service.exceptions.AccountNotFoundException;
import org.mql.microservices.accounts_service.accounts_service.models.Account;
import org.mql.microservices.accounts_service.accounts_service.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;           // simulates HTTP requests

    @MockitoBean
    AccountService accountService;   // fake service

    @Test
    void getAccount_returns200() throws Exception {
        // Given: the fake service returns an account
        UUID id = UUID.randomUUID();
        Account account = new Account("Alice", new BigDecimal("42.00")); //
        ReflectionTestUtils.setField(account, "id", id); // set the private field "id" of the account
        when(accountService.getAccount(id)).thenReturn(account); // the fake service will return this account when called with this id

        // When + Then: GET /api/v1/accounts/{id} answers 200 with the right JSON
        mockMvc.perform(get("/api/v1/accounts/" + id)) // simulate a GET request to the API
                .andExpect(status().isOk()) // expect HTTP 200
                .andExpect(jsonPath("$.ownerName").value("Alice")) // expect the JSON field "ownerName" to be "Alice"
                .andExpect(jsonPath("$.balance").value(42.00)); // expect the JSON field "balance" to be 42.00
    }

    @Test
    void getAccount_returns404WhenNotFound() throws Exception {
        // Given: the fake service throws "not found"
        UUID id = UUID.randomUUID();
        when(accountService.getAccount(id)).thenThrow(new AccountNotFoundException(id));

        // When + Then: the API answers 404 with an error message
        mockMvc.perform(get("/api/v1/accounts/" + id)) 
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }
}
