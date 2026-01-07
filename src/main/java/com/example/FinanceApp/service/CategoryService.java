package com.example.FinanceApp.service;

import com.example.FinanceApp.dto.category.CategoryRequest;
import com.example.FinanceApp.dto.category.CategoryResponse;
import com.example.FinanceApp.entity.Category;
import com.example.FinanceApp.entity.CategoryType;
import com.example.FinanceApp.entity.User;
import com.example.FinanceApp.repository.CategoryRepository;
import com.example.FinanceApp.repository.TransactionRepository;
import com.example.FinanceApp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository,TransactionRepository transactionRepository){
        this.categoryRepository=categoryRepository;
        this.userRepository=userRepository;
        this.transactionRepository=transactionRepository;
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

    public List<CategoryResponse> getCategories(HttpSession session){
        User user = getLoggedInUser(session);

        return categoryRepository
                .findByUserIdOrUserIsNull(user.getId())
                .stream()
                .map(c->new CategoryResponse(
                        c.getName(),
                        c.getType().name(),
                        c.isCustom()
                ))
                .collect(Collectors.toList());
    }

    public CategoryResponse createCategory(CategoryRequest request,HttpSession session){
        User user=getLoggedInUser(session);

        if(categoryRepository.existsByNameAndUserId(request.name,user.getId())){
            throw new RuntimeException("Category already exists");
        }

        Category category= new Category(
                request.name,
                CategoryType.valueOf(request.type),
                true,
                user
        );
        categoryRepository.save(category);

        return new CategoryResponse(
                category.getName(), category.getType().name(),
                true
        );
    }

    public void deleteCategory(String name,HttpSession session){
        User user = getLoggedInUser(session);
        boolean used =
                transactionRepository.existsByCategoryNameAndUserId(name, user.getId());

        if (used) {
            throw new RuntimeException("Category in use");
        }

        Category category = categoryRepository.findAll().stream()
                .filter(c->c.getName().equals(name))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Category not found"));

        if(!category.isCustom())
            throw new RuntimeException("Default category cannot be deleted");

        if(!category.getUser().getId().equals(user.getId()))
            throw new RuntimeException("Forbidden");

        categoryRepository.delete(category);
    }

}
