package com.example.FinanceApp.service.chat;
import com.example.FinanceApp.exception.UnsupportedChatQueryException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class ChatOrchestrator {

    private final IntentClassifier intentClassifier;
    private final AiExplainationService aiExplainationService;

    // Fixed typo in constructor argument and field assignment
    public ChatOrchestrator(IntentClassifier intentClassifier, AiExplainationService aiExplanationService) {
        this.intentClassifier = intentClassifier;
        this.aiExplainationService = aiExplanationService;
    }

    public String handle(String userMessage, HttpSession session) {
        // Classify the message
        IntentClassifier.Intent intent = intentClassifier.classify(userMessage);

        return switch (intent) {
            case REPORT_MONTHLY ->
                    "You can view your monthly report from Reports → Monthly section.";

            case REPORT_YEARLY ->
                    "Your yearly financial summary is available in Reports → Yearly.";

            case GOAL_PROGRESS ->
                    "Savings goal progress is calculated using your income minus expenses since the goal start date.";

            case TRANSACTION_HELP ->
                    aiExplainationService.explainTransactionFlow();

            case CATEGORY_HELP ->
                    aiExplainationService.explainCategoryUsage();

            case GENERAL_HELP ->
                    aiExplainationService.generalHelp();

            case UNSUPPORTED ->
                    throw new UnsupportedChatQueryException();

            default ->
                    "I am not sure how to help with that.";
        };
    }
}