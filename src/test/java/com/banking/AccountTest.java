package com.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.banking.service.Account;
import com.banking.service.BankStatementPrinter;
import com.banking.interfaces.StatementPrinter;


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

    @Test
    void shouldPrintStatementWithNoTransactions() {
        StatementPrinter printer = new BankStatementPrinter();
        String statement = printer.print(account.getTransactions());
        assertTrue(statement.contains("DATE | AMOUNT | BALANCE"));
    }

    @Test
    void shouldPrintStatementWithTheRightTransactions() {
        StatementPrinter printer = new BankStatementPrinter();

        account.deposit(1000);
        account.withdraw(300);
        account.deposit(2000);

        String statement = printer.print(account.getTransactions());
        assertTrue(statement.contains("DATE | AMOUNT | BALANCE"));
        assertTrue(statement.contains("1000"));
        assertTrue(statement.contains("-300"));
        assertTrue(statement.contains("2000"));
    }
}