package com.example.FinanceApp.controller;

import com.example.FinanceApp.dto.chat.ChatRequest;
import com.example.FinanceApp.dto.chat.ChatResponse;
import com.example.FinanceApp.service.chat.ChatService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody @Valid ChatRequest request, HttpSession session) {
        // Pass request to the Service layer
        String responseMessage = chatService.processUserRequest(request.getQuery(), session);

        // Wrap response in DTO
        return ResponseEntity.ok(new ChatResponse(responseMessage, null));
    }
}
