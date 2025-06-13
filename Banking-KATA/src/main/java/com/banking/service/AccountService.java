package com.banking.service;

import com.banking.model.Account;
import com.banking.model.Money;
import com.banking.model.Transaction;
import com.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String accountId) {
        if (accountRepository.findById(accountId) != null) {
            throw new IllegalArgumentException("Account already exists with id: " + accountId);
        }
        Account account = new Account(accountId, Clock.systemUTC());
        accountRepository.save(account);
        return account;
    }

    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId);
    }

    public Account deposit(String accountId, Money amount) {
        Account account = accountRepository.findById(accountId);
        if (account == null) throw new IllegalArgumentException("Account not found");
        account.deposit(amount);
        accountRepository.save(account);
        return account;
    }

    public Account withdraw(String accountId, Money amount) {
        Account account = accountRepository.findById(accountId);
        if (account == null) throw new IllegalArgumentException("Account not found");
        account.withdraw(amount);
        accountRepository.save(account);
        return account;
    }

    public List<Transaction> getTransactions(String accountId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) return null;
        return account.getTransactions();
    }
}