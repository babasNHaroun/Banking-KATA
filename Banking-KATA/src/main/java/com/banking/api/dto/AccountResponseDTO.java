package com.banking.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class AccountResponseDTO {
    private String accountId;
    private String balance;
    private List<TransactionResponseDTO> transactions;

    
}