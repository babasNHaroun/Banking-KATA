package com.banking.api.dto;

import lombok.Data;

@Data 
public class TransactionResponseDTO {
    private String date;
    private String amount;
    private String balanceAfter;
}