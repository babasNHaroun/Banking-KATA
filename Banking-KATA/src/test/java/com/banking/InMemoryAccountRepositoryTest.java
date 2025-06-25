package com.banking;

import com.banking.domain.Account;
import com.banking.domain.Money;
import com.banking.repository.AccountRepository;
import com.banking.repository.InMemoryAccountRepository;
import com.banking.service.AccountService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryAccountRepositoryTest {

    @Test
    void endToEndAccountLifecycle() {
        AccountRepository repo = new InMemoryAccountRepository();
        AccountService service = new AccountService(repo);


        Account account = new Account("account-123", Clock.systemUTC());
        repo.save(account);

        // Deposit
        service.deposit("account-123", new Money(BigDecimal.valueOf(1000)));
        // Withdraw
        service.withdraw("account-123", new Money(BigDecimal.valueOf(300)));

        // Reload account and check
        Account accountLoaded = repo.findById("account-123");
        assertNotNull(accountLoaded);
        assertEquals(new Money(BigDecimal.valueOf(700)), accountLoaded.getBalance());
        assertEquals(2, accountLoaded.getTransactions().size());
    }
}