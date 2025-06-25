package com.banking.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AmountRequestDTO {
    private BigDecimal amount;
}