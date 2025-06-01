package com.banking.interfaces;

import java.math.BigDecimal;

public interface BankAccount {
    /**
     * Returns the current balance of the account.
     * 
     * @return The current balance.
     */
    BigDecimal getBalance();

    /**
     * Deposits an amount into the account.
     * 
     * @param amount The amount to deposit.
     */
    void deposit(BigDecimal amount);

    /**
     * Withdraws an amount from the account.
     * 
     * @param amount The amount to withdraw.
     */
    void withdraw(BigDecimal amount);
}
