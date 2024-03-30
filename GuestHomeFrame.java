package edu.baylor.hoteltransylvania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuestHomeFrame extends JFrame {
    private GuestProfile guest;
    public CardLayout cl;
    public JPanel container;
    private JPanel homePanel;
    private JPanel reservePanel;
    private JPanel editPanel;
    private JLabel welcomeLabel;
    private GridBagConstraints gbc;
    private JButton EditReservationBtn;
    private JButton ReserveRoomBtn;
    private JButton LogoutBtn;

    public GuestHomeFrame (String username, String password) throws IllegalArgumentException {
        if(username == null || username.isEmpty()) {
            throw new IllegalArgumentException("ERR: Invalid Guest username");
        }
        setTitle("Hotel Transylvania");
        setSize(new Dimension(720 , 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // DUMMY DATA, REPLACE LATER!
        try {
            guest = new GuestProfile(
                    "Ben",
                    "Doverson",
                    "bendoverson@yahoo.com",
                    "0000000000");
        }
        catch (Exception e) {
            Logger log = Logger.getLogger("edu.baylor.hoteltransylvania");
            log.log(Level.SEVERE, "ERR: Guest profile information could not be set for GuestHomeProfile, closing...");
            dispose();
        }

        // Panel that holds all potential panels
        cl = new CardLayout();
        container = new JPanel(cl);

        // Home page, can Reserve / Edit / Logout
        homePanel = new JPanel(new GridBagLayout());

        // "Welcome, [username]"
        welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Helvetica", Font.PLAIN, 40));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Updates frame to EditReservationPanel
        EditReservationBtn = new JButton("Edit Reservation");
        EditReservationBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // logic to go to edit / see reservations
                // editPanel = new EditReseervationPanel();
                // mainPanel.add(editPanel, "Edit");
                // cl.show(editPanel, "Edit");
            }
        });

        // Updates frame to ReserveRoomPanel
        ReserveRoomBtn = new JButton("Reserve Room");
        ReserveRoomBtn.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                reservePanel = new ReserveRoomPanel((GuestHomeFrame) SwingUtilities.windowForComponent((JButton) e.getSource()), guest);
                container.add(reservePanel, "Reserve");
                cl.show(container, "Reserve");
            }
        });

        // Closes GuestHomeFrame and opens new LoginFrame
        LogoutBtn = new JButton("Logout");
        LogoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to save user information / updates, controller?
                new LoginFrame();
                dispose();
            }
        });

        // Layout of HomePanel
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = gbc.gridy = 0;
        homePanel.add(welcomeLabel, gbc);

        // Layout of JButtons
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(EditReservationBtn, gbc);
        ++gbc.gridx;
        buttons.add(ReserveRoomBtn, gbc);
        ++gbc.gridx;
        buttons.add(LogoutBtn, gbc);

        // Reusing GridBagConstraints for layout of homePanel
        gbc.gridx = 0;
        gbc.gridy = 1;
        homePanel.add(buttons, gbc);

        container.add(homePanel, "Home");
        add(container);
        cl.show(container, "Home");
        //add(homePanel);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //revalidate();
        //repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GuestHomeFrame GHF = new GuestHomeFrame("username", "password");
                GHF.setVisible(true);
            }
        });
    }
}
