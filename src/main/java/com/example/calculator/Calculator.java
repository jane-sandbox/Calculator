package com.example.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * A simple calculator GUI application with basic arithmetic operations.
 */
public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder expression;
    private boolean justCalculated;

    public Calculator() {
        expression = new StringBuilder();
        justCalculated = false;
        
        setupUI();
    }

    private void setupUI() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Display field
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setPreferredSize(new Dimension(300, 50));
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Button panel with grid layout
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        
        // Button labels in order
        String[] buttonLabels = {
            "C", "CE", "⌫", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "(", "0", ")", "="
        };
        
        // Create and add buttons
        for (String label : buttonLabels) {
            JButton button = createButton(label);
            buttonPanel.add(button);
        }
        
        mainPanel.add(display, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(70, 50));
        button.addActionListener(this);
        
        // Color coding for different button types
        if (text.matches("[0-9]") || text.equals(".")) {
            button.setBackground(Color.WHITE);
        } else if (text.matches("[+\\-×÷=()]")) {
            button.setBackground(new Color(255, 165, 0)); // Orange for operators
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(220, 220, 220)); // Light gray for functions
        }
        
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "C":
                clearAll();
                break;
            case "CE":
                clearEntry();
                break;
            case "⌫":
                backspace();
                break;
            case "+":
            case "-":
            case "×":
            case "÷":
            case "(":
            case ")":
                addToExpression(command);
                break;
            case "=":
                calculate();
                break;
            case ".":
                addToExpression(command);
                break;
            default:
                // Number buttons (0-9)
                if (command.matches("[0-9]")) {
                    addToExpression(command);
                }
                break;
        }
    }
    
    private void clearAll() {
        expression.setLength(0);
        display.setText("0");
        justCalculated = false;
    }
    
    private void clearEntry() {
        if (expression.length() > 0) {
            expression.deleteCharAt(expression.length() - 1);
            if (expression.length() == 0) {
                display.setText("0");
            } else {
                display.setText(expression.toString());
            }
        }
    }
    
    private void backspace() {
        if (expression.length() > 0) {
            expression.deleteCharAt(expression.length() - 1);
            if (expression.length() == 0) {
                display.setText("0");
            } else {
                display.setText(expression.toString());
            }
        }
    }
    
    private void addToExpression(String input) {
        if (justCalculated && input.matches("[0-9(.]")) {
            expression.setLength(0);
            justCalculated = false;
        }
        
        expression.append(input);
        display.setText(expression.toString());
    }
    
    
    private void calculate() {
        if (expression.length() == 0) {
            return;
        }
        
        try {
            String expr = expression.toString().replace("×", "*").replace("÷", "/");
            double result = evaluateExpression(expr);
            
            // Format result to remove unnecessary decimal places
            if (result == (long) result) {
                display.setText(String.valueOf((long) result));
            } else {
                display.setText(String.valueOf(result));
            }
            
            expression.setLength(0);
            expression.append(display.getText());
            justCalculated = true;
            
        } catch (Exception ex) {
            display.setText("Error");
            expression.setLength(0);
        }
    }
    
    private double evaluateExpression(String expr) throws Exception {
        return new ExpressionEvaluator().evaluate(expr);
    }
    
    private static class ExpressionEvaluator {
        private int pos = -1, ch;
        
        public double evaluate(String expression) throws Exception {
            this.pos = -1;
            return parse(expression);
        }
        
        private void nextChar(String str) {
            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
        }
        
        private boolean eat(int charToEat, String str) {
            while (ch == ' ') nextChar(str);
            if (ch == charToEat) {
                nextChar(str);
                return true;
            }
            return false;
        }
        
        private double parse(String str) throws Exception {
            nextChar(str);
            double x = parseExpression(str);
            if (pos < str.length()) throw new Exception("Unexpected: " + (char)ch);
            return x;
        }
        
        private double parseExpression(String str) throws Exception {
            double x = parseTerm(str);
            for (;;) {
                if      (eat('+', str)) x += parseTerm(str);
                else if (eat('-', str)) x -= parseTerm(str);
                else return x;
            }
        }
        
        private double parseTerm(String str) throws Exception {
            double x = parseFactor(str);
            for (;;) {
                if      (eat('*', str)) x *= parseFactor(str);
                else if (eat('/', str)) {
                    double divisor = parseFactor(str);
                    if (divisor == 0) throw new Exception("Division by zero");
                    x /= divisor;
                }
                else return x;
            }
        }
        
        private double parseFactor(String str) throws Exception {
            if (eat('+', str)) return parseFactor(str);
            if (eat('-', str)) return -parseFactor(str);
            
            double x;
            int startPos = this.pos;
            if (eat('(', str)) {
                x = parseExpression(str);
                if (!eat(')', str)) throw new Exception("Missing ')'");
            } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar(str);
                x = Double.parseDouble(str.substring(startPos, this.pos));
            } else {
                throw new Exception("Unexpected: " + (char)ch);
            }
            
            return x;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Use default look and feel if system L&F fails
            }
            
            new Calculator().setVisible(true);
        });
    }
}