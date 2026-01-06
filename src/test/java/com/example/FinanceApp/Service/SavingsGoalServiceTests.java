package com.example.FinanceApp.Service;
import com.example.FinanceApp.entity.*;
import com.example.FinanceApp.repository.SavingsGoalRepository;
import com.example.FinanceApp.repository.TransactionRepository;
import com.example.FinanceApp.repository.UserRepository;
import com.example.FinanceApp.service.SavingsGoalService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class SavingsGoalServiceTests {

    SavingsGoalRepository goalRepo = mock(SavingsGoalRepository.class);
    TransactionRepository txnRepo = mock(TransactionRepository.class);
    UserRepository userRepo = mock(UserRepository.class);

    SavingsGoalService service =
            new SavingsGoalService(goalRepo, txnRepo, userRepo);

    @Test
    void calculate_progress_success() {

        User user = new User();
        user.setId(1L);

        SavingsGoal goal = new SavingsGoal();
        goal.setTargetAmount(BigDecimal.valueOf(5000));
        goal.setStartDate(LocalDate.of(2024, 1, 1));
        goal.setUser(user);

        Transaction income = new Transaction();
        income.setAmount(BigDecimal.valueOf(3000));
        income.setCategory(new Category("Salary", CategoryType.INCOME, false, null));

        Transaction expense = new Transaction();
        expense.setAmount(BigDecimal.valueOf(500));
        expense.setCategory(new Category("Food", CategoryType.EXPENSE, false, null));

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(goalRepo.findByUserId(1L)).thenReturn(List.of(goal));
        when(txnRepo.findByUserIdAndDateBetween(any(), any(), any()))
                .thenReturn(List.of(income, expense));

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("USER_ID")).thenReturn(1L);

        service.getAll(session);
    }
}