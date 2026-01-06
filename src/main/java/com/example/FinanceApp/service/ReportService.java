package com.example.FinanceApp.service;

import com.example.FinanceApp.dto.report.MonthlyReportResponse;
import com.example.FinanceApp.dto.report.YearlyReportResponse;
import com.example.FinanceApp.dto.transaction.TransactionRequest;
import com.example.FinanceApp.entity.CategoryType;
import com.example.FinanceApp.entity.Transaction;
import com.example.FinanceApp.entity.User;
import com.example.FinanceApp.repository.TransactionRepository;
import com.example.FinanceApp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public ReportService(TransactionRepository transactionRepository, UserRepository userRepository){
        this.transactionRepository=transactionRepository;
        this.userRepository=userRepository;
    }

    private User getLoggedInUser(HttpSession session) {
        Object id = session.getAttribute("USER_ID");
        System.out.println("SESSION USER_ID = " + id);

        if (id == null) {
            throw new RuntimeException("Unauthorized");
        }

        return userRepository.findById((Long) id)
                .orElseThrow(() -> new RuntimeException("Unauthorized"));
    }

    public MonthlyReportResponse montlyReport(int year,int month,HttpSession session){
        User user= getLoggedInUser(session);

        LocalDate start=LocalDate.of(year,month,1);
        LocalDate end= start.withDayOfMonth(start.lengthOfMonth());

        List<Transaction> transactions=transactionRepository.findByUserIdAndDateBetween(
                user.getId(),start,end);

        Map<String, BigDecimal> incomeMap= new HashMap<>();
        Map<String, BigDecimal> expenseMap= new HashMap<>();

        BigDecimal incomeTotal=BigDecimal.ZERO;
        BigDecimal expenseTotal=BigDecimal.ZERO;

        for(Transaction t: transactions){
            String category=t.getCategory().getName();

            if(t.getCategory().getType()== CategoryType.INCOME){
                incomeMap
                        .put(category,incomeMap.getOrDefault(category,BigDecimal.ZERO)
                                .add(t.getAmount()));
                incomeTotal=incomeTotal.add(t.getAmount());
            }
            else{
                expenseMap
                        .put(category, expenseMap.getOrDefault(category,BigDecimal.ZERO)
                                .add(t.getAmount()));
                expenseTotal=expenseTotal.add(t.getAmount());
            }
        }

        MonthlyReportResponse response=MonthlyReportResponse.builder()
                .month(month)
                .year(year)
                .totalIncome(incomeMap)
                .totalExpenses(expenseMap)
                .netSavings(incomeTotal.subtract(expenseTotal))
                .build();

        return response;

    }

    public YearlyReportResponse yearlyReport(int year,HttpSession session){
        User user=getLoggedInUser(session);
        LocalDate start=LocalDate.of(year,1,1);
        LocalDate end=LocalDate.of(year,12,31);

        List<Transaction> transactions=transactionRepository.findByUserIdAndDateBetween(
                user.getId(),start,end);

        Map<String, BigDecimal> incomeMap= new HashMap<>();
        Map<String, BigDecimal> expenseMap= new HashMap<>();

        BigDecimal incomeTotal=BigDecimal.ZERO;
        BigDecimal expenseTotal=BigDecimal.ZERO;

        for(Transaction t: transactions){
            String category=t.getCategory().getName();

            if(t.getCategory().getType()== CategoryType.INCOME){
                incomeMap
                        .put(category,incomeMap.getOrDefault(category,BigDecimal.ZERO)
                                .add(t.getAmount()));
                incomeTotal=incomeTotal.add(t.getAmount());
            }
            else{
                expenseMap
                        .put(category, expenseMap.getOrDefault(category,BigDecimal.ZERO)
                                .add(t.getAmount()));
                expenseTotal=expenseTotal.add(t.getAmount());
            }
        }
        YearlyReportResponse response=YearlyReportResponse.builder()
                .year(year)
                .totalIncome(incomeMap)
                .totalExpense(expenseMap)
                .netSavings(incomeTotal.subtract(expenseTotal))
                .build();

        return response;
    }

}
