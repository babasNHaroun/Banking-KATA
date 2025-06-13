package com.banking.repository;

import com.banking.model.Account;

public interface AccountRepository {
    Account findById(String accountId);
    void save(Account account);
}