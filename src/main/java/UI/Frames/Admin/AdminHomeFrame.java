package UI.Frames.Admin;


import AccountService.Admin;
import AccountService.Employee;
import AccountService.Guest;
import Central.CentralDatabase;
import Central.CentralProfiles;
import Central.CentralRoom;
import UI.Frames.Employee.EmployeeHomeFrame;
import UI.Frames.Employee.EmployeeRegistrationFrame;
import UI.Frames.LoginFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdminHomeFrame extends JFrame {
    private Admin admin;
    public CardLayout cl;
    public JPanel container, homePanel;
    private JLabel welcomeLabel;
    private GridBagConstraints gbc;
    private JButton processCheckBtn, roomStatusBtn, reservationStatusBtn,
            billingBtn, LogoutBtn, createAccountBtn, resetAccountPasswordBtn,
            initBtn;
    private static EmployeeHomeFrame employeeFrame;

    public AdminHomeFrame(String username, String password) throws IllegalArgumentException {
        this.admin = CentralProfiles.getAdmin(username, password);

        setTitle("Hotel Transylvania");
        setSize(new Dimension(1080 , 480));
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
                //TODO: ADD LOGIC FOR CHECKING IN
                JOptionPane.showMessageDialog(container, "Not implemented yet");
            }
        });

        //TODO: Add edit functionality
        // Opens new frame that displays room status
        roomStatusBtn = new JButton("Rooms Status");
        roomStatusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame = new JFrame();
                JPanel tablePanel= new JPanel();

                String[] columnNames = {"Rm #", "Rm Typ", "Qual Lvl", "Rm Stat", "Bd Typ", "Smokng"};
                DefaultTableModel roomAvailModel = new DefaultTableModel();
                for(String name : columnNames) {
                    roomAvailModel.addColumn(name);
                }


                JTable availRooms = new JTable(roomAvailModel);
                tableFrame.getContentPane().add(new JScrollPane(availRooms));
                availRooms.setFillsViewportHeight(true);

                tablePanel.setLayout(new BorderLayout());
                tableFrame.setSize(500,500);
                tableFrame.setVisible(true);
            }
        });

        //TODO: Add edit functionality
        // Opens new frame that displays reservations
        reservationStatusBtn = new JButton("Reservations");
        reservationStatusBtn.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame = new JFrame();
                JPanel tablePanel= new JPanel();

                String[] columnNames = {"Rm #", "Guest", "Start Date", "End Date"};
                DefaultTableModel reservationModel = new DefaultTableModel();
                for(String name : columnNames) {
                    reservationModel.addColumn(name);
                }

                try {
                    Scanner s = new Scanner(new File("./src/main/resources/reservations.csv"));
                    while(s.hasNextLine()) {
                        String line = s.nextLine();
                        String[] split = line.split(",");
                        reservationModel.addRow(split);

                    }
                } catch (FileNotFoundException e1) {
                    throw new RuntimeException(e1);
                }
                JTable availRooms = new JTable(reservationModel);
                tableFrame.getContentPane().add(new JScrollPane(availRooms));
                availRooms.setFillsViewportHeight(true);

                tablePanel.setLayout(new BorderLayout());
                tableFrame.setSize(500,500);
                tableFrame.setVisible(true);
            }
        });

        //TODO: Connect with billing system
        // Calculates billing information for a guest
        billingBtn = new JButton("Get Billing Information");
        billingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guestUsername = JOptionPane.showInputDialog("Enter the guest's username");
                String accountID = getAccountIDFromUsername(guestUsername);

                if (accountID != null) {
                    JOptionPane.showMessageDialog(container, guestUsername + ": some balance", "Balance", JOptionPane.PLAIN_MESSAGE);
                } else {
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

        //TODO: Reset password in database, not just in Arlow
        // Resets the password of a given guest to the default "password"
        resetAccountPasswordBtn = new JButton("Reset User Password");
        resetAccountPasswordBtn.addActionListener(new ActionListener() {
            // Load reserve room panel into frame
            public void actionPerformed(ActionEvent e) {
                String accountUsername = JOptionPane.showInputDialog("Enter the username whose password you would like to reset");
                String accountID = getAccountIDFromUsername(accountUsername);

                if (accountID != null) {
                    try {
                        if (JOptionPane.showConfirmDialog(container, "Are you sure you want to reset " + accountUsername + "'s password?", "Confirm Password Reset", JOptionPane.YES_NO_OPTION) == 0) {
                            //Doesn't change password in csv, didn't want to waste time figuring that out when we're using a database anyway -C
                            //Person.Arlow.resetPassword(accountID);
                            JOptionPane.showMessageDialog(container, "Password reset for " + accountUsername);
                        }  else {
                            JOptionPane.showMessageDialog(container, "Password was not reset");
                        }
                    } catch (Exception e1) {
                        System.out.println("Unable to reset account password");
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(container, "No account matching that username could be found");
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

    private static String getAccountIDFromUsername(String accountUsername) {
        String accountID = null;
        try (Scanner scanner = new Scanner(new File("./src/main/resources/Person_Profiles.csv"))) {
            while (scanner.hasNextLine() && accountID == null) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 7 &&
                        parts[5].equals(accountUsername)) {
                    accountID = parts[0];
                }
            }
        }

        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return accountID;
    }
}






