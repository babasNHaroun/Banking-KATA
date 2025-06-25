package com.banking.controller;

import com.banking.service.AccountService;
import com.banking.service.StatementPrinter;
import com.banking.utils.AccountMapper;
import com.banking.domain.Account;
import com.banking.domain.Money;
import com.banking.domain.Transaction;
import com.banking.model.dto.AccountCreateRequestDTO;
import com.banking.model.dto.AccountResponseDTO;
import com.banking.model.dto.AmountRequestDTO;
import com.banking.model.dto.TransactionResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final StatementPrinter statementPrinter;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(AccountService accountService, StatementPrinter statementPrinter) {
        this.accountService = accountService;
        this.statementPrinter = statementPrinter;
    }

    // Create Account
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountCreateRequestDTO request) {
        logger.info("Received request to create account with id: {}", request.getAccountId());
        Account account = accountService.createAccount(request.getAccountId());
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountMapper.toDto(account));
    }

    // Get Account Details
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable("accountId") String accountId) {
        logger.info("Received request to fetch account details for id: {}", accountId);
        Account account = accountService.getAccount(accountId);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Deposit
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponseDTO> deposit(@PathVariable("accountId") String accountId, @RequestBody AmountRequestDTO request) {
        logger.info("Received request to deposit to account {}", accountId);
        Account account = accountService.deposit(accountId, new Money(request.getAmount()));
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Withdraw
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountResponseDTO> withdraw(@PathVariable("accountId") String accountId, @RequestBody AmountRequestDTO request) {
        logger.info("Received request to withdraw from account {}", accountId);
        Account account = accountService.withdraw(accountId, new Money(request.getAmount()));
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Get Transactions
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(@PathVariable("accountId") String accountId) {
        logger.info("Received request to fetch transactions for account {}", accountId);
        List<Transaction> transactions = accountService.getTransactions(accountId);
        if (transactions == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<TransactionResponseDTO> dtos = transactions.stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Get Statement
    @GetMapping("/{accountId}/statement")
    public ResponseEntity<String> getStatement(@PathVariable("accountId") String accountId) {
        logger.info("Received request to fetch statement for account {}", accountId);
        List<Transaction> transactions = accountService.getTransactions(accountId);
        if (transactions == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String statement = statementPrinter.print(transactions);
        return ResponseEntity.ok(statement);
    }
}
