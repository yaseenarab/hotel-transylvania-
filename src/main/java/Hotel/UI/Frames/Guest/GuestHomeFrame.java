package UI.Frames.Guest;


import AccountService.Guest;
import Central.CentralProfiles;
import UI.Frames.EditReservationFrame;
import UI.Frames.LoginFrame;
import UI.Panels.ReserveRoomPanel;
import UI.Panels.ShoppingMainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestHomeFrame extends JFrame {
    private Guest guest;
    public CardLayout cl;
    public JPanel container;
    private JPanel homePanel;
    private JPanel reservePanel;
    private JPanel editPanel;
    private JPanel shoppingPanel;
    private JLabel welcomeLabel;
    private GridBagConstraints gbc;
    private JButton EditReservationBtn;
    private JButton ReserveRoomBtn;
    private JButton ShoppingBtn;
    private JButton LogoutBtn;

    public Guest getGuest() { return guest; }

    public GuestHomeFrame(String username, String password) throws IllegalArgumentException {
        if(username == null || username.isEmpty()) {
            throw new IllegalArgumentException("ERR: Invalid Guest username");
        }

        this.guest = CentralProfiles.getGuest(username, password);
        //System.out.println(guest.getFirstName());

        setTitle("Hotel Transylvania");
        setSize(new Dimension(720 , 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

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
        EditReservationBtn = new JButton("Edit Reservations");
        EditReservationBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame editPanel = new EditReservationFrame(guest);
                editPanel.setVisible(true);
            }
        });

        // Updates frame to ReserveRoomPanel
        ReserveRoomBtn = new JButton("Reserve Room");
        ReserveRoomBtn.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                try {
                    //reservePanel = new ReserveRoomPanel((GuestHomeFrame) SwingUtilities.windowForComponent((JButton) e.getSource()),  guest);
                    reservePanel = new ReserveRoomPanel(GuestHomeFrame.this, guest);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                container.add(reservePanel, "Reserve");
                container.revalidate();
                //cl.addLayoutComponent(reservePanel, "Reserve");
                cl.show(container, "Reserve");
            }
        });

        ShoppingBtn = new JButton("Shop");
        ShoppingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shoppingPanel = new ShoppingMainPanel(GuestHomeFrame.this);
                container.add(shoppingPanel, "Shop");
                //cl.addLayoutComponent(shoppingPanel, "Shop");
                //container.revalidate();
                cl.show(container, "Shop");
                container.repaint();
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
        buttons.add(ShoppingBtn, gbc);
        ++gbc.gridx;
        buttons.add(LogoutBtn, gbc);

        // Reusing GridBagConstraints for layout of homePanel
        gbc.gridx = 0;
        gbc.gridy = 1;
        homePanel.add(buttons, gbc);

        container.add(homePanel, "Home");
        add(container);
        cl.show(container, "Home");
        setVisible(true);
    }
}