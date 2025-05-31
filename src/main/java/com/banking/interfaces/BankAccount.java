package com.banking.interfaces;

public interface BankAccount {
    int getBalance();

    void deposit(int amount);

    void withdraw(int amount);
}
