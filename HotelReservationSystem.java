package org.example;

import javax.swing.*;

public class HotelReservationSystem {
    private JFrame panel;

    public HotelReservationSystem() {
        panel = new JFrame("Hotel Reservation System");
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setSize(800, 600);

        ReserveRoomPanel reserveRoomPanel = new ReserveRoomPanel();
        panel.add(reserveRoomPanel);
        panel.pack();
        panel.setLocationRelativeTo(null);
        panel.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelReservationSystem::new);
    }
}