package com.banking;

import com.banking.domain.Account;
import com.banking.domain.Money;
import com.banking.domain.Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountDomainTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("test-id", Clock.systemUTC());
    }

    @Test
    void shouldHaveZeroBalanceWhenCreated() {
        assertEquals(new Money(BigDecimal.ZERO), account.getBalance());
    }

    @Test
    void shouldIncreaseBalanceWhenDepositing() {
        account.deposit(new Money(BigDecimal.valueOf(1000)));
        assertEquals(new Money(BigDecimal.valueOf(1000)), account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenDepositingNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(new Money(BigDecimal.valueOf(-1000))));
    }

    @Test
    void shouldDecreaseBalanceWhenWithdrawing() {
        account.deposit(new Money(BigDecimal.valueOf(1000)));
        account.withdraw(new Money(BigDecimal.valueOf(300)));
        assertEquals(new Money(BigDecimal.valueOf(700)), account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(new Money(BigDecimal.valueOf(-1000))));
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingMoreThanBalance() {
        account.deposit(new Money(BigDecimal.valueOf(500)));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(new Money(BigDecimal.valueOf(1000))));
    }

    @Test
    void shouldAllowWithdrawalOfFullBalance() {
        account.deposit(new Money(new BigDecimal("1000")));
        account.withdraw(new Money(new BigDecimal("1000")));
        assertEquals(new Money(BigDecimal.ZERO), account.getBalance());
    }

    @Test
    void shouldMaintainTransactionOrder() {
        account.deposit(new Money(BigDecimal.TEN));
        account.withdraw(new Money(BigDecimal.ONE));
        List<Transaction> transactions = account.getTransactions();

        assertEquals(new Money(BigDecimal.TEN), transactions.get(0).amount());
        assertEquals(new Money(BigDecimal.ONE.negate()), transactions.get(1).amount());
    }

    @Test
    void shouldMaintainConsistencyAfterFailedWithdrawal() {
        account.deposit(new Money(new BigDecimal("1000")));
        try {
            account.withdraw(new Money(new BigDecimal("2000")));
        } catch (IllegalArgumentException ex) {
            // expected
        }
        assertEquals(new Money(new BigDecimal("1000")), account.getBalance());
        assertEquals(1, account.getTransactions().size());
    }
}