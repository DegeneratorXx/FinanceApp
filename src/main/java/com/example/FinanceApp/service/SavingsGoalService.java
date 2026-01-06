package com.example.FinanceApp.service;

import com.example.FinanceApp.dto.goal.SavingsGoalRequest;
import com.example.FinanceApp.dto.goal.SavingsGoalResponse;
import com.example.FinanceApp.dto.goal.SavingsGoalUpdateRequest;
import com.example.FinanceApp.entity.CategoryType;
import com.example.FinanceApp.entity.SavingsGoal;
import com.example.FinanceApp.entity.Transaction;
import com.example.FinanceApp.entity.User;
import com.example.FinanceApp.repository.SavingsGoalRepository;
import com.example.FinanceApp.repository.TransactionRepository;
import com.example.FinanceApp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsGoalService {

    private final SavingsGoalRepository goalRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public SavingsGoalService(SavingsGoalRepository goalRepository,
                              TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
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

    public SavingsGoalResponse create(SavingsGoalRequest request,HttpSession session){
    User user = getLoggedInUser(session);
    SavingsGoal goal= SavingsGoal.builder()
            .goalName(request.goalName)
            .targetAmount(request.targetAmount)
            .targetDate(request.targetDate)
            .startDate(request.startDate!=null?request.startDate: LocalDate.now())
            .build();

    goal.setUser(user);
    goalRepository.save(goal);
    return map(goal,user);
    }

    public List<SavingsGoalResponse> getAll(HttpSession session){
        User user= getLoggedInUser(session);
        return goalRepository.findByUserId(user.getId())
                .stream()
                .map(g->map(g,user))
                .collect(Collectors.toList());
    }


    public SavingsGoalResponse getOne(Long id,HttpSession session){
        User user= getLoggedInUser(session);

        SavingsGoal goal= goalRepository.findById(id).orElseThrow(()->new RuntimeException("Gold not found, Invalid ID"));

        if(!goal.getUser().getId().equals(user.getId()))
            throw new RuntimeException("Forbidden");

        return map(goal,user);
    }

    public SavingsGoalResponse update(Long id, SavingsGoalUpdateRequest request, HttpSession session){
        User user=getLoggedInUser(session);

        SavingsGoal goal= goalRepository.findById(id).orElseThrow(()->new RuntimeException("Gold not found, Invalid ID"));

        if(!goal.getUser().getId().equals(user.getId()))
            throw new RuntimeException("Forbidden");

        if(request.targetAmount!=null)
            goal.setTargetAmount(request.targetAmount);
        if(request.targetDate!=null)
            goal.setTargetDate(request.targetDate);

        goalRepository.save(goal);
        return map(goal,user);
    }

    public void delete(Long id,HttpSession session){
        User user=getLoggedInUser(session);
        SavingsGoal goal=goalRepository.findById(id).orElseThrow(()->new RuntimeException("Goal Not Found"));

        if (!goal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        goalRepository.delete(goal);
    }

    //helper func

    private SavingsGoalResponse map(SavingsGoal goal, User user) {

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        List<Transaction> transactions =
                transactionRepository.findByUserIdAndDateBetween(
                        user.getId(),
                        goal.getStartDate(),
                        LocalDate.now()
                );

        for (Transaction t : transactions) {
            if (t.getCategory().getType() == CategoryType.INCOME) {
                totalIncome = totalIncome.add(t.getAmount());
            } else {
                totalExpense = totalExpense.add(t.getAmount());
            }
        }

        BigDecimal progress = totalIncome.subtract(totalExpense);
        if (progress.compareTo(BigDecimal.ZERO) < 0) {
            progress = BigDecimal.ZERO;
        }

        BigDecimal remaining = goal.getTargetAmount().subtract(progress);
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            remaining = BigDecimal.ZERO;
        }

        double percentage =
                (progress.doubleValue() / goal.getTargetAmount().doubleValue()) * 100;

        SavingsGoalResponse res = new SavingsGoalResponse();
        res.id = goal.getId();
        res.goalName = goal.getGoalName();
        res.targetAmount = goal.getTargetAmount();
        res.targetDate = goal.getTargetDate();
        res.startDate = goal.getStartDate();
        res.currentProgress = progress;
        res.remainingAmount = remaining;
        res.progressPercentage =
                Math.round(percentage * 100.0) / 100.0;

        return res;
    }


}
