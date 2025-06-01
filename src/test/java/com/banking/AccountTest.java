package com.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.banking.service.Account;
import com.banking.service.BankStatementPrinter;
import com.banking.utils.Constants;
import com.banking.interfaces.StatementPrinter;
import com.banking.model.Transaction;

public class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void shouldHaveZeroBalanceWhenCreated() {
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void shouldIncreaseBalanceWhenDepositing() {
        account.deposit(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenDepositingNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.valueOf(-1000)));
    }

    @Test
    void shouldDecreaseBalanceWhenWithdrawing() {
        account.deposit(BigDecimal.valueOf(1000));
        account.withdraw(BigDecimal.valueOf(300));
        assertEquals(BigDecimal.valueOf(700), account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.valueOf(-1000)));
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingMoreThanBalance() {
        account.deposit(BigDecimal.valueOf(500));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.valueOf(1000)));
    }

    @Test
    void shouldPrintStatementWithNoTransactions() {
        StatementPrinter printer = new BankStatementPrinter();
        String statement = printer.print(account.getTransactions());
        assertTrue(statement.contains(Constants.STATEMENT_HEADER));
    }

    @Test
    void shouldPrintStatementWithTheRightTransactions() {
        StatementPrinter printer = new BankStatementPrinter();
        account.deposit(BigDecimal.valueOf(1000));
        account.withdraw(BigDecimal.valueOf(300));
        account.deposit(BigDecimal.valueOf(2000));

        String statement = printer.print(account.getTransactions());

        assertTrue(statement.contains(Constants.STATEMENT_HEADER));
        assertTrue(statement.contains(BigDecimal.valueOf(1000).toString()));
        assertTrue(statement.contains(BigDecimal.valueOf(-300).toString()));
        assertTrue(statement.contains(BigDecimal.valueOf(2000).toString()));
    }

    @Test
    void shouldAllowWithdrawalOfFullBalance() {
        account.deposit(new BigDecimal("1000"));
        account.withdraw(new BigDecimal("1000"));
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void shouldMaintainTransactionOrder() {
        account.deposit(BigDecimal.TEN);
        account.withdraw(BigDecimal.ONE);
        List<Transaction> transactions = account.getTransactions();

        assertTrue(transactions.get(0).getAmount().compareTo(BigDecimal.TEN) == 0);
        assertTrue(transactions.get(1).getAmount().compareTo(BigDecimal.ONE.negate()) == 0);
    }

    @Test
    void shouldMaintainConsistencyAfterFailedWithdrawal() {
        account.deposit(new BigDecimal("1000"));
        try {
            account.withdraw(new BigDecimal("2000"));
        } catch (IllegalArgumentException ex) {
        }

        assertEquals(new BigDecimal("1000"), account.getBalance());
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void shouldHandleConcurrentDeposits() throws InterruptedException {
        Runnable depositRunnable = () -> account.deposit(BigDecimal.valueOf(1000));

        Thread t1 = new Thread(depositRunnable);
        Thread t2 = new Thread(depositRunnable);
        Thread t3 = new Thread(depositRunnable);

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        assertEquals(new BigDecimal("3000"), account.getBalance());
    }

    @Test
    void shouldFailWhenConcurrentWithdrawalsExceedBalance() throws IllegalArgumentException, InterruptedException {
        Account account = new Account();
        account.deposit(new BigDecimal("2000"));

        int threadsNumber = 3;
        AtomicInteger successCounter = new AtomicInteger();
        AtomicInteger failureCounter = new AtomicInteger();
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        for (int i = 0; i < threadsNumber; i++) {
            executor.execute(() -> {
                try {
                    account.withdraw(new BigDecimal("1000"));
                    successCounter.incrementAndGet();
                } catch (IllegalArgumentException e) {
                    failureCounter.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        assertEquals(2, successCounter.get());
        assertEquals(1, failureCounter.get());
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

}