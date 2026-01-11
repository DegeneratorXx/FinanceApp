package com.example.FinanceApp.client;

import org.springframework.stereotype.Component;

@Component
public class GeminiStartupCheck {

    public GeminiStartupCheck(GeminiClient client) {
        String reply = client.generate("Say hello in one word");
        System.out.println("Gemini says: " + reply);
    }
}
