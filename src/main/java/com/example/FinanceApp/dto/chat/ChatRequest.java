package com.example.FinanceApp.dto.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChatRequest {

    @NotBlank(message = "Query cannot be empty")
    @Size(max = 500, message = "Query too long")
    private String query;
}
