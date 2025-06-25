package com.banking.repository;

import com.banking.domain.Account;

public interface AccountRepository {
    Account findById(String accountId);
    void save(Account account);
}