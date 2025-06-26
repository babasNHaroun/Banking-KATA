package com.banking.model.dto;

import java.math.BigDecimal;

public class TransactionResponseDTO {
    private String date;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    
    public TransactionResponseDTO() {}
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }
    
    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
}