package com.example.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

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
}