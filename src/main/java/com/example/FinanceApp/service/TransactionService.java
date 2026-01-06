package com.example.FinanceApp.service;

import com.example.FinanceApp.dto.transaction.TransactionRequest;
import com.example.FinanceApp.dto.transaction.TransactionResponse;
import com.example.FinanceApp.dto.transaction.TransactionUpdateRequest;
import com.example.FinanceApp.entity.Category;
import com.example.FinanceApp.entity.Transaction;
import com.example.FinanceApp.entity.User;
import com.example.FinanceApp.repository.CategoryRepository;
import com.example.FinanceApp.repository.TransactionRepository;
import com.example.FinanceApp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              CategoryRepository categoryRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
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

    public TransactionResponse create(TransactionRequest request, HttpSession session){
        User user= getLoggedInUser(session);

        if(request.date.isAfter(LocalDate.now()))
            throw new RuntimeException("FutureDate Not Allowed");

        Category category = categoryRepository.findById(request.categoryId)
                .orElseThrow(()->new RuntimeException("Invalid Category"));

        Transaction tx=Transaction.builder()
                .amount(request.amount)
                .date(request.date)
                .description(request.description)
                .category(category)
                .user(user)
                .build();

        transactionRepository.save(tx);
        return map(tx);
    }

    public List<TransactionResponse> getAll(HttpSession session){
        User user= getLoggedInUser(session);

        return transactionRepository
                .findByUserIdOrderByDateDesc(user.getId())
                .stream()
                .map(this::map)
                .collect(Collectors.toList());

    }

    public TransactionResponse update(Long id, TransactionUpdateRequest request,HttpSession session){
        User user=getLoggedInUser(session);
        Transaction tx= transactionRepository.findById(id).orElseThrow(()->new RuntimeException("Transaction Not Found"));

        if(!tx.getUser().getId().equals(user.getId()))
            throw new RuntimeException("Forbidden");

        if(request.amount!=null)
            tx.setAmount(request.amount);

        if(request.description!=null)
            tx.setDescription(request.description);

        if(request.categoryId!=null){
            Category category=categoryRepository.findById(request.categoryId).orElseThrow(()->new RuntimeException("Invalid category"));
            tx.setCategory(category);
        }

        transactionRepository.save(tx);
        return map(tx);
    }

    public void delete(Long id, HttpSession session){
        User user = getLoggedInUser(session);
        Transaction tx=transactionRepository.findById(id).orElseThrow(()->new RuntimeException("Transaction Not Found"));

        if(!tx.getUser().getId().equals(user.getId()))
            throw new RuntimeException("Forbidden");

        transactionRepository.delete(tx);

    }

    private TransactionResponse map(Transaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getAmount(),
                tx.getDate(),
                tx.getCategory().getName(),
                tx.getCategory().getType().name(),
                tx.getDescription()
        );
    }
}
