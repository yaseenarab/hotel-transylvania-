package Hotel.UI;

import Hotel.AccountService.Guest;
import Hotel.DataBaseManipulation.DataGetSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ReservationFrame extends JFrame implements ActionListener {
    private JLabel titleLabel, reservationLabel;
    private JTextField reservationTextField;
    private JButton addReservationButton, editReservationButton, cancelReservationButton, addOneMoreButton;
    private JTextArea reservationsTextArea; // Added JTextArea for displaying reservations
    private Guest guest;

    private ArrayList<String> reservations; // Store reservations for the user

    public ReservationFrame(Guest guest) {
        setSize(900, 500);
        this.guest = guest;
        // Initialize reservations ArrayList
        reservations = new ArrayList<>();
        giveReservaitons();

        // Creating components
        titleLabel = new JLabel("Your Reservations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);



        reservationTextField = new JTextField(15);

        addReservationButton = new JButton("Add Reservation");
        addReservationButton.addActionListener( new ActionListener() {
                public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addReservationButton) {
                // Create a new instance of ReserveRoomPanel passing the guest information
                ReserveRoomPanel reserveRoomPanel;
                try {
                    reserveRoomPanel = new ReserveRoomPanel(guest);
                    // Set the content pane of the frame to the ReserveRoomPanel
                    setContentPane(reserveRoomPanel);
                    // Validate and repaint the frame to ensure the new panel is displayed
                    validate();
                    repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }}}});


        editReservationButton = new JButton("Edit Reservation");
        editReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedReservation = reservationsTextArea.getSelectedText(); // Get the highlighted text

                if (selectedReservation != null && !selectedReservation.isEmpty()) {
                    int index = reservations.indexOf(selectedReservation);
                    if (index != -1) {
                        int response = JOptionPane.showConfirmDialog(ReservationFrame.this, "Are you sure you want to edit this reservation?", "Edit Reservation", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            try {
                                // Parse the selected reservation string to extract room number and start date
                                String[] parts = selectedReservation.split("\\|\\|");
                                String roomNumber = parts[0].substring(parts[0].indexOf(":") + 1).trim();
                                String startDate = parts[1].substring(parts[1].indexOf(":") + 1, parts[1].indexOf("End Date:")).trim();

                                // Delete the current reservation
                                Connection con = DriverManager.getConnection("jdbc:derby:ReservationsData;");
                                String deleteQuery = "DELETE FROM Reservations WHERE RoomNumber = ? AND StartDate = ?";
                                PreparedStatement pstmt = con.prepareStatement(deleteQuery);
                                pstmt.setInt(1, Integer.parseInt(roomNumber));
                                pstmt.setString(2, startDate);
                                int rowsAffected = pstmt.executeUpdate();

                                if (rowsAffected > 0) {
                                    // Reservation canceled successfully
                                    reservations.remove(index);
                                    displayReservations();
                                    ReserveRoomPanel reserveRoomPanel2;

                                    // Take the user to the reservation page
                                     reserveRoomPanel2 = new ReserveRoomPanel(guest);
                                    setContentPane(reserveRoomPanel2);
                                    validate();
                                    repaint();

                                    JOptionPane.showMessageDialog(ReservationFrame.this, "Please make a new reservation.");
                                } else {
                                    JOptionPane.showMessageDialog(ReservationFrame.this, "Failed to edit reservation!");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(ReservationFrame.this, "Please select a valid reservation to edit!");
                    }
                } else {
                    JOptionPane.showMessageDialog(ReservationFrame.this, "Please highlight/select a reservation to edit!");
                }
            }
        });
        cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedReservation = reservationsTextArea.getSelectedText(); // Get the highlighted text

                if (selectedReservation != null && !selectedReservation.isEmpty()) {
                    int index = reservations.indexOf(selectedReservation);
                    if (index != -1) {
                        int response = JOptionPane.showConfirmDialog(ReservationFrame.this, "Are you sure you want to cancel this reservation?", "Cancel Reservation", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            try {
                                // Parse the selected reservation string to extract room number and start date
                                String[] parts = selectedReservation.split("\\|\\|");
                                String roomNumber = parts[0].substring(parts[0].indexOf(":") + 1).trim();
                                String startDate = parts[1].substring(parts[1].indexOf(":") + 1, parts[1].indexOf("End Date:")).trim();
                                Connection con = null;
                                con = DriverManager.getConnection("jdbc:derby:ReservationsData;");
                                Statement stmt = con.createStatement();
                                // Prepare the delete statement
                                String deleteQuery = "DELETE FROM Reservations WHERE RoomNumber = ? AND StartDate = ?";
                                PreparedStatement pstmt = con.prepareStatement(deleteQuery);

                                // Set parameters
                                pstmt.setInt(1, Integer.parseInt(roomNumber)); // Assuming roomNumber is an integer
                                pstmt.setString(2, startDate); // Assuming startDate is a string in 'YYYY-MM-DD' format

                                // Execute the update
                                int rowsAffected = pstmt.executeUpdate();

                                if (rowsAffected > 0) {
                                    // Remove the reservation from the list
                                    reservations.remove(index);
                                    displayReservations(); // Update reservations display
                                    JOptionPane.showMessageDialog(ReservationFrame.this, "Reservation canceled successfully!");
                                } else {
                                    JOptionPane.showMessageDialog(ReservationFrame.this, "Failed to cancel reservation!");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(ReservationFrame.this, "Please select a valid reservation to cancel!");
                    }
                } else {
                    JOptionPane.showMessageDialog(ReservationFrame.this, "Please highlight/select a reservation to cancel!");
                }
            }
        });







        // JTextArea for displaying reservations
        reservationsTextArea = new JTextArea(20, 40);
        reservationsTextArea.setEditable(false); // Make it non-editable
        JScrollPane scrollPane = new JScrollPane(reservationsTextArea); // Add scroll functionality
        displayReservations();

        // Creating panel for better layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
       // formPanel.add(reservationLabel);
        //formPanel.add(reservationTextField);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Adjust spacing
        buttonPanel.add(addReservationButton);
        buttonPanel.add(editReservationButton);
        buttonPanel.add(cancelReservationButton);

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
        Connection con = null;

        try {
            DataGetSet d = new DataGetSet();

            con = DriverManager.getConnection("jdbc:derby:ReservationsData;");
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM Reservations where Username = '" + guest.getUsername()+"'");

            while(res.next()){

                reservations.add("RoomNumber: " + res.getString("RoomNumber") + " || Start Date: " + res.getString("StartDate") + "    End Date: " + res.getString("EndDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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