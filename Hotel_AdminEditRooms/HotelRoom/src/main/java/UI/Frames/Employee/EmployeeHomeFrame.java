package UI.Frames.Employee;


import AccountService.Employee;
import Central.CentralProfiles;
import UI.Frames.LoginFrame;
import UI.Frames.Admin.updateReservationsFrame;
import UI.Panels.UpdateRoomsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class EmployeeHomeFrame extends JFrame {
    private Employee employee;
    public CardLayout cl;
    public JPanel container;
    private JPanel homePanel;
    private JPanel updateRoomPanel;
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

     // Opens new frame that displays room status
        roomStatusBtn = new JButton("Rooms Status");
        roomStatusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {                   
                    updateRoomPanel = new UpdateRoomsPanel();
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
}






