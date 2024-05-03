package UI.Frames.Admin;


import AccountService.Admin;
import Central.CentralDatabase;
import Central.CentralProfiles;
import Central.CentralRoom;
import UI.Frames.Employee.EmployeeRegistrationFrame;
import UI.Panels.UpdateRoomsPanel;
import UI.Frames.LoginFrame;
import UI.Frames.CheckInFrame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

@SuppressWarnings("serial")
public class AdminHomeFrame extends JFrame {
    private Admin admin;
    public CardLayout cl;
    public JPanel container, homePanel, updateRoomPanel;
    private JLabel welcomeLabel;
    private GridBagConstraints gbc;

    private JFrame checkInFrame;
    private JButton processCheckBtn, roomStatusBtn, reservationStatusBtn,
            billingBtn, LogoutBtn, createAccountBtn, resetAccountPasswordBtn,
            initBtn;

    public AdminHomeFrame(String username, String password) throws IllegalArgumentException {
        this.admin = CentralProfiles.getAdmin(username, password);

        setTitle("Hotel Transylvania");
        setSize(new Dimension(1200 , 480));
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

        // Process a guest check in or check out
        processCheckBtn = new JButton("Check In/Out");
        processCheckBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //CentralDatabase.updateRoom(myReservation.getRoomID(), RoomStatus.OcDi);
                checkInFrame = new CheckInFrame(username, password);
                checkInFrame.setVisible(true);

            }
        });
        // Opens new frame that displays room status
        roomStatusBtn = new JButton("Rooms Status");
        roomStatusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    updateRoomPanel = new UpdateRoomsPanel(container, cl);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                container.add(updateRoomPanel, "Rooms");
                container.revalidate();
                cl.show(container, "Rooms");
            }
        });
        
        // Opens new frame that displays reservations
        reservationStatusBtn = new JButton("Reservations");
        reservationStatusBtn.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
        	public void actionPerformed(ActionEvent e) {
                JFrame reservationFrame = new updateReservationsFrame();
                reservationFrame.setVisible(true);
            }
        });

        //TODO: Connect with billing system
        // Calculates billing information for a guest
        billingBtn = new JButton("Get Billing Information");
        billingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guestUsername = JOptionPane.showInputDialog("Enter the guest's username");
                double cost = 0.0;

                ResultSet res = CentralDatabase.getReservations(guestUsername);
                try {
                    while(res.next()) {
                        cost += res.getInt("COST");
                    }
                    JOptionPane.showMessageDialog(container, guestUsername + ": $" + cost, "Balance", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception eCost) {
                    JOptionPane.showMessageDialog(container, "No account matching that username could be found");
                }
            }
        });

        // Creates a new employee account, with a default password of "password"
        createAccountBtn = new JButton("Add Employee");
        createAccountBtn.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                JFrame registrationFrame = new EmployeeRegistrationFrame();
                registrationFrame.setVisible(true);
            }
        });
        // Resets the password of a given guest to the default "password"
        resetAccountPasswordBtn = new JButton("Reset User Password");
        resetAccountPasswordBtn.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                String accountUsername = JOptionPane.showInputDialog("Enter the username whose password you would like to reset");

                try {
                    if (JOptionPane.showConfirmDialog(container, "Are you sure you want to reset " + accountUsername + "'s password?", "Confirm Password Reset", JOptionPane.YES_NO_OPTION) == 0) {
                        String guestPassword = JOptionPane.showInputDialog("Enter the new password");
                        CentralDatabase.updatePassword(accountUsername, guestPassword);
                        JOptionPane.showMessageDialog(container, "Password reset");
                    }  else {
                        JOptionPane.showMessageDialog(container, "Password was not reset");
                    }
                } catch (Exception e1) {
                    System.out.println("Unable to reset account password");
                    e1.printStackTrace();
                }
            }
        });

        initBtn = new JButton("Initialize Hotel");
        initBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!CentralRoom.initializeRooms(3, 36, username)) {
                    JOptionPane.showMessageDialog(container, "Cannot initialize rooms");
                }
                else {
                    JOptionPane.showMessageDialog(container, "Room initialization successful!");
                }
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
        buttons.add(processCheckBtn, gbc);
        ++gbc.gridx;
        buttons.add(roomStatusBtn, gbc);
        ++gbc.gridx;
        buttons.add(reservationStatusBtn, gbc);
        ++gbc.gridx;
        buttons.add(billingBtn, gbc);
        ++gbc.gridx;
        buttons.add(createAccountBtn, gbc);
        ++gbc.gridx;
        buttons.add(resetAccountPasswordBtn, gbc);
        ++gbc.gridx;
        buttons.add(initBtn, gbc);
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






