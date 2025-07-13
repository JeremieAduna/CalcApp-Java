package MyCalculator;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    private int boardWidth = 360;
    private int boardHeight = 540;

    Color customOrange = new Color(255, 95, 21);
    Color customBlack = new Color(28, 28, 28);
    Color customWhite = new Color(255, 255, 255);
    Color customGray = new Color(201, 201, 201);

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String[] buttonValues = {
            "AC", "←", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "←", "%"};

    String A = "0";
    String operator = null;
    String B = null;

    public Calculator() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customWhite);
        displayLabel.setForeground(customBlack);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
//        displayPanel.setPreferredSize(new Dimension(boardWidth, 100));
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5,4));
        buttonsPanel.setBackground(customWhite);
        buttonsPanel.setBorder(new LineBorder(customGray));
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonValues.length; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customWhite));
            if (Arrays.asList(topSymbols).contains(buttonValue)){
                button.setBackground(customWhite);
                button.setForeground(customOrange);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue)){

                button.setBackground(customWhite);
                button.setForeground(customOrange);
                button.setFont(new Font("Arial", Font.PLAIN, 32));
            }
            else {
                button.setBackground(customWhite);
                button.setForeground(customBlack);
                button.setFont(new Font("Arial", Font.PLAIN, 23));
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("=")) {
                            equalsButton();
                            clearAll();
                        }
                        else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null){
                                A = displayLabel.getText();
                                B = "0";
                            }
                            else {
                                B = displayLabel.getText();
                                A = equalsButton();
                                displayLabel.setText(A);
                            }
                            operator = buttonValue;

                        }
                    }
                    else if (Arrays.asList(topSymbols).contains(buttonValue)){
                        if (buttonValue.equals("AC")){
                            clearAll();
                            displayLabel.setText("0");
                        }
                        else if (buttonValue.equals("←")){
                            String currentText = displayLabel.getText();
                            if (currentText.length() > 1)
                                displayLabel.setText(currentText.substring(0, currentText.length() - 1));
                            else
                                displayLabel.setText("0");
                        }
                        else if (buttonValue.equals("%")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }
                    else {
                        if(buttonValue.equals(".")){
                            if (!displayLabel.getText().contains("."))
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                        }
                        else if (buttonValue.equals("+/-")){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if ("0123456789".contains(buttonValue)){
                            if (A.equals("0")) {
                                if (displayLabel.getText().equals("0"))
                                    displayLabel.setText(buttonValue);
                                else
                                    displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                            else {
                                if (displayLabel.getText().equals(A))
                                    displayLabel.setText(buttonValue);
                                else
                                    displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }

                }
            });
        }
    }
    private void clearAll(){
        A = "0";
        operator = null;
        B = null;
    }
    private String removeZeroDecimal(double numDisplay){
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        else
            return Double.toString(numDisplay);
    }

    private String equalsButton(){
        String total = null;
        if (A != null){
            B = displayLabel.getText();
            double numA = Double.parseDouble(A);
            double numb = Double.parseDouble(B);

            if (operator.equals("+")){
                displayLabel.setText(removeZeroDecimal(numA + numb));
            }
            else if (operator.equals("-")){
                displayLabel.setText(removeZeroDecimal(numA - numb));
            }
            else if (operator.equals("×")){
                displayLabel.setText(removeZeroDecimal(numA * numb));
            }
            else if (operator.equals("÷")){
                displayLabel.setText(removeZeroDecimal(numA / numb));
            }
            total = displayLabel.getText();
        } return total;
    }
}
