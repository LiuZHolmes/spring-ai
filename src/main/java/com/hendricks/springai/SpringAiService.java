package com.hendricks.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
@AllArgsConstructor
public class SpringAiService {

    private final ChatClient.Builder builder;

    public String getResponse(String prompt) {
        ChatClient chatClient = builder.build();
        return chatClient.prompt(prompt).tools(new CalculatorTool()).call().content();
    }

    public String extract(ExtractionRequest request) {
        // 组装提示
        String prompt = String.format("请根据主题 '%s' 和内容 '%s' 提取大概摘要, 并用中文返回结果。", request.getSubject(), request.getBody());
        ChatClient chatClient = builder.build();
        return chatClient.prompt(prompt).tools(new CalculatorTool()).call().content();
    }

    public Map<String, String> summarizeEmail(int id) {
        try {
            // Load email.json
            String jsonContent = new String(Files.readAllBytes(Paths.get("/home/cruX/spring-ai/spring-ai/data/email.json")));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonContent);

            // Find the thread and summary by id
            JsonNode rows = rootNode.get("rows");
            JsonNode targetRow = rows.get(id);
            if (targetRow == null) {
                return Map.of("error", "Invalid ID: No email thread found.");
            }

            JsonNode thread = targetRow.get("row").get("thread");
            String existingSummary = targetRow.get("row").get("summary").asText();

            // Prepare prompt for the model
            String threadContent = thread.toString();
            String prompt = String.format("请总结以下邮件线程内容：%s", threadContent);

            // Call the model for summarization
            ChatClient chatClient = builder.build();
            String generatedSummary = chatClient.prompt(prompt).call().content();

            // Evaluate the generated summary
            String evaluationPrompt = String.format(
                "请对以下生成的总结与已有总结进行评估：\n\n生成的总结：%s\n\n已有总结：%s\n\n请用中文简短地给出评价。",
                generatedSummary, existingSummary
            );
            String evaluation = chatClient.prompt(evaluationPrompt).call().content();

            // Return structured output
            return Map.of(
                "generatedSummary", generatedSummary,
                "evaluation", evaluation
            );

        } catch (Exception e) {
            return Map.of("error", "Error processing the request: " + e.getMessage());
        }
    }
}