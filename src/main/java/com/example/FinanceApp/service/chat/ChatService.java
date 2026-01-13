package com.example.FinanceApp.service.chat;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatOrchestrator chatOrchestrator;

    public ChatService(ChatOrchestrator chatOrchestrator) {
        this.chatOrchestrator = chatOrchestrator;
    }

    public String processUserRequest(String message, HttpSession session) {
        // 1. Security Check: Ensure user is logged in
        if (session.getAttribute("USER_ID") == null) {
            // This will be caught by GlobalExceptionHandler -> 401 Unauthorized
            throw new RuntimeException("Unauthorized");
        }

        // 2. Delegate the logic to the Orchestrator
        return chatOrchestrator.handle(message, session);
    }
}
