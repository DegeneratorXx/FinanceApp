package com.example.FinanceApp.service.chat;

import org.springframework.stereotype.Component;

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

    public Intent classify(String query){
        String q = query.toLowerCase();

        // Reports
        if (q.contains("monthly") || q.contains("month")) {
            return Intent.REPORT_MONTHLY;
        }

        if (q.contains("yearly") || q.contains("year")) {
            return Intent.REPORT_YEARLY;
        }

        // Goals
        if (q.contains("goal") || q.contains("saving") || q.contains("progress")) {
            return Intent.GOAL_PROGRESS;
        }

        // Transactions
        if (q.contains("transaction") || q.contains("expense") || q.contains("income")) {
            return Intent.TRANSACTION_HELP;
        }

        // Categories
        if (q.contains("category")) {
            return Intent.CATEGORY_HELP;
        }

        // Help
        if (q.contains("help") || q.contains("how")) {
            return Intent.GENERAL_HELP;
        }

        // Everything else
        return Intent.UNSUPPORTED;
    }
}
