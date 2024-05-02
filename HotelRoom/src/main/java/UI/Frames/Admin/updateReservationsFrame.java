package UI.Frames.Admin;

import AccountService.Guest;
import Central.CentralDatabase;
import Central.CentralProfiles;
import Central.CentralReservations;
import Central.CentralRoom;
import Utilities.DateProcessor;
import UI.Panels.ReserveRoomPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class updateReservationsFrame extends JFrame implements ActionListener {
	private JLabel futureLabel, activeLabel;
    private JTextField reservationTextField;
    private JButton addReservationButton, editReservationButton, cancelReservationButton, addOneMoreButton;
    private JTextArea reservationsTextArea, activeReservationsTextArea; // Added JTextArea for displaying reservations
    private Guest guest;
    private ReserveRoomPanel reserveRoomPanel2;

    private ArrayList<String> futureReservations, activeReservations; // Store reservations for the user

    public updateReservationsFrame() {
        // JTextArea for displaying reservations
       defaultPanel();
    }

    private void defaultPanel(){
        setSize(900, 500);
        // Initialize reservations ArrayList

        futureReservations = new ArrayList<>();
        activeReservations = new ArrayList<>();

        JPanel titlePanel= new JPanel();

        // Creating components
        futureLabel = new JLabel("Future Reservations");
        futureLabel.setFont(new Font("Arial", Font.BOLD, 20));
        futureLabel.setPreferredSize(new Dimension(500,30));

        activeLabel = new JLabel("Active Reservations");
        activeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        //activeLabel.setPreferredSize(new Dimension(500,30));
        //titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(futureLabel,BorderLayout.WEST);
        titlePanel.add(activeLabel,BorderLayout.EAST);

        reservationTextField = new JTextField(15);
        addReservationButton = new JButton("Add Reservation");
        addReservationButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addReservationButton) {
                    // Create a new instance of ReserveRoomPanel passing the guest information
                    ReserveRoomPanel reserveRoomPanel;
                    try {
                    	String guestUsername = JOptionPane.showInputDialog(updateReservationsFrame.this, "Please enter the guest's username: ");
                    	ResultSet guestData = CentralDatabase.getGuest(guestUsername);
                    	if (guestData.next()) {
                    		guest = new Guest(guestData.getString("PersonID"), guestData.getString("FirstName"), guestData.getString("LastName"), guestData.getString("Email"), guestData.getString("PhoneNumber"), guestData.getString("Username"), guestData.getString("Password"));
                    		reserveRoomPanel = new ReserveRoomPanel(guest);
                            // Set the content pane of the frame to the ReserveRoomPanel
                            JButton exitButton = reserveRoomPanel.getExitButton();
                            exitButton.addActionListener(ex -> exit(null, null));
                            setContentPane(reserveRoomPanel);
                            validate();
                            repaint();
                    	} else {
                    		JOptionPane.showMessageDialog(updateReservationsFrame.this, "Guest could not be found");
                    	}
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }}}});



        editReservationButton = new JButton("Edit Reservation");
        editReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedReservation = reservationsTextArea.getSelectedText(); // Get the highlighted text
                boolean futureSelected = !(selectedReservation == null);
                if(!futureSelected){
                    selectedReservation = activeReservationsTextArea.getSelectedText();
                }

                if (selectedReservation != null && !selectedReservation.isEmpty()) {
                    int index = futureSelected ? futureReservations.indexOf(selectedReservation) : activeReservations.indexOf(selectedReservation);
                    if (index != -1) {
                        int response = JOptionPane.showConfirmDialog(updateReservationsFrame.this, "Are you sure you want to edit this reservation?", "Edit Reservation", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            try {
                                // Parse the selected reservation string to extract room number and start date
                                String[] parts = selectedReservation.split("\\|\\|");
                                String roomNumber = parts[0].substring(parts[0].indexOf(":") + 1).trim();
                                String startDate = parts[1].substring(parts[1].indexOf(":") + 1, parts[1].indexOf("End Date:")).trim();

                                // Delete the current reservation
                                Connection con = CentralDatabase.getConReservationDatabase();
                                Statement stmt = con.createStatement();
                                String saveData = "Select * from Reservations where RoomNumber = "+Integer.parseInt(roomNumber);

                                ResultSet res = stmt.executeQuery(saveData);

                                Date currentDate = new Date();
                                Integer roomID = null;
                                String roomType;
                                String bedType;
                                String qualityLevel;

                                boolean smoking;
                                BigDecimal startToNow = null;
                                BigDecimal startToEnd= null;
                                String endDate = null;
                                BigDecimal perNight = null;
                                String originalResID = null;
                                double cost = 0;
                                
                                String guestUsername = null;


                                if(res.next()){
                                    System.out.println(res.getInt("RoomNumber"));
                                    System.out.println("RESERvation Id + " + res.getString("reservationID"));

                                    roomID = res.getInt("Roomnumber");
                                    endDate = res.getString("EndDate");
                                    cost = res.getDouble("cost");
                                    startToNow =new BigDecimal( DateProcessor.differenceinDays(DateProcessor.stringToDate(startDate),currentDate));
                                    startToEnd = new BigDecimal(DateProcessor.differenceinDays(DateProcessor.stringToDate(startDate),DateProcessor.stringToDate(endDate)));
                                    perNight = CentralRoom.calculatorCost(res.getInt("roomNumber"));
                                    originalResID = res.getString("ReservationID");
                                    guestUsername = res.getString("Username");
                                    System.out.println(guestUsername);

                                    saveData = "INSERT INTO Reservations(Roomnumber, username,startDate,EndDate,ReservationId,cost) values(" + roomID +",'" + res.getString("Username") +"','"+ res.getString("StartDate") +"','"+ res.getString("EndDate") +"','"+ res.getString("ReservationId") +"',"+ res.getDouble("Cost") +")";
                                }
                                BigDecimal totalCost = new BigDecimal(cost);
                                BigDecimal souvenierCost = startToEnd.multiply(perNight);
                                souvenierCost = totalCost.subtract(souvenierCost);

                                System.out.println("STARTNOW BIG DECI : " + startToNow);
                                System.out.println("PERNIGHT BIG DECI : " + perNight);
                                System.out.println("Souvenier Cost: ");
                                BigDecimal firstHalf = startToNow.multiply(perNight);
                                double firstHalfCost = firstHalf.doubleValue();
                                System.out.println( "The first HALF: " + firstHalfCost);


                                String deleteQuery = "DELETE FROM Reservations WHERE RoomNumber = ? AND StartDate = ?";
                                PreparedStatement pstmt = con.prepareStatement(deleteQuery);
                                pstmt.setInt(1, Integer.parseInt(roomNumber));
                                pstmt.setString(2, startDate);
                                int rowsAffected = pstmt.executeUpdate();
                                
                                if (rowsAffected > 0) {
                                    // Reservation canceled successfully
                                    if(futureSelected){
                                        futureReservations.remove(index);
                                    }
                                    else{
                                        activeReservations.remove(index);
                                    }

                                    displayReservations();



                                    // Take the user to the reservation page
                                    System.out.println(CentralProfiles.guestisIn(guestUsername));
                                    
                                    ResultSet guestData = CentralDatabase.getGuest(guestUsername);
                                    guestData.next();
                                    guest = new Guest(guestData.getString("PersonID"), guestData.getString("FirstName"), guestData.getString("LastName"), guestData.getString("Email"), guestData.getString("PhoneNumber"), guestData.getString("Username"), guestData.getString("Password"));
                                    reserveRoomPanel2 = new ReserveRoomPanel(guest);
                                    setContentPane(reserveRoomPanel2);
                                    validate();
                                    repaint();

                                    JButton exitButton =  reserveRoomPanel2.getExitButton();
                                    String finalSaveData = saveData;
                                    Statement finalStmt = stmt;


                                    exitButton.addActionListener(ex -> exit(finalSaveData, finalStmt));


                                    JButton reserveButton = reserveRoomPanel2.getReserveButton();
                                    for(ActionListener al : reserveButton.getActionListeners()){
                                        reserveButton.removeActionListener(al);
                                    }
                                    final String[] resID = {null};
                                    if(futureSelected){
                                        reserveButton.addActionListener(ex ->exit(null,null));
                                    }
                                    else{
                                        String finalOriginalResID = originalResID;
                                        BigDecimal finalSouvenierCost = souvenierCost;
                                        reserveButton.addActionListener(ex ->finalizeEdit(resID[0], finalOriginalResID, firstHalfCost, finalSouvenierCost));
                                    }

                                    addWindowListener(new WindowAdapter()
                                    {
                                        @Override
                                        public void windowClosing(WindowEvent e)
                                        {
                                            System.out.println("Closed");
                                            exit(finalSaveData, finalStmt);
                                            e.getWindow().dispose();
                                        }
                                    });

                                    reserveButton.addActionListener(ex -> {
                                       resID[0] = reserveRoomPanel2.performReservation();
                                    });



                                    con = CentralDatabase.getConReservationDatabase();
                                    stmt = con.createStatement();
                                    res = stmt.executeQuery("Select * from Rooms where Roomnumber = "+ roomID);
                                    res.next();
                                    roomType = res.getString("roomtype");
                                    bedType = res.getString("bedType");
                                    qualityLevel = res.getString("QualityLevel");
                                    smoking = res.getBoolean("smokingAllowed");

                                    reserveRoomPanel2.fillBoxes(roomID,roomType,bedType,qualityLevel,smoking,(futureSelected ? startDate : DateProcessor.dateToString(currentDate)),endDate);

                                    JOptionPane.showMessageDialog(updateReservationsFrame.this, "Please make a new reservation.");
                                } else {
                                    JOptionPane.showMessageDialog(updateReservationsFrame.this, "Failed to edit reservation!");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(updateReservationsFrame.this, "Please select a valid reservation to edit!");
                    }
                } else {
                    JOptionPane.showMessageDialog(updateReservationsFrame.this, "Please highlight/select a reservation to edit!");
                }
            }
        });

        cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedReservation = reservationsTextArea.getSelectedText(); // Get the highlighted text
                boolean futureSelected = !(selectedReservation == null);
                if(!futureSelected){
                    selectedReservation = activeReservationsTextArea.getSelectedText();
                }

                if (selectedReservation != null && !selectedReservation.isEmpty()) {
                    int index = futureSelected ? futureReservations.indexOf(selectedReservation) : activeReservations.indexOf(selectedReservation);
                    if (index != -1) {
                        int response = JOptionPane.showConfirmDialog(updateReservationsFrame.this, "Are you sure you want to cancel this reservation?", "Cancel Reservation", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            try {
                                // Parse the selected reservation string to extract room number and start date
                                String[] parts = selectedReservation.split("\\|\\|");
                                String roomNumber = parts[0].substring(parts[0].indexOf(":") + 1).trim();
                                String startDate = parts[1].substring(parts[1].indexOf(":") + 1, parts[1].indexOf("End Date:")).trim();
                                Connection con = null;
                                con = CentralDatabase.getConReservationDatabase();
                                Statement stmt = con.createStatement();
                                // Prepare the delete statement
                                ResultSet res = stmt.executeQuery("Select * FROM Reservations where StartDate = '" + startDate + "' AND Roomnumber = " + Integer.parseInt(roomNumber));
                                boolean checkedIn = false;
                                if(res.next()){
                                    checkedIn = res.getBoolean("checkedin");

                                }
                                int rowsAffected;
                                if(!checkedIn){
                                    String deleteQuery = "DELETE FROM Reservations WHERE RoomNumber = ? AND StartDate = ?";
                                    PreparedStatement pstmt = con.prepareStatement(deleteQuery);

                                    // Set parameters
                                    pstmt.setInt(1, Integer.parseInt(roomNumber)); // Assuming roomNumber is an integer
                                    pstmt.setString(2, startDate); // Assuming startDate is a string in 'YYYY-MM-DD' format
                                    rowsAffected= pstmt.executeUpdate();
                                }
                                else{
                                    rowsAffected = 0;
                                    JOptionPane.showMessageDialog(updateReservationsFrame.this, "This is an Active Reservation and you are Checked in. Please go see the Hotel Clerk or Call them!");

                                }

                                // Execute the update


                                if (rowsAffected > 0) {
                                    // Remove the reservation from the list
                                    if(futureSelected){
                                        futureReservations.remove(index);
                                    }
                                    else{
                                        activeReservations.remove(index);
                                    }

                                    displayReservations(); // Update reservations display
                                    JOptionPane.showMessageDialog(updateReservationsFrame.this, "Reservation canceled successfully!");
                                } else {
                                    JOptionPane.showMessageDialog(updateReservationsFrame.this, "Failed to cancel reservation!");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(updateReservationsFrame.this, "Please select a valid reservation to cancel!");
                    }
                } else {
                    JOptionPane.showMessageDialog(updateReservationsFrame.this, "Please highlight/select a reservation to cancel!");
                }
            }
        });


        reservationsTextArea = new JTextArea(20, 40);
        reservationsTextArea.setEditable(false); // Make it non-editable
        JScrollPane scrollPane = new JScrollPane(reservationsTextArea); // Add scroll functionality


        activeReservationsTextArea = new JTextArea(20, 40);
        activeReservationsTextArea.setEditable(false); // Make it non-editable
        JScrollPane scrollPaneActive = new JScrollPane(activeReservationsTextArea); // Add scroll functionality

        giveReservaitons();
        displayReservations();


        // Creating panel for better layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // formPanel.add(reservationLabel);
        //formPanel.add(reservationTextField);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Adjust spacing
        buttonPanel.add(addReservationButton);
        buttonPanel.add(editReservationButton);
        buttonPanel.add(cancelReservationButton);



        panel.add(titlePanel, BorderLayout.NORTH);
        //panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.WEST);
        panel.add(scrollPaneActive,BorderLayout.EAST);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding panel to the frame
        setContentPane(panel);
        validate();
        repaint();
    }

    private void finalizeEdit(String s, String finalOriginalResID, double firstHalfCost, BigDecimal rest) {
        try {
            Connection con = CentralDatabase.getConReservationDatabase();
            Statement stmt = con.createStatement();

            ResultSet res =  stmt.executeQuery("Select * from Reservations where ReservationID = '" +s + "'");
            PreparedStatement pstmt;

            if(res.next()){
                pstmt = con.prepareStatement("UPDATE reservations SET ReservationId = '" + finalOriginalResID + "'"+  " where ReservationID = '" +s + "'");
                pstmt.executeUpdate();
                System.out.println(firstHalfCost);
                pstmt = con.prepareStatement("UPDATE reservations SET Cost = " + (double)(res.getDouble("Cost") +rest.doubleValue() + firstHalfCost )+  " where ReservationID = '" +s + "'");
                pstmt.executeUpdate();
            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        exit(null,null);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addReservationButton) {
            String reservation = reservationTextField.getText();
            futureReservations.add(reservation);
            displayReservations(); // Update reservations display
            JOptionPane.showMessageDialog(this, "Reservation added successfully!");
        } else if (e.getSource() == editReservationButton) {
            int index = getSelectedReservationIndex();
            if (index != -1) {
                String newReservation = JOptionPane.showInputDialog(this, "Enter new reservation:");
                futureReservations.set(index, newReservation);
                displayReservations(); // Update reservations display
                JOptionPane.showMessageDialog(this, "Reservation edited successfully!");
            }
        } else if (e.getSource() == cancelReservationButton) {
            int index = getSelectedReservationIndex();
            if (index != -1) {
                futureReservations.remove(index);
                displayReservations(); // Update reservations display
                JOptionPane.showMessageDialog(this, "Reservation canceled successfully!");
            }
        } else if (e.getSource() == addOneMoreButton) {
            reservationTextField.setText(""); // Clear text field for adding a new reservation
        }
    }
    private void giveReservaitons(){
        Connection con = null;

        try {
            con = CentralDatabase.getConReservationDatabase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM Reservations");


            while(res.next()) {
                System.out.println(res.getString("Roomnumber"));

                Date start = DateProcessor.stringToDate(res.getString("StartDate"));
                Date end = DateProcessor.stringToDate(res.getString("endDate"));

                if(DateProcessor.inBetweenToday(start,end)){
                	System.out.println("Adding to current");
                    activeReservations.add("RoomNumber: " + res.getString("RoomNumber") + " || Start Date: " + res.getString("StartDate") + "    End Date: " + res.getString("EndDate"));
                }
                else if (DateProcessor.inFuture(start)) {
                	System.out.println("Adding to future");
                    futureReservations.add("RoomNumber: " + res.getString("RoomNumber") + " || Start Date: " + res.getString("StartDate") + "    End Date: " + res.getString("EndDate"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getSelectedReservationIndex() {
        String selectedReservation = JOptionPane.showInputDialog(this, "Enter the reservation to edit/cancel:");
        return futureReservations.indexOf(selectedReservation);
    }

    private void displayReservations() {
        reservationsTextArea.setText(""); // Clear the text area
        activeReservationsTextArea.setText(""); // Clear the text area
        for (String reservation : futureReservations) {
            //if(){
            reservationsTextArea.append(reservation + "\n");
            //}
            // Append each reservation to the text area
        }
        for (String reservation : activeReservations) {
            //if(){
            activeReservationsTextArea.append(reservation + "\n");
            //}
            // Append each reservation to the text area
        }
    }

    private void exit(String SQL, Statement stmt){

        //getContentPane().removeAll();
        if(SQL != null){
            CentralReservations.exectueSQL(SQL,stmt);
        }
        defaultPanel();

    }
}