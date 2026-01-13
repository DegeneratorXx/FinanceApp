package com.example.FinanceApp.service.chat;

import com.example.FinanceApp.client.GeminiClient;
import org.springframework.stereotype.Component;


@Component
public class AiExplainationService {

    private final GeminiClient geminiClient;

    public AiExplainationService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    public String explainTransactionFlow() {
        return geminiClient.ask("""
            Explain how transactions work in FinanceApp.
            Cover:
            - Income vs Expense
            - Categories
            - Date restrictions
            - How reports use transactions
            Keep the explanation short and simple.
        """);
    }

    public String explainCategoryUsage() {
        return geminiClient.ask("""
            Explain categories in FinanceApp.
            Cover:
            - Default categories
            - Custom categories
            - Income vs Expense types
            - Why some categories cannot be deleted
        """);
    }

    public String generalHelp() {
        return geminiClient.ask("""
            Explain what FinanceApp does.
            Cover:
            - Transactions
            - Categories
            - Goals
            - Reports
            Do not mention anything outside this application.
        """);
    }
}
