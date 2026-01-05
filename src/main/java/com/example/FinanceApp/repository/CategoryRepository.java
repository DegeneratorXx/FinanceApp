package com.example.FinanceApp.repository;

import com.example.FinanceApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByUserIdOrUserIsNull(Long userId);
    boolean existsByNameAndUserId(String name,Long userId);


}
