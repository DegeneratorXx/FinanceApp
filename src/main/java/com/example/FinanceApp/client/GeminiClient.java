package com.example.FinanceApp.client;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GeminiClient {

    private final Client client;

    public GeminiClient(
            @Value("${gemini.api.key}") String apiKey
    ){
        this.client=Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String generate(String systemInstruction, String userMessage) {
        try {
            // 1. Wrap the System Instruction in a Content object
            Content systemContent = Content.builder()
                    .parts(Collections.singletonList(
                            Part.builder().text(systemInstruction).build()
                    ))
                    .build();

            // 2. Create the Configuration
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .systemInstruction(systemContent)
                    .temperature(0.2f) // Optional: Keeps answers focused
                    .build();

            // 3. Call the API with the config
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    userMessage,
                    config
            );

            return response.text();

        } catch (Exception e) {
            return "AI service is currently unavailable: " + e.getMessage();
        }
    }


}
