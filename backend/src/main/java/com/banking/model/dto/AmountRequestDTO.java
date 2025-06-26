package com.banking.model.dto;

import java.math.BigDecimal;

public class AmountRequestDTO {
    private BigDecimal amount;
    
    public AmountRequestDTO() {}
    
    public AmountRequestDTO(BigDecimal amount) {
        this.amount = amount;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}