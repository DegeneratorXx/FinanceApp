package com.example.FinanceApp.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportResponse {

    public int month;
    public int year;
    public Map<String, BigDecimal> totalIncome;
    public Map<String, BigDecimal> totalExpenses;
    public BigDecimal netSavings;
}
