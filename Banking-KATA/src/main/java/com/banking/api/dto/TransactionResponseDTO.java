package com.banking.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data 
public class TransactionResponseDTO {
    private String date;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
}