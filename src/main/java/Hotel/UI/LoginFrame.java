package Hotel.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel userLabel, passwordLabel, titleLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginFrame() {
        setTitle("Login Page");

        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Creating components
        titleLabel = new JLabel("Welcome to Hotel Transylvania");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userTextField = new JTextField(15);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField(15);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setPreferredSize(new Dimension(120, 35)); // Set preferred size
        loginButton.setBackground(Color.decode("#4CAF50")); // Green color
        loginButton.setForeground(Color.black); // White text color

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setPreferredSize(new Dimension(120, 35)); // Set preferred size
        registerButton.setBackground(Color.decode("#008CBA")); // Blue color
        registerButton.setForeground(Color.black); // White text color

        // Creating panel for better layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.add(userLabel);
        formPanel.add(userTextField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(new JLabel()); // Empty label for spacing

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Adjust spacing
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding panel to the frame
        add(panel);

        // Registering action listeners
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (e.getSource() == loginButton) {
            if (authenticateUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");

                new GuestHomeFrame(username, password);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
        } else if (e.getSource() == registerButton) {
            JFrame register = new GuestRegistrationFrame(username, password);
            register.setVisible(true);
            dispose();
        }
    }

    private boolean authenticateUser(String username, String password) {
        try (Scanner scanner = new Scanner(new File("Person_Profiles.csv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 7 &&
                        parts[0].contains("TVGI") &&
                        parts[5].equals(username) &&
                        parts[6].equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
