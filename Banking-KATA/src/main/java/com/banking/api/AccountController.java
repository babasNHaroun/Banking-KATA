package com.banking.api;

import com.banking.model.Account;
import com.banking.model.Money;
import com.banking.model.Transaction;
import com.banking.service.AccountService;
import com.banking.service.StatementPrinter;
import com.banking.api.dto.AccountCreateRequestDTO;
import com.banking.api.dto.AmountRequestDTO;
import com.banking.api.dto.TransactionResponseDTO;
import com.banking.api.mapper.AccountMapper;

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

    private ResponseEntity<String> accountNotFound(String accountId) {
        logger.warn("Account not found: {}", accountId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No account found with id: " + accountId);
    }

    // Create Account
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountCreateRequestDTO request) {
        logger.info("Received request to create account with id: {}", request.getAccountId());
        Account account = accountService.createAccount(request.getAccountId());
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountMapper.toDto(account));
    }

    // Get Account Details
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable("accountId") String accountId) {
        logger.info("Received request to fetch account details for id: {}", accountId);
        Account account = accountService.getAccount(accountId);
        if (account == null) {
            return accountNotFound(accountId);
        }
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Deposit
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<?> deposit(@PathVariable("accountId") String accountId, @RequestBody AmountRequestDTO request) {
        logger.info("Received request to deposit to account {}", accountId);
        Account account = accountService.deposit(accountId, new Money(request.getAmount()));
        if (account == null) {
            return accountNotFound(accountId);
        }
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Withdraw
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable("accountId") String accountId, @RequestBody AmountRequestDTO request) {
        logger.info("Received request to withdraw from account {}", accountId);
        Account account = accountService.withdraw(accountId, new Money(request.getAmount()));
        if (account == null) {
            return accountNotFound(accountId);
        }
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Get Transactions
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable("accountId") String accountId) {
        logger.info("Received request to fetch transactions for account {}", accountId);
        List<Transaction> transactions = accountService.getTransactions(accountId);
        if (transactions == null) {
            return accountNotFound(accountId);
        }
        List<TransactionResponseDTO> dtos = transactions.stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Get Statement
    @GetMapping("/{accountId}/statement")
    public ResponseEntity<?> getStatement(@PathVariable("accountId") String accountId) {
        logger.info("Received request to fetch statement for account {}", accountId);
        List<Transaction> transactions = accountService.getTransactions(accountId);
        if (transactions == null) {
            return accountNotFound(accountId);
        }
        String statement = statementPrinter.print(transactions);
        return ResponseEntity.ok(statement);
    }
}