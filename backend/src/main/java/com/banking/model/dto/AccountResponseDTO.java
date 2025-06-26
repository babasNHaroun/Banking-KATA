package com.banking.model.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class AccountResponseDTO {
    private String accountId;
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal balance;
    private List<TransactionResponseDTO> transactions;
    
    public AccountResponseDTO() {}
    
    public String getAccountId() {
        return accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public List<TransactionResponseDTO> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(List<TransactionResponseDTO> transactions) {
        this.transactions = transactions;
    }
}