package com.example.FinanceApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @Column(nullable = false)
    private boolean isCustom;

    @ManyToOne
    private User user;

    public Category(String name, CategoryType type, boolean isCustom, User user) {
        this.name = name;
        this.type = type;
        this.isCustom = isCustom;
        this.user = user;
    }


}
