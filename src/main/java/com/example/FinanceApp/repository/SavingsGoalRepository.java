package com.example.FinanceApp.repository;

import com.example.FinanceApp.entity.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface SavingsGoalRepository extends JpaRepository<SavingsGoal,Long> {
    List<SavingsGoal> findByUserId(Long userId);

}
