package com.banking.model;

import java.time.LocalDateTime;



public record Transaction(Money amount, Money balanceAfter, LocalDateTime date) {}