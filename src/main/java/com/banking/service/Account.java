package com.banking.service;

import java.util.ArrayList;
import java.util.List;
import com.banking.model.Transaction;
import com.banking.interfaces.BankAccount;

public class Account implements BankAccount {
    private int balance;
    private final List<Transaction> transactions;

    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    @Override
    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public void deposit(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot deposit a negative amount");
        }
        this.balance += amount;
        transactions.add(new Transaction(amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance -= amount;
        transactions.add(new Transaction(-amount, balance));
    }

}