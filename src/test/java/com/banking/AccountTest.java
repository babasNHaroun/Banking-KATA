package com.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void shouldHaveZeroBalanceWhenCreated() {
        assertEquals(0, account.getBalance());
    }

    @Test
    void shouldIncreaseBalanceWhenDepositing() {
        account.deposit(1000);
        assertEquals(1000, account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenDepositingNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-1000));
    }

     @Test
    void shouldDecreaseBalanceWhenWithdrawing() {
        account.deposit(1000);
        account.withdraw(300);
        assertEquals(700, account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-1000));
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingMoreThanBalance() {
        account.deposit(500);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(1000));
    }
}