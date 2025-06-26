package com.banking.service;

import com.banking.domain.Account;
import com.banking.domain.Money;
import com.banking.domain.Transaction;
import com.banking.repository.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String accountId) {
        logger.info("Creating account with id: {}", accountId);
        if (accountRepository.findById(accountId) != null) {
            logger.warn("Account already exists with id: {}", accountId);
            throw new IllegalArgumentException("Account already exists with id: " + accountId);
        }
        final Account account = new Account(accountId, Clock.systemUTC());
        accountRepository.save(account);
        logger.info("Account created with id: {}", accountId);
        return account;
    }

    public Optional<Account> getAccount(String accountId) {
        logger.info("Fetching account with id: {}", accountId);
        return Optional.ofNullable(accountRepository.findById(accountId));
    }

    public Account deposit(String accountId, Money amount) {
        logger.info("Depositing {} to account {}", amount, accountId);
        final Account account = getAccountOrThrow(accountId);
        account.deposit(amount);
        accountRepository.save(account);
        logger.info("Deposit successful for account {}", accountId);
        return account;
    }

    public Account withdraw(String accountId, Money amount) {
        logger.info("Withdrawing {} from account {}", amount, accountId);
        final Account account = getAccountOrThrow(accountId);
        account.withdraw(amount);
        accountRepository.save(account);
        logger.info("Withdraw successful for account {}", accountId);
        return account;
    }

    public List<Transaction> getTransactions(String accountId) {
        logger.info("Fetching transactions for account {}", accountId);
        final Account account = getAccountOrThrow(accountId);
        List<Transaction> transactions = account.getTransactions();
        if (transactions == null) {
            logger.info("No transactions found for account {}", accountId);
            transactions = List.of(); 
        }
        logger.info("Found {} transactions for account {}", transactions.size(), accountId);
        return transactions;
    }

    private Account getAccountOrThrow(String accountId) {
      return Optional.ofNullable(accountRepository.findById(accountId))
        .orElseThrow(() -> {
            logger.warn("Account not found: {}", accountId);
            return new IllegalArgumentException("Account not found");
        });
   }
}