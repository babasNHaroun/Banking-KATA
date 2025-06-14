package com.banking.api.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AmountRequestDTO {
    private BigDecimal amount;
}