package com.example.FinanceApp.dto.goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsGoalUpdateRequest {
    public BigDecimal targetAmount;
    public LocalDate targetDate;
}
