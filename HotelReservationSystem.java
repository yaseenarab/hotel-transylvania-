package org.example;

import javax.swing.*;

public class HotelReservationSystem {
    private JFrame panel;

    public HotelReservationSystem(GuestProfile guest) {
        panel = new JFrame("Hotel Reservation System");
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setSize(800, 600);

        ReserveRoomPanel reserveRoomPanel = new ReserveRoomPanel(guest);
        panel.add(reserveRoomPanel);
        panel.pack();
        panel.setLocationRelativeTo(null);
        panel.setVisible(true);
    }
}