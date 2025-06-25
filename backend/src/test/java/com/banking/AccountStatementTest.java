package com.banking;

import com.banking.domain.Account;
import com.banking.domain.Money;
import com.banking.service.StatementPrinter;
import com.banking.service.impl.BankStatementPrinter;
import com.banking.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

public class AccountStatementTest {
    private Account account;
    private StatementPrinter printer;

    @BeforeEach
    void setUp() {
        account = new Account("test-id", Clock.systemUTC());
        printer = new BankStatementPrinter();
    }

    @Test
    void shouldPrintStatementWithNoTransactions() {
        String statement = printer.print(account.getTransactions());
        assertTrue(statement.contains(Constants.STATEMENT_HEADER));
    }

    @Test
    void shouldPrintStatementWithTheRightTransactions() {
        account.deposit(new Money(BigDecimal.valueOf(1000)));
        account.withdraw(new Money(BigDecimal.valueOf(300)));
        account.deposit(new Money(BigDecimal.valueOf(2000)));

        String statement = printer.print(account.getTransactions());

        assertTrue(statement.contains(Constants.STATEMENT_HEADER));
        assertTrue(statement.contains("1000.00"));
        assertTrue(statement.contains("-300.00"));
        assertTrue(statement.contains("2000.00"));
    }
}