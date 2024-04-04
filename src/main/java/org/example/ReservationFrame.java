package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationFrame extends JFrame implements ActionListener {
    private JLabel titleLabel, reservationLabel;
    private JTextField reservationTextField;
    private JButton addReservationButton, editReservationButton, cancelReservationButton, addOneMoreButton;
    private JTextArea reservationsTextArea; // Added JTextArea for displaying reservations
    private Guest guest;

    private ArrayList<String> reservations; // Store reservations for the user

    public ReservationFrame(Guest guest) {
        //setTitle("Reservation System");
        setSize(400, 300);
        //etDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null); // Center the frame on the screen
        this.guest = guest;
        // Initialize reservations ArrayList
        reservations = new ArrayList<>();
        giveReservaitons();

        // Creating components
        titleLabel = new JLabel("Your Reservations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        reservationLabel = new JLabel("Reservation:");
        reservationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        reservationTextField = new JTextField(15);
        addReservationButton = new JButton("Add Reservation");
        addReservationButton.addActionListener(this);
        editReservationButton = new JButton("Edit Reservation");
        editReservationButton.addActionListener(this);
        cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.addActionListener(this);
        addOneMoreButton = new JButton("Add One More");
        addOneMoreButton.addActionListener(this);

        // JTextArea for displaying reservations
        reservationsTextArea = new JTextArea(10, 20);
        reservationsTextArea.setEditable(false); // Make it non-editable
        JScrollPane scrollPane = new JScrollPane(reservationsTextArea); // Add scroll functionality
        displayReservations();
        // Creating panel for better layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.add(reservationLabel);
        formPanel.add(reservationTextField);

        formPanel.add(new JLabel()); // Empty label for spacing

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Adjust spacing
        buttonPanel.add(addReservationButton);
        buttonPanel.add(editReservationButton);
        buttonPanel.add(cancelReservationButton);
        buttonPanel.add(addOneMoreButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.WEST); // Add scrollPane to display reservations
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding panel to the frame
        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addReservationButton) {
            String reservation = reservationTextField.getText();
            reservations.add(reservation);
            displayReservations(); // Update reservations display
            JOptionPane.showMessageDialog(this, "Reservation added successfully!");
        } else if (e.getSource() == editReservationButton) {
            int index = getSelectedReservationIndex();
            if (index != -1) {
                String newReservation = JOptionPane.showInputDialog(this, "Enter new reservation:");
                reservations.set(index, newReservation);
                displayReservations(); // Update reservations display
                JOptionPane.showMessageDialog(this, "Reservation edited successfully!");
            }
        } else if (e.getSource() == cancelReservationButton) {
            int index = getSelectedReservationIndex();
            if (index != -1) {
                reservations.remove(index);
                displayReservations(); // Update reservations display
                JOptionPane.showMessageDialog(this, "Reservation canceled successfully!");
            }
        } else if (e.getSource() == addOneMoreButton) {
            reservationTextField.setText(""); // Clear text field for adding a new reservation
        }
    }
    private void giveReservaitons(){
        try{
            Scanner s = new Scanner(new File("reservations.csv"));
            while(s.hasNextLine()){
                String line = s.nextLine();
                String []split = line.split(",");
                reservations.add("Room Number:"+split[0] + " || Start Date: " + split[2] + "   End Date: " + split[3]);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private int getSelectedReservationIndex() {
        String selectedReservation = JOptionPane.showInputDialog(this, "Enter the reservation to edit/cancel:");
        return reservations.indexOf(selectedReservation);
    }

    private void displayReservations() {
        reservationsTextArea.setText(""); // Clear the text area
        for (String reservation : reservations) {
            reservationsTextArea.append(reservation + "\n"); // Append each reservation to the text area
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //ReservationFrame reservationFrame = new ReservationFrame();
                //reservationFrame.setVisible(true);
            }
        });
    }
}