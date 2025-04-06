package com.hendricks.springai;

import org.springframework.ai.tool.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SummaryResultValidationTool {

    @Tool(description = "Performs validation on a object thay might be SummaryResultDto, return the original string if valid, return error message if invalid.", returnDirect = true)
    public String execute(@ToolParam(description = "json string of a object thay might be SummaryResultDto") String object) {
        System.out.println("Validating object: " + object);
        SummaryResultDto summaryResultDto;
        String result;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            summaryResultDto = objectMapper.readValue(object, SummaryResultDto.class);
            if (summaryResultDto.getWho() == null || summaryResultDto.getWho().isEmpty()) {
                result =  "Validation failed: 'who' field is empty.";
            }
            result = objectMapper.writeValueAsString(summaryResultDto);
        } catch (Exception e) {
            result = String.format("Validation failed: Object format error");
        }
        System.out.println("Tool result: " + result);
        return result;
    }
}