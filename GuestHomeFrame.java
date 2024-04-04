package org.example;

import javax.swing.*;
import java.awt.*;

public class GuestHomeFrame extends JFrame {
    private Guest guest;
    public CardLayout cl;
    public JPanel container;
    private JPanel editPanel;
    private JPanel reservePanel;
    private JLabel welcomeLabel;
    private JButton EditReservationBtn;
    private JButton ReserveRoomBtn;
    private JButton LogoutBtn;

    public GuestHomeFrame(String username, String password) throws IllegalArgumentException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("ERR: Invalid Guest username");
        }

        // Assuming Person.Arlow.getGuest() is a valid way to retrieve a Guest object
        this.guest = Person.Arlow.getGuest(username, password);

        setTitle("Hotel Transylvania");
        setSize(new Dimension(720, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cl = new CardLayout();
        container = new JPanel(cl);

        JPanel homePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Use this to make the label span the entire row

        welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Helvetica", Font.PLAIN, 40));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        homePanel.add(welcomeLabel, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        EditReservationBtn = new JButton("Edit Reservation");
        EditReservationBtn.addActionListener(e -> {
            try {
                editPanel = new EditReservationPanel(this, guest); // Assuming EditReservationPanel constructor
                container.add(editPanel, "Edit");
                cl.show(container, "Edit");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        ReserveRoomBtn = new JButton("Reserve Room");
        ReserveRoomBtn.addActionListener(e -> {
            try {
                reservePanel = new ReserveRoomPanel(this, guest); // Assuming ReserveRoomPanel constructor
                container.add(reservePanel, "Reserve");
                cl.show(container, "Reserve");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        LogoutBtn = new JButton("Logout");
        LogoutBtn.addActionListener(e -> {
            new LoginFrame(); // Assuming LoginFrame constructor
            dispose();
        });

        buttonsPanel.add(EditReservationBtn);
        buttonsPanel.add(ReserveRoomBtn);
        buttonsPanel.add(LogoutBtn);

        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Make sure this panel also spans the entire row
        homePanel.add(buttonsPanel, gbc);

        container.add(homePanel, "Home");
        add(container);
        cl.show(container, "Home");
        setVisible(true);
    }
}