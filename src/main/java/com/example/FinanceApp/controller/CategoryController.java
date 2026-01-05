package com.example.FinanceApp.controller;

import com.example.FinanceApp.dto.category.CategoryRequest;
import com.example.FinanceApp.dto.category.CategoryResponse;
import com.example.FinanceApp.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(HttpSession session){
        return ResponseEntity.ok(categoryService.getCategories(session));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody CategoryRequest request,
            HttpSession session){
        CategoryResponse response=categoryService.createCategory(request,session);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCategory(@PathVariable String name, HttpSession session){
        categoryService.deleteCategory(name,session);
        return new ResponseEntity<>("Category delete successfully",HttpStatus.OK);
    }
}
