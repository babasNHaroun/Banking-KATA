package com.banking.api;

import com.banking.model.Account;
import com.banking.model.Money;
import com.banking.model.Transaction;
import com.banking.service.AccountService;
import com.banking.service.StatementPrinter;
import com.banking.api.dto.AccountCreateRequestDTO;
import com.banking.api.dto.AmountRequestDTO;
import com.banking.api.dto.AccountResponseDTO;
import com.banking.api.dto.TransactionResponseDTO;
import com.banking.api.mapper.AccountMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final StatementPrinter statementPrinter;

    public AccountController(AccountService accountService, StatementPrinter statementPrinter) {
        this.accountService = accountService;
        this.statementPrinter = statementPrinter;
    }

    // Create Account
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountCreateRequestDTO request) {
        try {
            Account account = accountService.createAccount(request.getAccountId());
            return ResponseEntity.status(201).body(AccountMapper.toDto(account));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Get Account Details
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable("accountId") String accountId) {
        Account account = accountService.getAccount(accountId);
        if (account == null) {
            return ResponseEntity.status(404)
                .body("No account found with id: " + accountId);
        }
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

    // Deposit
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<?> deposit(@PathVariable("accountId") String accountId, @RequestBody AmountRequestDTO request) {
        try {
            Account account = accountService.deposit(accountId, new Money(new BigDecimal(request.getAmount())));
            if (account == null) {
                return ResponseEntity.status(404)
                    .body("No account found with id: " + accountId);
            }
            return ResponseEntity.ok(AccountMapper.toDto(account));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Withdraw
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable("accountId") String accountId, @RequestBody AmountRequestDTO request) {
        try {
            Account account = accountService.withdraw(accountId, new Money(new BigDecimal(request.getAmount())));
            if (account == null) { 
                return ResponseEntity.status(404)
                    .body("No account found with id: " + accountId);
            }
            return ResponseEntity.ok(AccountMapper.toDto(account));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Get Transactions
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable("accountId") String accountId) {
        List<Transaction> transactions = accountService.getTransactions(accountId);
        if (transactions == null) {
            return ResponseEntity.status(404)
                .body("No account found with id: " + accountId);
        }
        List<TransactionResponseDTO> dtos = transactions.stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Get Statement
    @GetMapping("/{accountId}/statement")
    public ResponseEntity<?> getStatement(@PathVariable("accountId") String accountId) {
        List<Transaction> transactions = accountService.getTransactions(accountId);
        if (transactions == null) {
            return ResponseEntity.status(404)
                .body("No account found with id: " + accountId);
        }
        String statement = statementPrinter.print(transactions);
        return ResponseEntity.ok(statement);
    }
}