package Hotel.UI.Frames.Guest;


import Hotel.Central.CentralProfiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestRegistrationFrame extends JFrame implements ActionListener {

    private JTextField firstNameField, lastNameField, emailField, phoneField, usernameField, passwordField;
    private JButton registerButton;

    public GuestRegistrationFrame(String username, String password) {
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
        usernameField = new JTextField(username);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JTextField(password);
        formPanel.add(passwordField);

        registerButton = new JButton("Register");
        formPanel.add(registerButton);
        registerButton.addActionListener(this);

        add(formPanel, BorderLayout.CENTER);
    }

    private boolean registerGuest(String firstname,String lastName, String email, String phoneNumber, String username, String password) {
        try {
            CentralProfiles.makeGuestProfile(firstname,lastName,email,phoneNumber,username,password);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {

            boolean skip = false;
            String firstName =  firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            if(!emailField.getText().contains("@") || !emailField.getText().substring(emailField.getText().length()-4, emailField.getText().length()).equals(".com")){
                JOptionPane.showMessageDialog(this, "Please enter email correctly with the @ and .com and the end");
                skip = true;
            }

            if(phoneField.getText().length() != 10){
                JOptionPane.showMessageDialog(this, "please enter a valid Phone number Length XXXXXXXXXX");
                skip = true;
            }


            if(!skip) {
                try {


                    if (!CentralProfiles.guestisIn(usernameField.getText()) && registerGuest(firstName, lastName, email, phoneNumber, username, password)) {
                        JOptionPane.showMessageDialog(this, "Registration successful!");
                        new GuestHomeFrame(usernameField.getText(), passwordField.getText());
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


}
