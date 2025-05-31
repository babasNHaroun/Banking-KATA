package com.banking;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private int balance;
    private final List<Transaction> transactions;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot deposit a negative amount");
        }
        this.balance += amount;
        transactions.add(new Transaction(amount, balance));
    }

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

    
    public String printStatement() {
        StringBuilder statement = new StringBuilder("DATE | AMOUNT | BALANCE\n");
        for (Transaction transaction : transactions) {
            statement.append(String.format("%s | %d | %d\n",
                    transaction.getDate().format(DATE_FORMATTER),
                    transaction.getAmount(),
                    transaction.getBalance()));
        }
        return statement.toString();
    }


}