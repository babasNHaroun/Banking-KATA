package com.banking.api.mapper;

import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.api.dto.AccountResponseDTO;
import com.banking.api.dto.TransactionResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {
    public static AccountResponseDTO toDto(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountId(account.getAccountId());
        dto.setBalance(account.getBalance().getValue());
        List<TransactionResponseDTO> txDtos = account.getTransactions().stream()
            .map(AccountMapper::toDto)
            .collect(Collectors.toList());
        dto.setTransactions(txDtos);
        return dto;
    }

    public static TransactionResponseDTO toDto(Transaction tx) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setDate(tx.date().toString());
        dto.setAmount(tx.amount().getValue());
        dto.setBalanceAfter(tx.balanceAfter().getValue());
        return dto;
    }
}