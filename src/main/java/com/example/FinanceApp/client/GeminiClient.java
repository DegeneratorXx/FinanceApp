package com.example.FinanceApp.client;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

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

    public String ask(String prompt){
        try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null
            );
            return response.text();
        }
        catch (Exception e){
            return "AI service is currently unavailable "+ e.getMessage();
        }
    }


}
