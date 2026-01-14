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

        // ✅ Use Switch Expression (Clean, no 'return' inside cases)
        return switch (intent) {
            case REPORT_MONTHLY ->
                    aiExplainationService.answerSpecificQuestion("How do I see my monthly report?");

            case REPORT_YEARLY ->
                    aiExplainationService.answerSpecificQuestion("How do I see my yearly summary?");

            // For all these finance topics, pass the user's question directly to AI
            case GENERAL_HELP, TRANSACTION_HELP, CATEGORY_HELP, GOAL_PROGRESS ->
                    aiExplainationService.answerSpecificQuestion(userMessage);

            // Validation / Error cases
            case UNSUPPORTED ->
                    throw new UnsupportedChatQueryException();

            // Fallback for safety
            default ->
                    throw new UnsupportedChatQueryException();
        };
    }
}