package com.example.FinanceApp.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class GeminiClient {

    private final WebClient webClient;
    private final String apiKey;

    public GeminiClient(
            @Value("${gemini.api.key}") String apiKey
    ){
        this.apiKey=apiKey;
        this.webClient= WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1")
                .build();
    }

    public String generate(String prompt){
        Map<String,Object> body= Map.of(
                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text",prompt)
                                }
                        )
                }
        );

        return webClient.post().uri(uriBuilder -> uriBuilder
                                    .path("/models/gemini-2.5-flash:generateContent")
                                    .queryParam("key",apiKey)
                                    .build()
        )
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response->{
                    var candidates=(List<Map<String,Object>>) response.get("candidates");
                    var content = (Map<String, Object>) candidates.get(0).get("content");
                    var parts = (java.util.List<Map<String, String>>) content.get("parts");
                    return parts.get(0).get("text");

                })
                .block();
    }
}
