package com.banking;

import com.banking.model.Account;
import com.banking.model.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class AccountConcurrencyTest {

    @Test
    void shouldHandleConcurrentDeposits() throws InterruptedException {
        Account account = new Account("test-id", Clock.systemUTC());
        Runnable depositRunnable = () -> account.deposit(new Money(BigDecimal.valueOf(1000)));

        Thread t1 = new Thread(depositRunnable);
        Thread t2 = new Thread(depositRunnable);
        Thread t3 = new Thread(depositRunnable);

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        assertEquals(new Money(new BigDecimal("3000")), account.getBalance());
    }

    @Test
    void shouldFailWhenConcurrentWithdrawalsExceedBalance() throws InterruptedException {
        Account account = new Account("test-id-2", Clock.systemUTC());
        account.deposit(new Money(new BigDecimal("2000")));

        int threadsNumber = 3;
        AtomicInteger successCounter = new AtomicInteger();
        AtomicInteger failureCounter = new AtomicInteger();
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        for (int i = 0; i < threadsNumber; i++) {
            executor.execute(() -> {
                try {
                    account.withdraw(new Money(new BigDecimal("1000")));
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
        assertEquals(new Money(BigDecimal.ZERO), account.getBalance());
    }
}