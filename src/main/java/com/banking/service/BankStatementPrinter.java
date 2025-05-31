package com.banking.service;

import com.banking.interfaces.StatementPrinter;
import java.time.format.DateTimeFormatter;
import com.banking.model.Transaction;
import java.util.List;

public class BankStatementPrinter implements StatementPrinter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public String print(List<Transaction> transactions) {
        StringBuilder statement = new StringBuilder("DATE | AMOUNT | BALANCE\n");
        for (Transaction transaction : transactions) {
            statement.append(String.format("%s | %d | %d\n",
                    transaction.getDate().format(DATE_FORMATTER),
                    transaction.getAmount(),
                    transaction.getBalance()));
        }
        return statement.toString();
    }

}
