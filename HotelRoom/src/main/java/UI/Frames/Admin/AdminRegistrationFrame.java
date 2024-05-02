package UI.Frames.Admin;

import AccountService.Person;
import Central.CentralProfiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminRegistrationFrame extends JFrame implements ActionListener {
    private static final String ADMIN_CODE = "admin";
    private String username, password;
    private JTextField firstNameField, lastNameField, emailField, phoneField, usernameField, passwordField, accessCodeField;
    private JButton registerButton;

    public AdminRegistrationFrame(String username, String password) {
        this.username = username;
        this.password = password;

        setTitle("Admin Registration Page");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen


        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(username);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JTextField(password);
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Access Code:"));
        accessCodeField = new JTextField();
        formPanel.add(accessCodeField);

        registerButton = new JButton("Register");
        formPanel.add(registerButton);
        registerButton.addActionListener(this);

        add(formPanel, BorderLayout.CENTER);
    }

    private boolean registerAdmin(String firstname,String lastName, String email, String phoneNumber, String username, String password) {
        try {
            CentralProfiles.makeAdminProfile(firstname,lastName,email,phoneNumber,username,password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            try {
                String firstName =  firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                boolean skip = false;

                try {
                    Person.isValidPerson(firstName, lastName, email, phoneNumber, username, password);
                } catch (Exception p) {
                    JOptionPane.showMessageDialog(this, p.getLocalizedMessage());
                    skip = true;
                }
                if(!accessCodeField.getText().equals(ADMIN_CODE)) {
                    JOptionPane.showMessageDialog(this, "Invalid access code");
                } else if (!skip && !CentralProfiles.AdminisIn(usernameField.getText()) &&
                        registerAdmin(firstName,lastName,email,phoneNumber,username,password)) {

                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    new AdminHomeFrame(username, password);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Username or Password already exists. Please try again.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please fill all fields correctly: \n" + ex.getLocalizedMessage());
            }

        }
    }
}
