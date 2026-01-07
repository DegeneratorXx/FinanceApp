package com.example.FinanceApp.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    public BigDecimal amount;
    public LocalDate date;
    public String category;
    public String description;
}
