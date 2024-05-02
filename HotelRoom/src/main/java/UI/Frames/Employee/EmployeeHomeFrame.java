package UI.Frames.Employee;


import AccountService.Employee;
import Central.CentralProfiles;
import Central.CentralReservations;
import UI.Frames.LoginFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeHomeFrame extends JFrame {
    private Employee employee;
    public CardLayout cl;
    public JPanel container;
    private JPanel homePanel;
    private JPanel reservePanel;
    private JPanel editPanel;
    private JLabel welcomeLabel;
    private GridBagConstraints gbc;
    private JButton processCheckBtn;
    private JButton roomStatusBtn;
    private JButton reservationStatusBtn;
    private JButton billingBtn;
    private JButton LogoutBtn;
    private JButton createAccountBtn;
    private JButton resetAccountPasswordBtn;

    public EmployeeHomeFrame(String username, String password) throws IllegalArgumentException {
        this.employee = CentralProfiles.getEmployee(username, password);

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
                try {
                    //Reservations reservations = new Reservations();
                } catch (Exception e1) {
                    System.out.println("Exception caught in Room Status");
                }
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
                    ResultSet res = CentralReservations.getReservaions();

                    while(res.next()) {

                        String[] split =  new String[4];

                        split[0]= res.getInt("roomnumber") +"";
                        split[1]= res.getString("username") +"";
                        split[2]= res.getString("startdate") +"";
                        split[3]= res.getString("enddate") +"";

                        reservationModel.addRow(split);

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
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
        if (employee.getEmployeeID().contains("TVAI")) {
            buttons.add(createAccountBtn, gbc);
            ++gbc.gridx;
            buttons.add(resetAccountPasswordBtn, gbc);
            ++gbc.gridx;
        }
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
        return CentralProfiles.getGuestID(accountUsername);
    }
}






