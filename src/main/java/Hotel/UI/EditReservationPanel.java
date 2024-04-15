package Hotel.UI;

import Hotel.AccountService.Guest;
import Hotel.ReservationService.Reservations;
import Hotel.ReservationService.RoomReservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class EditReservationPanel extends JPanel {
    private Guest guest;
    private Reservations reservations;

    public EditReservationPanel(GuestHomeFrame frame, Guest guest) throws Exception {
        this.guest = guest;
        this.reservations = new Reservations(); // Initialize the reservations system
        setLayout(new BorderLayout());

        // Fetch reservations for the guest
        List<RoomReservation> guestReservations = fetchReservationsForGuest(guest);

        // Display each reservation with options to edit or remove
        JPanel reservationsPanel = new JPanel(new GridLayout(guestReservations.size(), 1));
        for (RoomReservation reservation : guestReservations) {
            JPanel reservationPanel = new JPanel(new FlowLayout());
            JLabel reservationLabel = new JLabel("Room " + reservation.getRoom().getRoomNumber() + ": " + reservation.getStartDate() + " to " + reservation.getEndDate());
            JButton editButton = new JButton("Edit");
            JButton removeButton = new JButton("Remove");

            // Add action listeners for buttons
            editButton.addActionListener(e -> editReservation(reservation));
            removeButton.addActionListener(e -> removeReservation(reservation));

            reservationPanel.add(reservationLabel);
            reservationPanel.add(editButton);
            reservationPanel.add(removeButton);
            reservationsPanel.add(reservationPanel);
        }

        add(reservationsPanel, BorderLayout.CENTER);

        // Add room button
        JButton addRoomButton = new JButton("Add Room");
        addRoomButton.addActionListener(e -> addRoomToReservation());
        add(addRoomButton, BorderLayout.NORTH);

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            frame.dispose();
            new GuestHomeFrame(guest.getUsername(), guest.getPassword()).setVisible(true);
        });
        add(exitButton, BorderLayout.SOUTH);

    }

    private List<RoomReservation> fetchReservationsForGuest(Guest guest) {
        // Mock implementation, replace with actual logic to fetch reservations from the Reservations system
        return new ArrayList<>();
    }

    private void editReservation(RoomReservation reservation) {
        // Implement the logic to edit the selected reservation
        System.out.println("Editing reservation: " + reservation);
        // Open a dialog or another panel to edit reservation details
    }

    private void removeReservation(RoomReservation reservation) {
        // Implement the logic to remove the selected reservation
        System.out.println("Removing reservation: " + reservation);
        // Remove the reservation from the reservations system
    }

    private void addRoomToReservation() {
        // Implement the logic to add a new room to the reservation
        System.out.println("Adding new room to reservation");
        // Logic to add a new room
    }
}
