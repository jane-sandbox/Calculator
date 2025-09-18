package com.example.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple calculator GUI application with basic arithmetic operations.
 */
public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private double firstOperand;
    private String operator;
    private boolean startNewNumber;

    public Calculator() {
        currentInput = new StringBuilder();
        startNewNumber = true;
        
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
            "±", "0", ".", "="
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
        } else if (text.matches("[+\\-×÷=]")) {
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
            case "±":
                toggleSign();
                break;
            case "+":
            case "-":
            case "×":
            case "÷":
                handleOperator(command);
                break;
            case "=":
                calculate();
                break;
            case ".":
                addDecimalPoint();
                break;
            default:
                // Number buttons (0-9)
                if (command.matches("[0-9]")) {
                    addDigit(command);
                }
                break;
        }
    }
    
    private void clearAll() {
        currentInput.setLength(0);
        display.setText("0");
        firstOperand = 0;
        operator = null;
        startNewNumber = true;
    }
    
    private void clearEntry() {
        currentInput.setLength(0);
        display.setText("0");
        startNewNumber = true;
    }
    
    private void backspace() {
        String current = display.getText();
        if (current.length() > 1 && !current.equals("0")) {
            current = current.substring(0, current.length() - 1);
            display.setText(current);
        } else {
            display.setText("0");
            startNewNumber = true;
        }
    }
    
    private void toggleSign() {
        String current = display.getText();
        if (!current.equals("0")) {
            if (current.startsWith("-")) {
                display.setText(current.substring(1));
            } else {
                display.setText("-" + current);
            }
        }
    }
    
    private void addDigit(String digit) {
        String current = display.getText();
        
        if (startNewNumber || current.equals("0")) {
            display.setText(digit);
            startNewNumber = false;
        } else {
            display.setText(current + digit);
        }
    }
    
    private void addDecimalPoint() {
        String current = display.getText();
        
        if (startNewNumber) {
            display.setText("0.");
            startNewNumber = false;
        } else if (!current.contains(".")) {
            display.setText(current + ".");
        }
    }
    
    private void handleOperator(String newOperator) {
        if (operator != null && !startNewNumber) {
            calculate();
        }
        
        firstOperand = Double.parseDouble(display.getText());
        operator = newOperator;
        startNewNumber = true;
    }
    
    private void calculate() {
        if (operator == null) {
            return;
        }
        
        double secondOperand = Double.parseDouble(display.getText());
        double result = 0;
        
        try {
            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "×":
                    result = firstOperand * secondOperand;
                    break;
                case "÷":
                    if (secondOperand == 0) {
                        display.setText("Error: Division by zero");
                        operator = null;
                        startNewNumber = true;
                        return;
                    }
                    result = firstOperand / secondOperand;
                    break;
            }
            
            // Format result to remove unnecessary decimal places
            if (result == (long) result) {
                display.setText(String.valueOf((long) result));
            } else {
                display.setText(String.valueOf(result));
            }
            
        } catch (Exception ex) {
            display.setText("Error");
        }
        
        operator = null;
        startNewNumber = true;
        firstOperand = result;
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