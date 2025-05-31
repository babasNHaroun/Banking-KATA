package com.banking;

public class Account {
    private int balance;

    public Account() {
        this.balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot deposit a negative amount");
        }
        this.balance += amount;
    }
}