package com.banking.model;

import java.time.LocalDateTime;

public class Transaction {
    private final LocalDateTime date;
    private final int amount;
    private final int balance;

    public Transaction(int amount, int balance) {
        this.date = LocalDateTime.now();
        this.amount = amount;
        this.balance = balance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public int getBalance() {
        return balance;
    }
}