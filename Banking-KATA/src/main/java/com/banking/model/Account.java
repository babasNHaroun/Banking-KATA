package com.banking.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;


public class Account {
    private final String accountId;
    private final List<Transaction> transactions = new ArrayList<>();
    private Money balance = new Money(BigDecimal.ZERO);
    private final Clock clock;

    public Account(String accountId, Clock clock) {
        this.accountId = accountId;
        this.clock = clock;
    }

    public String getAccountId() {
        return accountId;
    }

    public Money getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void deposit(Money amount) {
        if (amount.isNegative()) throw new IllegalArgumentException("Deposit must be positive");
        synchronized (this) {
            balance = balance.add(amount);
            transactions.add(new Transaction(amount, balance, LocalDateTime.now(clock)));
        }
    }

    public void withdraw(Money amount) {
        if (amount.isNegative()) throw new IllegalArgumentException("Withdrawal must be positive");
        if (balance.subtract(amount).isNegative()) throw new IllegalArgumentException("Insufficient funds");
        synchronized (this) {
           balance = balance.subtract(amount);
           transactions.add(new Transaction(new Money(amount.getValue().negate()), balance, LocalDateTime.now(clock)));
        }
    }
}