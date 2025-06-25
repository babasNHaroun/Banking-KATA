package com.banking.model.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class AccountResponseDTO {
    private String accountId;
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal balance;
    private List<TransactionResponseDTO> transactions;
}