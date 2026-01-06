package com.example.FinanceApp.controller;

import com.example.FinanceApp.dto.goal.SavingsGoalRequest;
import com.example.FinanceApp.dto.goal.SavingsGoalResponse;
import com.example.FinanceApp.dto.goal.SavingsGoalUpdateRequest;
import com.example.FinanceApp.service.SavingsGoalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class SavingGoalController {

    private SavingsGoalService goalService;
    public SavingGoalController(SavingsGoalService goalService){
        this.goalService=goalService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingsGoalResponse> getOne(@PathVariable Long id, HttpSession session){
        return new ResponseEntity<>(goalService.getOne(id,session), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SavingsGoalResponse>> getAll(HttpSession session){
        return new ResponseEntity<>(goalService.getAll(session), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavingsGoalResponse> update(@PathVariable Long id, @RequestBody SavingsGoalUpdateRequest request, HttpSession session){
        return new ResponseEntity<>(goalService.update(id,request,session),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SavingsGoalResponse> create(@RequestBody SavingsGoalRequest request,HttpSession session){
        return new ResponseEntity<>(goalService.create(request,session),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,HttpSession session){
        goalService.delete(id, session);
        return new ResponseEntity<>("Goal Deleted Successfully",HttpStatus.OK);
    }
}
