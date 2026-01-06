package com.example.FinanceApp.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {
    public BigDecimal amount;
    public LocalDate date;
    public Long categoryId;
    public String description;
}
