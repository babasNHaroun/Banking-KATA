package com.banking.service;

import com.banking.model.Account;
import com.banking.model.Money;
import com.banking.repository.AccountRepository;

public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public void deposit(String accountId, Money amount) {
        Account account = repository.findById(accountId);
        account.deposit(amount);
        repository.save(account);
    }

    public void withdraw(String accountId, Money amount) {
        Account account = repository.findById(accountId);
        account.withdraw(amount);
        repository.save(account);
    }
}