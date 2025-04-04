package com.hendricks.springai;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorToolTest {

    /**
     * 测试 CalculatorTool 的 execute 方法在加法运算时的功能
     */
    @Test
    public void testExecuteAddition() {
        CalculatorTool calculator = new CalculatorTool();
        String result = calculator.execute(2.0, 3.0, "+");
        assertEquals("5.0", result);
    }

    /**
     * 测试 CalculatorTool 的 execute 方法在减法运算时的功能
     */
    @Test
    public void testExecuteSubtraction() {
        CalculatorTool calculator = new CalculatorTool();
        String result = calculator.execute(5.0, 2.0, "-");
        assertEquals("3.0", result);
    }

    /**
     * 测试 CalculatorTool 的 execute 方法在乘法运算时的功能
     */
    @Test
    public void testExecuteMultiplication() {
        CalculatorTool calculator = new CalculatorTool();
        String result = calculator.execute(2.0, 3.0, "*");
        assertEquals("6.0", result);
    }

    /**
     * 测试 CalculatorTool 的 execute 方法在除法运算时的功能
     */
    @Test
    public void testExecuteDivision() {
        CalculatorTool calculator = new CalculatorTool();
        String result = calculator.execute(6.0, 2.0, "/");
        assertEquals("3.0", result);
    }

    /**
     * 测试 CalculatorTool 的 execute 方法在除数为零的除法运算时的功能
     */
    @Test
    public void testExecuteDivisionByZero() {
        CalculatorTool calculator = new CalculatorTool();
        String result = calculator.execute(6.0, 0.0, "/");
        assertEquals("Error: Division by zero", result);
    }

    /**
     * 测试 CalculatorTool 的 execute 方法在输入无效操作符时的功能
     */
    @Test
    public void testExecuteInvalidOperation() {
        CalculatorTool calculator = new CalculatorTool();
        String result = calculator.execute(2.0, 3.0, "%");
        assertEquals("Error: Invalid operation", result);
    }
}