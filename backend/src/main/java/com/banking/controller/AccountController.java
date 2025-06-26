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
        final Account account = accountService.createAccount(request.getAccountId());
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountMapper.toDto(account));
    }

    // Get Account Details
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable("accountId") String accountId) {
        logger.info("Received request to fetch account details for id: {}", accountId);
        return accountService.getAccount(accountId)
                .map(account -> ResponseEntity.ok(AccountMapper.toDto(account)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Deposit
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponseDTO> deposit(@PathVariable("accountId") String accountId, @RequestBody AmountRequestDTO request) {
        logger.info("Received request to deposit to account {}", accountId);
        final Account account = accountService.deposit(accountId, new Money(request.getAmount()));
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Withdraw
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountResponseDTO> withdraw(@PathVariable("accountId") String accountId, @RequestBody AmountRequestDTO request) {
        logger.info("Received request to withdraw from account {}", accountId);
        final Account account = accountService.withdraw(accountId, new Money(request.getAmount()));
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Get Transactions
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(@PathVariable String accountId) {
        logger.info("Received request to fetch transactions for account {}", accountId);
        return ResponseEntity.ok(
            accountService.getTransactions(accountId).stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList())
        );
    }       

    // Get Statement
    @GetMapping("/{accountId}/statement")
    public ResponseEntity<String> getStatement(@PathVariable("accountId") String accountId) {
        logger.info("Received request to fetch statement for account {}", accountId);
        final List<Transaction> transactions = accountService.getTransactions(accountId);
        final String statement = statementPrinter.print(transactions);
        return ResponseEntity.ok(statement);
    }
}
