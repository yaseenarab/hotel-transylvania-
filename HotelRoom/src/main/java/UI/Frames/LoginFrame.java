package UI.Frames;

import Central.CentralProfiles;
import UI.Frames.Admin.AdminHomeFrame;
import UI.Frames.Employee.EmployeeHomeFrame;
import UI.Frames.Guest.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener {

    private JLabel userLabel, passwordLabel, titleLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JCheckBox isEmployee;
    private JLabel questionEmployee,questionAdmin;
    private JCheckBox isAdmin;
    private String connectionStr;
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


        questionEmployee = new JLabel("Are you an Employee?");
        questionEmployee.setFont(new Font("Arial", Font.PLAIN, 14));
        isEmployee = new JCheckBox();
        isEmployee.addActionListener(e ->employeeCheckbox());


        questionAdmin = new JLabel("Are you an Admin?");
        questionAdmin.setFont(new Font("Arial", Font.PLAIN, 14));
        isAdmin = new JCheckBox();
        isAdmin.setVisible(false);
        questionAdmin.setVisible(false);



        // Creating panel for better layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.add(userLabel);
        formPanel.add(userTextField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(questionEmployee);
        formPanel.add(isEmployee);
        formPanel.add(questionAdmin);
        formPanel.add(isAdmin);
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
        pack();
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {

        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        boolean loggedIn = false;
        if (e.getSource() == loginButton) {

            if(!isEmployee.isSelected()) {
                if (CentralProfiles.authenticateUser(username, password, "Guest")) {
                    new GuestHomeFrame(username, password);
                    dispose();
                    loggedIn = true;

                }
            }
            else if(isEmployee.isSelected() && !isAdmin.isSelected()){
                if(CentralProfiles.authenticateUser(username, password, "Employee")){
                    new EmployeeHomeFrame(username,password);
                    dispose();
                    loggedIn = true;
                }


            }
            else if(isEmployee.isSelected() && isAdmin.isSelected()){
                if(CentralProfiles.authenticateUser(username, password, "Admin")){
                    new AdminHomeFrame(username,password);
                    dispose();
                    loggedIn = true;
                }
            }
            if(!loggedIn){
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
        } else if (e.getSource() == registerButton) {
            JFrame register = new GuestRegistrationFrame(username, password);
            register.setVisible(true);
            dispose();
        }
    }

    private void employeeCheckbox(){
        if(isEmployee.isSelected()){
            isAdmin.setVisible(true);
            questionAdmin.setVisible(true);
        }
        else {
            isAdmin.setVisible(false);
            questionAdmin.setVisible(false);
            isAdmin.setSelected(false);
        }

    }

    private boolean authenticateUser(String username, String password) {
        Connection con = null;
        String connectionStr;

        try {

            con = DriverManager.getConnection("jdbc:derby:PersonProfilesData;");
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM PersonProfiles WHERE Username = '" + username + "'");


            if (res.next() && res.getString("Password").equals(password)) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }



}
