package com.example.FinanceApp.controller;

import com.example.FinanceApp.dto.transaction.TransactionRequest;
import com.example.FinanceApp.dto.transaction.TransactionResponse;
import com.example.FinanceApp.dto.transaction.TransactionUpdateRequest;
import com.example.FinanceApp.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody TransactionRequest request, HttpSession session){
        return new ResponseEntity<>(transactionService.create(request,session), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAll(HttpSession session){
        return ResponseEntity.ok(transactionService.getAll(session));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> update(
            @PathVariable Long id, @RequestBody TransactionUpdateRequest request, HttpSession session
    ){
        return new ResponseEntity<>(transactionService.update(id,request,session),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id,
            HttpSession session
    ){
        transactionService.delete(id,session);
        return ResponseEntity.ok("Transaction Deleted Successfully");
    }


}
