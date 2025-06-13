package com.banking.service.impl;

import java.time.format.DateTimeFormatter;
import com.banking.model.Transaction;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.banking.utils.Constants;
import org.springframework.stereotype.Service;
import com.banking.service.StatementPrinter;

@Service
public class BankStatementPrinter implements StatementPrinter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Logger logger = LoggerFactory.getLogger(BankStatementPrinter.class);

    @Override
    public String print(List<Transaction> transactions) {
        logger.info("Printing a statement ...");
        StringBuilder statement = new StringBuilder(Constants.STATEMENT_HEADER);
        for (Transaction transaction : transactions) {
            statement.append(String.format("%s | %s | %s\n",
                    transaction.date().format(DATE_FORMATTER),
                    transaction.amount(),
                    transaction.balanceAfter()));
        }
        return statement.toString();
    }

}
