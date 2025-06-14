package com.banking.integration;

import com.banking.api.dto.AccountCreateRequestDTO;
import com.banking.api.dto.AmountRequestDTO;
import com.banking.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String accountId;

    @BeforeEach
    void setUp() throws Exception {
        accountId = "account-" + UUID.randomUUID();

        AccountCreateRequestDTO request = new AccountCreateRequestDTO();
        request.setAccountId(accountId);

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createAccount_shouldReturn201AndAccount() throws Exception {
        // Try creating a new unique account
        String newAccountId = "account-" + UUID.randomUUID();
        AccountCreateRequestDTO request = new AccountCreateRequestDTO();
        request.setAccountId(newAccountId);

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value(newAccountId));
    }

    @Test
    void deposit_shouldReturn200AndUpdatedAccount() throws Exception {
        AmountRequestDTO depositRequest = new AmountRequestDTO();
        depositRequest.setAmount(new BigDecimal("100.00"));
        mockMvc.perform(post("/accounts/" + accountId + "/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("100.00"));
    }

    @Test
    void withdraw_shouldReturn200AndUpdatedAccount() throws Exception {
        // Deposit first
        AmountRequestDTO depositRequest = new AmountRequestDTO();
        depositRequest.setAmount(new BigDecimal("200.00"));
        mockMvc.perform(post("/accounts/" + accountId + "/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk());

        // Then withdraw
        AmountRequestDTO withdrawRequest = new AmountRequestDTO();
        withdrawRequest.setAmount(new BigDecimal("50.00"));
        mockMvc.perform(post("/accounts/" + accountId + "/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("150.00"));
    }

    @Test
    void getAccount_shouldReturnAccountDetails() throws Exception {
        mockMvc.perform(get("/accounts/" + accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(accountId));
    }

    @Test
    void getTransactions_shouldReturnList() throws Exception {
        // Deposit to create a transaction
        AmountRequestDTO depositRequest = new AmountRequestDTO();
        depositRequest.setAmount(new BigDecimal("100.00"));
        mockMvc.perform(post("/accounts/" + accountId + "/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk());

        // Get transactions
        mockMvc.perform(get("/accounts/" + accountId + "/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getStatement_shouldReturnString() throws Exception {
        mockMvc.perform(get("/accounts/" + accountId + "/statement"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(Constants.STATEMENT_HEADER)));
    }
}