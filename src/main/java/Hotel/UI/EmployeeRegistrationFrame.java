package main.java.Hotel.UI;


import main.java.Hotel.AccountService.Employee;
import main.java.Hotel.AccountService.Guest;
import main.java.Hotel.AccountService.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeRegistrationFrame extends JFrame implements ActionListener {
    private String username, password;
	private JTextField firstNameField, lastNameField, emailField, phoneField, usernameField, passwordField;
    private JButton registerButton;

    public EmployeeRegistrationFrame(String username, String password) {
    	this.username = username;
    	this.password = password;
    	
        setTitle("Registration Page");
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
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JTextField("password");
        formPanel.add(passwordField);

        registerButton = new JButton("Register");
        formPanel.add(registerButton);
        registerButton.addActionListener(this);

        add(formPanel, BorderLayout.CENTER);
    }

    private boolean registerEmployee(Employee employee) {
        try {
            Person.writePerson(employee);
            Person.Arlow.addEmployee(employee);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            try {
                Employee employee = new Employee(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        phoneField.getText(),
                        usernameField.getText(),
                        passwordField.getText());

                if (!Person.personInFile(employee) && registerEmployee(employee)) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    new EmployeeHomeFrame(username, password);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Username or Password already exists. Please try again.");
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please fill all fields correctly: \n" + ex.getLocalizedMessage());
            }

        }
    }
}