package UI.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.CENTER;

public class GuestHomePanel {
    private JPanel content;
    private JLabel welcomeLabel;
    private GridBagConstraints gbc;
    private JButton EditReservation;
    private JButton ReserveRoom;

    public GuestHomePanel(JPanel mainPanel, String guestFirstName) throws IllegalArgumentException {
        if(guestFirstName == null || guestFirstName.isEmpty()) {
            throw new IllegalArgumentException("ERR: Invalid Guest username");
        }
        content = new JPanel(new GridBagLayout());

        welcomeLabel = new JLabel("Welcome, " + guestFirstName);
        //welcomeLabel.setText("Welcome, " + username);
        welcomeLabel.setFont(new Font("Helvetica", Font.PLAIN, 40));
        welcomeLabel.setMinimumSize(new Dimension(200, 100));
        welcomeLabel.setHorizontalAlignment(CENTER);

        EditReservation = new JButton("Edit Reservation");
        EditReservation.addActionListener(new ActionListener() {
            // edit page stuff
            public void actionPerformed(ActionEvent e) {
                mainPanel.remove(content);
                // logic to go to edit / see reservations
            }
        });

        ReserveRoom = new JButton("Reserve Room");
        ReserveRoom.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                mainPanel.remove(content);
                //mainPanel.add(new ReserveRoomPanel(mainPanel, guestFirstName));
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        gbc = new GridBagConstraints();
        //gbc.fill = HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        //gbc.fill = BOTH;
        gbc.gridx = gbc.gridy = 0;
        content.add(welcomeLabel, gbc);
        ++gbc.gridy;
        content.add(EditReservation, gbc);
        ++gbc.gridx;
        content.add(ReserveRoom, gbc);
    }

    public JPanel getPanel() {
        return this.content;
    }
}
