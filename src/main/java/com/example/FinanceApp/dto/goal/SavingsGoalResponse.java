package com.example.FinanceApp.dto.goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsGoalResponse {
    public Long id;
    public String goalName;
    public BigDecimal targetAmount;
    public LocalDate targetDate;
    public LocalDate startDate;
    public BigDecimal currentProgress;
    public double progressPercentage;
    public BigDecimal remainingAmount;
}
