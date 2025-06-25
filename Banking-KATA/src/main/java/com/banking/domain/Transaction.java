package com.banking.domain;

import java.time.LocalDateTime;



public record Transaction(Money amount, Money balanceAfter, LocalDateTime date) {}