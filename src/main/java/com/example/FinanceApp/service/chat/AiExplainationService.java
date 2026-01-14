package com.example.FinanceApp.service.chat;

import com.example.FinanceApp.client.GeminiClient;
import org.springframework.stereotype.Component;

import static com.example.FinanceApp.service.chat.AiPrompts.APP_CONTEXT;


@Component
public class AiExplainationService {

    private final GeminiClient geminiClient;


    public AiExplainationService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    // Dynamic method for general finance/app queries
    public String answerSpecificQuestion(String userMessage) {
        return geminiClient.generate(
                APP_CONTEXT,  // System Instruction (The Rules)
                userMessage   // User Prompt (The Question)
        );

    }


}
