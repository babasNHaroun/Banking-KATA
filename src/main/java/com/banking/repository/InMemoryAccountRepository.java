package com.banking.repository;

import com.banking.model.Account;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;


@Repository
public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account findById(String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getAccountId(), account);
    }
}