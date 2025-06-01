package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final LocalDateTime date;
    private final BigDecimal amount;
    private final BigDecimal balance;

    public Transaction(BigDecimal amount, BigDecimal balance) {
        this.date = LocalDateTime.now();
        this.amount = amount;
        this.balance = balance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}