package com.banking.service;

import java.util.List;

import com.banking.domain.Transaction;

public interface StatementPrinter {
    /**clear
     * 
     * Prints a statement of transactions.
     * 
     * @param transactions The list of transactions to print.
     * @return The statement as a string.
     */
    String print(List<Transaction> transactions);
}
