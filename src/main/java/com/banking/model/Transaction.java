package com.banking.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Transaction {
    private final LocalDateTime date;
    private final Money amount;
    private final Money balanceAfter;

    public Transaction(Money amount, Money balance, LocalDateTime date) {
        this.amount = amount;
        this.balanceAfter = balance;
        this.date = date;
    }

    public Money getAmount() {
        return amount;
    }

    public Money getBalanceAfter() {
        return balanceAfter;
    }

      public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(date, transaction.date) &&
               Objects.equals(amount, transaction.amount) &&
               Objects.equals(balanceAfter, transaction.balanceAfter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, balanceAfter);
    }
}