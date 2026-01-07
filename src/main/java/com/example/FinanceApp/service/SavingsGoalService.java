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
import java.math.RoundingMode;
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
        if (request.targetAmount == null || request.targetAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid target amount");
        }

        if (request.targetDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Target date must be in the future");
        }

        LocalDate startDate =
                request.startDate != null ? request.startDate : LocalDate.now();

        if (startDate.isAfter(request.targetDate)) {
            throw new RuntimeException("Start date cannot be after target date");
        }

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

        List<Transaction> txns =
                transactionRepository.findByUserIdAndDateBetween(
                        user.getId(),
                        goal.getStartDate(),
                        LocalDate.now()
                );

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;

        for (Transaction t : txns) {
            if (t.getCategory().getType() == CategoryType.INCOME) {
                income = income.add(t.getAmount());
            } else {
                expense = expense.add(t.getAmount());
            }
        }

        BigDecimal progress = income.subtract(expense);
        if (progress.compareTo(BigDecimal.ZERO) < 0) {
            progress = BigDecimal.ZERO;
        }



        SavingsGoalResponse res = new SavingsGoalResponse();
        res.id = goal.getId();
        res.goalName = goal.getGoalName();
        res.targetAmount = goal.getTargetAmount();
        res.targetDate = goal.getTargetDate();
        res.startDate = goal.getStartDate();
        res.currentProgress = progress;
        res.remainingAmount = goal.getTargetAmount().subtract(progress).max(BigDecimal.ZERO);
        res.progressPercentage = progress.multiply(BigDecimal.valueOf(100))
                        .divide(goal.getTargetAmount(), 2, RoundingMode.HALF_UP).doubleValue();

        return res;
    }


}
