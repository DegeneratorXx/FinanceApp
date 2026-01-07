package com.example.FinanceApp.repository;

import com.example.FinanceApp.entity.Category;
import com.example.FinanceApp.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByUserIdOrUserIsNull(Long userId);
    boolean existsByNameAndUserId(String name,Long userId);
    Optional<Category> findByNameAndUserIdOrUserIsNull(String name, Long userId);
    Optional<Category> findByNameAndUserIdAndType(
            String name,
            Long userId,
            CategoryType type
    );
    Optional<Category> findByNameAndUserId(String name, Long userId);

    Optional<Category> findByNameAndUserIsNull(String name);


}
