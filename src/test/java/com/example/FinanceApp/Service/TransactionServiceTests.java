package com.example.FinanceApp.Service;
import com.example.FinanceApp.dto.transaction.TransactionRequest;
import com.example.FinanceApp.entity.Category;
import com.example.FinanceApp.entity.CategoryType;
import com.example.FinanceApp.entity.Transaction;
import com.example.FinanceApp.entity.User;
import com.example.FinanceApp.repository.*;
import com.example.FinanceApp.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
public class TransactionServiceTests {
    TransactionRepository transactionRepository = mock(TransactionRepository.class);
    CategoryRepository categoryRepository = mock(CategoryRepository.class);
    UserRepository userRepository = mock(UserRepository.class);

    TransactionService service =
            new TransactionService(transactionRepository, categoryRepository, userRepository);

    @Test
    void create_transaction_success() {

        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setType(CategoryType.INCOME);

        TransactionRequest req = new TransactionRequest();
        req.amount = BigDecimal.valueOf(5000);
        req.date = LocalDate.now();
        req.categoryId = 1L;

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("USER_ID")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        service.create(req, session);

        verify(transactionRepository).save(any(Transaction.class));
    }
}
