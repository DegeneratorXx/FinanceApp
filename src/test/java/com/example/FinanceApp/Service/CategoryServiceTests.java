package com.example.FinanceApp.Service;

import com.example.FinanceApp.dto.category.CategoryResponse;
import com.example.FinanceApp.entity.Category;
import com.example.FinanceApp.entity.CategoryType;
import com.example.FinanceApp.entity.User;
import com.example.FinanceApp.repository.CategoryRepository;
import com.example.FinanceApp.repository.TransactionRepository;
import com.example.FinanceApp.repository.UserRepository;
import com.example.FinanceApp.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryServiceTests {

    CategoryRepository categoryRepository = mock(CategoryRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    TransactionRepository transactionRepository =mock(TransactionRepository.class);

    CategoryService categoryService =
            new CategoryService(categoryRepository, userRepository,transactionRepository);

    @Test
    void getCategories_success() {

        User user = new User();
        user.setId(1L);

        Category salary = new Category("Salary", CategoryType.INCOME, false, null);
        Category food = new Category("Food", CategoryType.EXPENSE, false, null);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("USER_ID")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findByUserIdOrUserIsNull(1L))
                .thenReturn(List.of(salary, food));

        List<CategoryResponse> result =
                categoryService.getCategories(session);

        assertEquals(2, result.size());
        assertEquals("Salary", result.get(0).name);
    }
}
