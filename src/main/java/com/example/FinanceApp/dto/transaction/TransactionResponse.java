package com.example.FinanceApp.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {
    public Long id;
    public BigDecimal amount;
    public LocalDate date;
    public String category;
    public String type;
    public String description;

    public TransactionResponse(Long id, BigDecimal amount, LocalDate date, String category, String type, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.type = type;
        this.description = description;
    }
}
