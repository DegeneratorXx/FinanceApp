package com.example.FinanceApp.dto.transaction;

import java.math.BigDecimal;

public class TransactionUpdateRequest {
    public BigDecimal amount;
    public Long categoryId;
    public String description;
}
