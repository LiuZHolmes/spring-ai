package com.hendricks.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringaiController {

    private final ChatClient.Builder builder;

    public SpringaiController(ChatClient.Builder builder) {
        this.builder = builder;
    }

    @GetMapping("/prompt")
    public String getResponse(@RequestParam String prompt) {
        ChatClient chatClient = builder.build();
        return chatClient.prompt(prompt).tools(new CalculatorTool()).call().content();
    }
}