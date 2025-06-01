package com.banking.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.banking.model.Transaction;
import com.banking.interfaces.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account implements BankAccount {
    private BigDecimal balance;
    private final List<Transaction> transactions;
    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    private boolean isNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public synchronized BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        synchronized (this) {
            return Collections.unmodifiableList(new ArrayList<>(transactions));
        }
    }

    @Override
    public synchronized void deposit(BigDecimal amount) {
        logger.info("Depositing amount: {}", amount);
        if (isNegative(amount)) {
            throw new IllegalArgumentException("Cannot deposit a negative amount");
        }
        this.balance = this.balance.add(amount);
        transactions.add(new Transaction(amount, balance));
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) {
        logger.info("Withdrawing amount: {}", amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        }
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
        transactions.add(new Transaction(amount.negate(), balance));
    }

}