package com.hendricks.springai;

import org.springframework.ai.tool.annotation.*;

public class CalculatorTool {


    @Tool(description ="Performs basic mathematical calculations.")
    public String execute(@ToolParam(description = "num1") Double num1, @ToolParam(description = "num2") Double num2, @ToolParam(description = "operation:+-*/") String operation) {
        System.out.println("Call tools");
        switch (operation) {
            case "+":
                return String.valueOf(num1 + num2);
            case "-":
                return String.valueOf(num1 - num2);
            case "*":
                return String.valueOf(num1 * num2);
            case "/":
                if (num2 == 0) {
                    return "Error: Division by zero";
                }
                return String.valueOf(num1 / num2);
            default:
                return "Error: Invalid operation";
        }
    }
}