package UI.Frames;

import Central.CentralProfiles;
import UI.Frames.Admin.AdminHomeFrame;
import UI.Frames.Admin.AdminRegistrationFrame;
import UI.Frames.Employee.EmployeeHomeFrame;
import UI.Frames.Guest.GuestHomeFrame;
import UI.Frames.Guest.GuestRegistrationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel userLabel, passwordLabel, titleLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton, registerGuestButton,
            registerAdminButton;

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

        registerGuestButton = new JButton("Register Guest");
        registerGuestButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerGuestButton.setPreferredSize(new Dimension(120, 35)); // Set preferred size
        registerGuestButton.setBackground(Color.decode("#008CBA")); // Blue color
        registerGuestButton.setForeground(Color.black); // White text color

        registerAdminButton = new JButton("Register Admin");
        registerAdminButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerAdminButton.setPreferredSize(new Dimension(120, 35)); // Set preferred size
        registerAdminButton.setBackground(Color.decode("#008CBA")); // Blue color
        registerAdminButton.setForeground(Color.black); // White text color

        // Creating panel for better layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.add(userLabel);
        formPanel.add(userTextField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(new JLabel()); // Empty label for spacing

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Adjust spacing
        buttonPanel.add(loginButton);
        buttonPanel.add(registerGuestButton);
        buttonPanel.add(registerAdminButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding panel to the frame
        add(panel);

        // Registering action listeners
        loginButton.addActionListener(this);
        registerGuestButton.addActionListener(this);
        registerAdminButton.addActionListener(this);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (e.getSource() == loginButton) {
            if (CentralProfiles.authenticateUser(username, password, "Guest")) {
                new GuestHomeFrame(username, password);
                dispose();
            }
            else if(CentralProfiles.authenticateUser(username, password, "Employee")){
                new EmployeeHomeFrame(username,password);
                dispose();
            }
            else if(CentralProfiles.authenticateUser(username, password, "Admin")){
                new AdminHomeFrame(username, password);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
        } else if (e.getSource() == registerGuestButton) {
            JFrame register = new GuestRegistrationFrame(username, password);
            register.setVisible(true);
            dispose();
        } else if (e.getSource() == registerAdminButton) {
            JFrame register = new AdminRegistrationFrame(username, password);
            register.setVisible(true);
            dispose();
        }
    }
}

