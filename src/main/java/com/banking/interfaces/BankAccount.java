package com.banking.interfaces;

import java.math.BigDecimal;

public interface BankAccount {
    BigDecimal getBalance();

    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);
}
