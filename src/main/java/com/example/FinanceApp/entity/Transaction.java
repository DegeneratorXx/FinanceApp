package com.example.FinanceApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    private String description;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private User user;


}
