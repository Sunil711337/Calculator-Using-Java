import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Main extends JFrame implements ActionListener {
    private JTextField inputField;
    private Calculator calculator;
    private String operator = ""; // Initialize operator to an empty string
    private double firstOperand;

    public Main() {
        calculator = new Calculator();

        // Create input field
        inputField = new JTextField();
        inputField.setEditable(false);
        inputField.setHorizontalAlignment(SwingConstants.RIGHT);
        inputField.setFont(new Font("Arial", Font.BOLD, 48)); // Make font bold and larger
        inputField.setPreferredSize(new Dimension(400, 100)); // Increase size of input box

        // Create a panel for the input field and the remove button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        // Create the remove button
        JButton removeButton = new JButton("<-");
        removeButton.setFont(new Font("Arial", Font.BOLD, 24)); // Set font for the remove button
        removeButton.addActionListener(this);
        removeButton.setPreferredSize(new Dimension(80, 100)); // Set size for the remove button

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(removeButton, BorderLayout.EAST); // Add the remove button to the right of the input field

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5)); // 5 rows, 4 columns

        // Add the remove button in the first row
        buttonPanel.add(removeButton); // Add remove button to the first position

        // Add other buttons
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+",
                "^", "%", "√" // New buttons for power, percentage, and square root
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 24)); // Make button text bold and larger
            button.setPreferredSize(new Dimension(80, 80)); // Set preferred size for buttons
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH); // Add the input panel with the remove button
        add(buttonPanel, BorderLayout.CENTER); // Add the button panel

        setTitle("Calculator");
        setSize(500, 600); // Set the size of the window
        setResizable(true); // Allow resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
            // If the last input was a result or an error, clear the input field before adding a new number
            if (inputField.getText().equals("Error") || (operator.isEmpty() && inputField.getText().isEmpty())) {
                inputField.setText(""); // Clear the input field
            }
            inputField.setText(inputField.getText() + command); // Append the digit
        } else if (command.equals("C")) {
            inputField.setText(""); // Clear the input field
            operator = ""; // Reset operator
            firstOperand = 0; // Reset first operand
        } else if (command.equals("<-")) { // Handle the remove button
            String currentText = inputField.getText();
            if (currentText.length() > 0) {
                inputField.setText(currentText.substring(0, currentText.length() - 1)); // Remove last character
            }
        } else if (command.equals("=")) {
            String input = inputField.getText(); // Get the input without spaces
            if (input.length() < 3) {
                inputField.setText("Error");
                return;
            }

            // Extract the operator and operands
            char operatorChar = ' ';
            for (char c : input.toCharArray()) {
                if ("+-*/^".indexOf(c) != -1) {
                    operatorChar = c;
                    break;
                }
            }

            if (operatorChar == ' ') {
                inputField.setText("Error");
                return;
            }

            String[] parts = input.split("[+*/^-]"); // Split by operators
            if (parts.length == 2) {
                try {
                    firstOperand = Double.parseDouble(parts[0]);
                    operator = String.valueOf(operatorChar);
                    double secondOperand = Double.parseDouble(parts[1]);
                    double result = 0;

                    switch (operator) {
                        case "+":
                            result = calculator.add(firstOperand, secondOperand);
                            break;
                        case "-":
                            result = calculator.subtract(firstOperand, secondOperand);
                            break;
                        case "*":
                            result = calculator.multiply(firstOperand, secondOperand);
                            break;
                        case "/":
                            if (secondOperand == 0) {
                                inputField.setText("Error: Division by zero");
                                return;
                            }
                            result = calculator.divide(firstOperand, secondOperand);
                            break;
                        case "^":
                            result = calculator.power(firstOperand, secondOperand);
                            break;
                    }

                    // Check if the result is an integer or a floating-point number
                    if (result % 1 == 0) {
                        inputField.setText(String.valueOf((int) result)); // Display as integer
                    } else {
                        DecimalFormat df = new DecimalFormat("#.00");
                        inputField.setText(df.format(result)); // Display with two decimal places
                    }
                    operator = ""; // Reset operator after calculation
                } catch (NumberFormatException ex) {
                    inputField.setText("Error");
                }
            } else {
                inputField.setText("Error");
            }
        } else if (command.equals("√")) { // Handle square root operation
            String input = inputField.getText();
            if (input.isEmpty()) {
                inputField.setText("Error");
                return;
            }
            try {
                double number = Double.parseDouble(input);
                if (number < 0) {
                    inputField.setText("Error: Negative input");
                } else {
                    double result = Math.sqrt(number);
                    DecimalFormat df = new DecimalFormat("#.00");
                    if (result % 1 == 0) {
                        inputField.setText(String.valueOf((int) result)); // Display as integer if whole number
                    } else {
                        inputField.setText(df.format(result)); // Display with two decimal places
                    }
                }
            } catch (NumberFormatException ex) {
                inputField.setText("Error");
            }
        }
        else if (command.equals("%")) { // Handle percentage operation
            String input = inputField.getText();
            if (input.isEmpty()) {
                inputField.setText("Error");
                return;
            }
            try {
                double number = Double.parseDouble(input);
                double result = number / 100; // Convert percentage to decimal
                DecimalFormat df = new DecimalFormat("#.00");
                if (result % 1 == 0) {
                    inputField.setText(String.valueOf((int) result)); // Display as integer if whole number
                } else {
                    inputField.setText(df.format(result)); // Display with two decimal places
                }
            } catch (NumberFormatException ex) {
                inputField.setText("Error");
            }
        }

        else {
            if (!operator.isEmpty()) {
                return; // Prevent multiple operators
            }
            if (!inputField.getText().isEmpty()) {
                firstOperand = Double.parseDouble(inputField.getText());
                operator = command;
                inputField.setText(inputField.getText() + operator); // Show operator in input field
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}