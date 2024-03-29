package edu.baylor.hoteltransylvania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.*;
public class GuestHomeFrame extends JFrame {
    //private GuestHomeController GHC;
    private JPanel content;
    private JLabel welcomeLabel;
    private GridBagConstraints gbc;
    private JButton EditReservation;
    private JButton ReserveRoom;
    private JButton Logout;

    public GuestHomeFrame (String username) throws IllegalArgumentException {
        if(username == null || username.isEmpty()) {
            throw new IllegalArgumentException("ERR: Invalid Guest username");
        }
        setTitle("Hotel Transylvania");
        setSize(new Dimension(720 , 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        content = new JPanel(new GridBagLayout());

        welcomeLabel = new JLabel("Welcome, " + username);
        //welcomeLabel.setText("Welcome, " + username);
        welcomeLabel.setFont(new Font("Helvetica", Font.PLAIN, 40));
        //welcomeLabel.setMinimumSize(new Dimension(200, 100));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        EditReservation = new JButton("Edit Reservation");
        EditReservation.addActionListener(new ActionListener() {
            // edit page stuff
            public void actionPerformed(ActionEvent e) {
                // logic to go to edit / see reservations
            }
        });

        ReserveRoom = new JButton("Reserve Room");
        ReserveRoom.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                //remove(content);
                remove(content);
                //add(new ReserveRoomPanel( , username));
                //revalidate();
                //repaint();
            }
        });

        Logout = new JButton("Logout");
        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to save user information / updates, controller?
                new LoginFrame();
                dispose();
            }
        });

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        //gbc.fill = BOTH;
        //gbc.fill = HORIZONTAL;
        gbc.gridx = gbc.gridy = 0;
        content.add(welcomeLabel, gbc);

        JPanel buttons = new JPanel(new FlowLayout());
        //++gbc.gridy;
        //content.add(EditReservation, gbc);
        buttons.add(EditReservation, gbc);
        ++gbc.gridx;
        //content.add(ReserveRoom, gbc);
        buttons.add(ReserveRoom, gbc);
        ++gbc.gridx;
        //content.add(Logout, gbc);
        buttons.add(Logout, gbc);

        //setLayout(null);
        gbc.gridx = 0;
        gbc.gridy = 1;
        content.add(buttons, gbc);
        add(content);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //revalidate();
        //repaint();
    }

    public JPanel GuestHomePanel(String username) {
        JPanel GHP = new JPanel(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        //welcomeLabel.setText("Welcome, " + username);
        welcomeLabel.setFont(new Font("Helvetica", Font.PLAIN, 40));
        //welcomeLabel.setMinimumSize(new Dimension(200, 100));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton EditReservation = new JButton("Edit Reservation");
        EditReservation.addActionListener(new ActionListener() {
            // edit page stuff
            public void actionPerformed(ActionEvent e) {
                // logic to go to edit / see reservations
            }
        });

        JButton ReserveRoom = new JButton("Reserve Room");
        ReserveRoom.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                //remove(content);
                remove(content);
                //add(new ReserveRoomPanel(content, username));
                revalidate();
                repaint();
            }
        });

        JButton Logout = new JButton("Logout");
        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to save user information / updates, controller?
                new LoginFrame();
                dispose();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        //gbc.fill = BOTH;
        //gbc.fill = HORIZONTAL;
        gbc.gridx = gbc.gridy = 0;
        GHP.add(welcomeLabel, gbc);

        JPanel buttons = new JPanel(new FlowLayout());
        //++gbc.gridy;
        //content.add(EditReservation, gbc);
        buttons.add(EditReservation, gbc);
        ++gbc.gridx;
        //content.add(ReserveRoom, gbc);
        buttons.add(ReserveRoom, gbc);
        ++gbc.gridx;
        //content.add(Logout, gbc);
        buttons.add(Logout, gbc);

        //setLayout(null);
        gbc.gridx = 0;
        gbc.gridy = 1;
        GHP.add(buttons, gbc);

        return GHP;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GuestHomeFrame GHF = new GuestHomeFrame("username");
                GHF.setVisible(true);
            }
        });
    }

    //public JPanel getPanel() {return this.content;}
}
