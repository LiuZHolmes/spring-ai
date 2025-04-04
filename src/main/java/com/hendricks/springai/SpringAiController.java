package com.hendricks.springai;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SpringAiController {

    private final SpringAiService springaiService;

    @GetMapping("/prompt")
    public String getResponse(@RequestParam String prompt) {
        return springaiService.getResponse(prompt);
    }

    @PostMapping("/extract")
    public String extract(@RequestBody ExtractionRequest request) {
        return springaiService.extract(request);
    }
}