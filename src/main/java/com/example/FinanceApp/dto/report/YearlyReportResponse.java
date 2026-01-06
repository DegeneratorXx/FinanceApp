package com.example.FinanceApp.dto.report;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public class YearlyReportResponse {
    public int year;
    public Map<String, BigDecimal> totalIncome;
    public Map<String, BigDecimal> totalExpense;
    public BigDecimal netSavings;

}
