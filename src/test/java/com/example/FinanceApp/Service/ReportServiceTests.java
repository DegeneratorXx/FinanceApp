package com.example.FinanceApp.Service;


import com.example.FinanceApp.dto.report.MonthlyReportResponse;
import com.example.FinanceApp.entity.*;
import com.example.FinanceApp.repository.TransactionRepository;
import com.example.FinanceApp.repository.UserRepository;
import com.example.FinanceApp.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportServiceTests {

    TransactionRepository transactionRepository = mock(TransactionRepository.class);
    UserRepository userRepository = mock(UserRepository.class);

    ReportService reportService =
            new ReportService(transactionRepository, userRepository);

    @Test
    void monthlyReport_success() {

        User user = new User();
        user.setId(1L);

        Transaction income = new Transaction();
        income.setAmount(BigDecimal.valueOf(3000));
        income.setCategory(new Category("Salary", CategoryType.INCOME, false, null));

        Transaction expense = new Transaction();
        expense.setAmount(BigDecimal.valueOf(500));
        expense.setCategory(new Category("Food", CategoryType.EXPENSE, false, null));

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("USER_ID")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(transactionRepository.findByUserIdAndDateBetween(
                eq(1L),
                any(LocalDate.class),
                any(LocalDate.class)
        )).thenReturn(List.of(income, expense));

        MonthlyReportResponse res =
                reportService.montlyReport(2026, 1, session);

        assertEquals(BigDecimal.valueOf(2500), res.netSavings);
        assertEquals(BigDecimal.valueOf(3000),
                res.totalIncome.get("Salary"));
        assertEquals(BigDecimal.valueOf(500),
                res.totalExpenses.get("Food"));
    }
}
