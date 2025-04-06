package com.hendricks.springai;

import org.apache.logging.log4j.util.Strings;
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

    public SummaryResultDto summarizeEmail(int id) {
        try {
            // Load email.json
            String jsonContent = new String(
                    Files.readAllBytes(Paths.get("/home/cruX/spring-ai/spring-ai/data/email.json")));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonContent);

            // Find the thread and summary by id
            JsonNode rows = rootNode.get("rows");
            JsonNode targetRow = rows.get(id);
            if (targetRow == null) {
                throw new IllegalArgumentException("Invalid ID: No email thread found.");
            }

            JsonNode thread = targetRow.get("row").get("thread");
            String threadContent = thread.toString();
            String prompt = String.format(
                    "请总结以下邮件线程内容，并返回一个JSON对象，格式为：{\"who\":[], \"what\":\"\", \"when\":\"\", \"why\":\"\", \"how\":\"\", \"where\":\"\"}：%s",
                    threadContent);

            ChatClient chatClient = builder.build();
            SummaryResultDto result = null;
            int retries = 3;
            String errorMessage = "";

            while (retries > 0) {
                String response = chatClient.prompt(prompt + errorMessage).tools(new SummaryResultValidationTool()).call().content();
                System.out.println("Response: " + response);
                System.out.println("Error message: " + errorMessage);
                if (response.startsWith("Validation failed:")) {
                    errorMessage = String.format("\n上次生成的结果有以下问题：%s\n请重新生成符合要求的JSON对象。", response);
                    retries--;
                } else {
                    response = response.substring(1, response.length() - 1).replace("\\", "");
                    System.out.println("Response after replacement: " + response);
                    result = objectMapper.readValue(response, SummaryResultDto.class);
                    break;
                }
            }

            if (result == null) {
                throw new RuntimeException("Failed to generate valid summary after 3 retries.");
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error processing the request: " + e.getMessage(), e);
        }
    }
}