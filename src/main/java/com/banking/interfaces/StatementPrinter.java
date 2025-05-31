package com.banking.interfaces;

import java.util.List;
import com.banking.model.Transaction;

public interface StatementPrinter {
    String print(List<Transaction> transactions);
}
