package com.hendricks.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

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
}