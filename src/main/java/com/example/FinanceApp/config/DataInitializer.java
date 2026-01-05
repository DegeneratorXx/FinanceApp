package com.example.FinanceApp.config;

import com.example.FinanceApp.entity.Category;
import com.example.FinanceApp.entity.CategoryType;
import com.example.FinanceApp.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadDefaultCategories(CategoryRepository categoryRepository){
        return args -> {
            if(categoryRepository.count()>0) return;

            categoryRepository.save(new Category("Salary",CategoryType.INCOME,false,null));
            categoryRepository.save(new Category("Food",CategoryType.EXPENSE,false,null));
            categoryRepository.save(new Category("Rent",CategoryType.EXPENSE,false,null));
            categoryRepository.save(new Category("Transportation",CategoryType.EXPENSE,false,null));
            categoryRepository.save(new Category("Entertainment",CategoryType.EXPENSE,false,null));
            categoryRepository.save(new Category("Healthcare",CategoryType.EXPENSE,false,null));
            categoryRepository.save(new Category("Utilities",CategoryType.EXPENSE,false,null));

        };
    }
}
