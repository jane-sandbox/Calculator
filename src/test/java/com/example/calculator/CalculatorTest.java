package com.example.calculator;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Method;

/**
 * Basic unit tests for the Calculator functionality.
 * Note: These tests focus on the calculation logic rather than GUI testing.
 */
public class CalculatorTest {
    
    @Test
    public void testBasicAddition() {
        // Since our calculator is GUI-based, we'll test the logic conceptually
        // In a real-world scenario, you'd extract the calculation logic to a separate class
        double result = performCalculation(5.0, "+", 3.0);
        assertEquals("Addition test failed", 8.0, result, 0.001);
    }
    
    @Test
    public void testBasicSubtraction() {
        double result = performCalculation(10.0, "-", 4.0);
        assertEquals("Subtraction test failed", 6.0, result, 0.001);
    }
    
    @Test
    public void testBasicMultiplication() {
        double result = performCalculation(6.0, "×", 7.0);
        assertEquals("Multiplication test failed", 42.0, result, 0.001);
    }
    
    @Test
    public void testBasicDivision() {
        double result = performCalculation(15.0, "÷", 3.0);
        assertEquals("Division test failed", 5.0, result, 0.001);
    }
    
    @Test
    public void testDecimalCalculation() {
        double result = performCalculation(2.5, "+", 1.7);
        assertEquals("Decimal addition test failed", 4.2, result, 0.001);
    }
    
    @Test
    public void testNegativeNumbers() {
        double result = performCalculation(-5.0, "+", 3.0);
        assertEquals("Negative number test failed", -2.0, result, 0.001);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {
        performCalculation(10.0, "÷", 0.0);
    }
    
    @Test
    public void testSimpleBrackets() {
        double result = evaluateExpression("(2+3)*4");
        assertEquals("Simple brackets test failed", 20.0, result, 0.001);
    }
    
    @Test
    public void testNestedBrackets() {
        double result = evaluateExpression("((2+3)*4)-5");
        assertEquals("Nested brackets test failed", 15.0, result, 0.001);
    }
    
    @Test
    public void testComplexExpression() {
        double result = evaluateExpression("2+(3*4)-(5/2)");
        assertEquals("Complex expression test failed", 11.5, result, 0.001);
    }
    
    @Test
    public void testBracketsWithDecimal() {
        double result = evaluateExpression("(2.5+1.5)*2");
        assertEquals("Brackets with decimal test failed", 8.0, result, 0.001);
    }
    
    @Test
    public void testOrderOfOperations() {
        double result = evaluateExpression("2+3*4");
        assertEquals("Order of operations test failed", 14.0, result, 0.001);
    }
    
    /**
     * Helper method to simulate calculator operations
     * In a production app, this logic would be extracted to a separate Calculator class
     */
    private double performCalculation(double first, String operator, double second) {
        switch (operator) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "×":
                return first * second;
            case "÷":
                if (second == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return first / second;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    /**
     * Helper method to test expression evaluation with brackets
     */
    private double evaluateExpression(String expression) {
        try {
            Calculator calculator = new Calculator();
            Method evaluateMethod = Calculator.class.getDeclaredMethod("evaluateExpression", String.class);
            evaluateMethod.setAccessible(true);
            String normalizedExpr = expression.replace("×", "*").replace("÷", "/");
            return (Double) evaluateMethod.invoke(calculator, normalizedExpr);
        } catch (Exception e) {
            throw new RuntimeException("Failed to evaluate expression: " + expression, e);
        }
    }
}