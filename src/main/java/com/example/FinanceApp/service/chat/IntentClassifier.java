package com.example.FinanceApp.service.chat;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IntentClassifier {

    public enum Intent{
        REPORT_MONTHLY,
        REPORT_YEARLY,
        GOAL_PROGRESS,
        TRANSACTION_HELP,
        CATEGORY_HELP,
        GENERAL_HELP,
        UNSUPPORTED
    }

    // Expanded keywords to include Architecture, Security, and Tech Stack
    private static final List<String> ALLOWED_KEYWORDS = List.of(
            // 1. Core Features
            "transaction", "expense", "income", "money", "save", "saving",
            "goal", "budget", "report", "month", "year", "summary",
            "category", "custom", "default", "add", "create", "delete",
            "update", "check", "how to", "app", "finance",

            // 2. Auth & Security
            "login", "register", "logout", "auth", "authentication",
            "security", "session", "cookie", "token", "user", "access",

            // 3. Architecture & Tech Stack
            "architecture", "structure", "layer", "controller", "service", "repository",
            "database", "db", "h2", "java", "spring", "boot", "maven",
            "tech", "stack", "design", "internal", "system",

            // 4. AI & API
            "api", "endpoint", "ai", "gemini", "bot", "assistant",

            //general
            "help"
    );

    public Intent classify(String message){
        String msg= message.toLowerCase();

        if(msg.contains("report") && msg.contains("month")) return Intent.REPORT_MONTHLY;
        if(msg.contains("report") && msg.contains("year")) return Intent.REPORT_YEARLY;

        boolean isRelevant =  ALLOWED_KEYWORDS.stream().anyMatch(msg::contains);

        if(isRelevant)
            return Intent.GENERAL_HELP;

        // Everything else
        return Intent.UNSUPPORTED;
    }
}
