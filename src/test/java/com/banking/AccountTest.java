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
        account.deposit(100);
        assertEquals(100, account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenDepositingNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
    }
}