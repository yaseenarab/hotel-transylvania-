package UI.Frames;

import AccountService.Guest;
import Central.CentralDatabase;
import Central.CentralReservations;
import Central.CentralRoom;
import UI.Panels.ReserveRoomPanel;
import Utilities.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class EditReservationFrame extends JFrame implements ActionListener {
    private JLabel futureLabel, activeLabel;
    private JTextField reservationTextField;
    private JButton addReservationButton, editReservationButton, cancelReservationButton, addOneMoreButton;
    //private JTextArea reservationsTextArea, activeReservationsTextArea; // Added JTextArea for displaying reservations
    private Guest guest;
    private ReserveRoomPanel reserveRoomPanel2;
    private JTable activeReservationsTable, futureReservationsTable;
    private DefaultTableModel activeModel,futureModel;

    private ArrayList<String> futureReservations, activeReservations; // Store reservations for the user

    public EditReservationFrame(Guest guest) {


        this.guest = guest;


        // JTextArea for displaying reservations
       defaultPanel();


    }

    private void defaultPanel(){
        setSize(900, 500);
        // Initialize reservations ArrayList

        activeModel = new DefaultTableModel();
        futureModel = new DefaultTableModel();
        activeModel.addColumn("ReservationId");
        activeModel.addColumn("RoomID");
        activeModel.addColumn("Start Date");
        activeModel.addColumn("End Date");

        futureModel.addColumn("ReservationId");
        futureModel.addColumn("RoomID");
        futureModel.addColumn("Start Date");
        futureModel.addColumn("End Date");


        activeReservationsTable = new JTable(activeModel);
        futureReservationsTable = new JTable(futureModel);
        activeReservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        futureReservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        activeReservationsTable.getSelectionModel().addListSelectionListener(this::deselectFuture);
        futureReservationsTable.getSelectionModel().addListSelectionListener(this::deselectActive);

        activeReservationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        futureReservationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        futureReservationsTable.setPreferredSize(new Dimension(450,250));
        activeReservationsTable.setPreferredSize(new Dimension(432,250));


        TableColumnModel tableColumnActive = activeReservationsTable.getColumnModel();
        tableColumnActive.getColumn(1).setPreferredWidth(30);
        tableColumnActive.getColumn(2).setPreferredWidth(40);
        tableColumnActive.getColumn(3).setPreferredWidth(40);

        TableColumnModel tableColumnFuture = futureReservationsTable.getColumnModel();
        tableColumnFuture.getColumn(1).setPreferredWidth(30);
        tableColumnFuture.getColumn(2).setPreferredWidth(40);
        tableColumnFuture.getColumn(3).setPreferredWidth(40);
        giveReservaitons();


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
                        reserveRoomPanel = new ReserveRoomPanel(guest);
                        // Set the content pane of the frame to the ReserveRoomPanel
                        JButton exitButton =reserveRoomPanel.getExitButton();
                        exitButton.addActionListener(ex -> exit(null, null));
                        setContentPane(reserveRoomPanel);
                        validate();
                        repaint();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }}}});



        editReservationButton = new JButton("Edit Reservation");
        editReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTable theTable = futureReservationsTable;
                int selectedReservation = futureReservationsTable.getSelectedRow(); // Get the highlighted text

                boolean futureSelected = (selectedReservation >-1);
                if(!futureSelected){

                    selectedReservation = activeReservationsTable.getSelectedRow();
                    theTable = activeReservationsTable;
                }


                if (selectedReservation > -1) {
                    int index = selectedReservation;//futureSelected ? futureReservationsTable.indexOf(selectedReservation) : activeReservations.indexOf(selectedReservation);
                    if (index > -1) {
                        int response = JOptionPane.showConfirmDialog(EditReservationFrame.this, "Are you sure you want to edit this reservation?", "Edit Reservation", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            try {
                                // Parse the selected reservation string to extract room number and start date
                                //String[] parts = selectedReservation.split("\\|\\|");
                                Integer roomNumber = (Integer) theTable.getValueAt(selectedReservation,1);//parts[0].substring(parts[0].indexOf(":") + 1).trim();
                                String startDate = (String)theTable.getValueAt(selectedReservation,2);//parts[1].substring(parts[1].indexOf(":") + 1, parts[1].indexOf("End Date:")).trim();
                                String reservationID = (String)theTable.getValueAt(selectedReservation,0);

                                // Delete the current reservation
                                Connection con = DriverManager.getConnection("jdbc:derby:ReservationsData;");
                                Statement stmt = con.createStatement();
                                String saveData = "Select * from Reservations where ReservationId = '"+reservationID + "'";

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
                                boolean checkedIn;
                                Integer nightsStayed = 0;
                                double cost = 0;


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
                                    checkedIn = res.getBoolean("checkedin");
                                    nightsStayed = res.getInt("nightsstayed");

                                    saveData = "INSERT INTO Reservations(Roomnumber, username,startDate,EndDate,ReservationId,cost,checkedin, nightsstayed) values(" + roomID +",'" + res.getString("Username") +"','"+ res.getString("StartDate") +"','"+ res.getString("EndDate") +"','"+ res.getString("ReservationId") +"',"+ res.getDouble("Cost") +"," +checkedIn + "," +nightsStayed +")";
                                } else {
                                    checkedIn = false;
                                }

                                System.out.println("START TO NOW: " + startToNow.intValue());
                                System.out.println("NIGHTS STAYED: " + nightsStayed);

                                if(startToNow.intValue() < 0){
                                    startToNow = new BigDecimal(0);
;                                }

                                CentralReservations.setNightsStayed(startToNow.intValue()+nightsStayed);


                                BigDecimal totalCost = new BigDecimal(cost);

                                BigDecimal stayCost = startToEnd.multiply(perNight);

                                if(((startToNow.intValue() == 0 || startToNow.intValue() == 1) && nightsStayed == 0)){
                                    stayCost = stayCost.multiply(new BigDecimal("1.25"));
                                }

                                //startToNow = startToNow.add(new BigDecimal(nightsStayed));
                                BigDecimal souvenierCost = totalCost.subtract(stayCost);

                                System.out.println("STRT TO NOW" +startToNow.intValue());


                                BigDecimal firstHalf = startToNow.multiply(perNight);
                                double firstHalfCost = firstHalf.doubleValue();
                                System.out.println("FIRST HALF COST: "+ firstHalfCost);

                                String deleteQuery = "DELETE FROM Reservations WHERE ReservationId = '" + originalResID+"'";
                                PreparedStatement pstmt = con.prepareStatement(deleteQuery);
                                //pstmt.executeUpdate();
                                //pstmt.setInt(1, roomNumber);
                                //pstmt.setString(2, startDate);
                                int rowsAffected = pstmt.executeUpdate();

                                if (rowsAffected > 0) {
                                    if(futureSelected){
                                        ((DefaultTableModel)futureReservationsTable.getModel()).removeRow(index);
                                    }
                                    else{

                                        ((DefaultTableModel)activeReservationsTable.getModel()).removeRow(index);
                                    }

                                    displayReservations();



                                    // Take the user to the reservation page
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
                                        System.out.println("FIRST HALF COST: "+ firstHalfCost);
                                        reserveButton.addActionListener(ex ->finalizeEdit(resID[0], finalOriginalResID, firstHalfCost, finalSouvenierCost, checkedIn));
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



                                    con = DriverManager.getConnection("jdbc:derby:HotelRoomsData;");
                                    stmt = con.createStatement();
                                    res = stmt.executeQuery("Select * from Rooms where Roomnumber = "+ roomID);
                                    res.next();
                                    roomType = res.getString("roomtype");
                                    bedType = res.getString("bedType");
                                    qualityLevel = res.getString("QualityLevel");
                                    smoking = res.getBoolean("smokingAllowed");

                                    reserveRoomPanel2.fillBoxes(roomID,roomType,bedType,qualityLevel,smoking,(futureSelected ? startDate : DateProcessor.dateToString(currentDate)),endDate);

                                    JOptionPane.showMessageDialog(EditReservationFrame.this, "Please make a new reservation.");
                                } else {
                                    JOptionPane.showMessageDialog(EditReservationFrame.this, "Failed to edit reservation!");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(EditReservationFrame.this, "Please select a valid reservation to edit!");
                    }
                } else {
                    JOptionPane.showMessageDialog(EditReservationFrame.this, "Please highlight/select a reservation to edit!");
                }
            }
        });

        cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTable theTable = futureReservationsTable;
                int selectedReservation = futureReservationsTable.getSelectedRow(); // Get the highlighted text

                boolean futureSelected = (selectedReservation >-1);
                if(!futureSelected){

                    selectedReservation = activeReservationsTable.getSelectedRow();
                    theTable = activeReservationsTable;
                }

                System.out.println("SELECTECT: " + selectedReservation);
                if (selectedReservation > -1) {
                    int index = selectedReservation;//futureSelected ? futureReservationsTable.indexOf(selectedReservation) : activeReservations.indexOf(selectedReservation);
                    if (index > -1) {
                        int response = JOptionPane.showConfirmDialog(EditReservationFrame.this, "Are you sure you want to edit this reservation?", "Edit Reservation", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            try {
                                // Parse the selected reservation string to extract room number and start date
                                //String[] parts = selectedReservation.split("\\|\\|");
                                Integer roomNumber = (Integer) theTable.getValueAt(selectedReservation,1);//parts[0].substring(parts[0].indexOf(":") + 1).trim();
                                String startDate = (String)theTable.getValueAt(selectedReservation,2);//parts[1].substring(parts[1].indexOf(":") + 1, parts[1].indexOf("End Date:")).trim();

                                Connection con = null;
                                con = DriverManager.getConnection("jdbc:derby:ReservationsData;");
                                Statement stmt = con.createStatement();
                                // Prepare the delete statement
                                ResultSet res = stmt.executeQuery("Select * FROM Reservations where StartDate = '" + startDate + "' AND Roomnumber = " + roomNumber);
                                boolean checkedIn = false;
                                if(res.next()){
                                    checkedIn = res.getBoolean("checkedin");

                                }
                                int rowsAffected;
                                if(!checkedIn){
                                    String deleteQuery = "DELETE FROM Reservations WHERE RoomNumber = ? AND StartDate = ?";
                                    PreparedStatement pstmt = con.prepareStatement(deleteQuery);

                                    // Set parameters
                                    pstmt.setInt(1, roomNumber); // Assuming roomNumber is an integer
                                    pstmt.setString(2, startDate); // Assuming startDate is a string in 'YYYY-MM-DD' format
                                    rowsAffected= pstmt.executeUpdate();
                                }
                                else{
                                    rowsAffected = 0;
                                    JOptionPane.showMessageDialog(EditReservationFrame.this, "This is an Active Reservation and you are Checked in. Please go see the Hotel Clerk or Call them!");

                                }

                                // Execute the update


                                if (rowsAffected > 0) {
                                    // Remove the reservation from the list
                                    if(futureSelected){

                                        ((DefaultTableModel)futureReservationsTable.getModel()).removeRow(index);
                                    }
                                    else{

                                        ((DefaultTableModel)activeReservationsTable.getModel()).removeRow(index);
                                    }

                                    displayReservations(); // Update reservations display
                                    //defaultPanel();
                                    JOptionPane.showMessageDialog(EditReservationFrame.this, "Reservation canceled successfully!");
                                } else {
                                    JOptionPane.showMessageDialog(EditReservationFrame.this, "Failed to cancel reservation!");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(EditReservationFrame.this, "Please select a valid reservation to cancel!");
                    }
                } else {
                    JOptionPane.showMessageDialog(EditReservationFrame.this, "Please highlight/select a reservation to cancel!");
                }
            }
        });


        //reservationsTextArea = new JTextArea(20, 40);
        //reservationsTextArea.setEditable(false); // Make it non-editable
        JScrollPane scrollPane = new JScrollPane(futureReservationsTable); // Add scroll functionality


        //activeReservationsTextArea = new JTextArea(20, 40);
        //activeReservationsTextArea.setEditable(false); // Make it non-editable
        JScrollPane scrollPaneActive = new JScrollPane(activeReservationsTable); // Add scroll functionality


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
        panel.add(scrollPaneActive);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding panel to the frame
        setContentPane(panel);
        validate();
        repaint();
    }


    private void deselectFuture(ListSelectionEvent listSelectionEvent) {

        futureReservationsTable.getSelectionModel().clearSelection();


    }
    private void deselectActive(ListSelectionEvent listSelectionEvent) {
        activeReservationsTable.getSelectionModel().clearSelection();



    }

    private void finalizeEdit(String s, String finalOriginalResID, double firstHalfCost, BigDecimal rest, boolean checkedIn) {
        System.out.println("FIRST HALF COST: "+ firstHalfCost);
        try {

            Connection con = CentralDatabase.getConReservationDatabase();
            Statement stmt = con.createStatement();

            ResultSet res =  stmt.executeQuery("Select * from Reservations where ReservationID = '" +s + "'");
            PreparedStatement pstmt;

            if(res.next()){
                pstmt = con.prepareStatement("UPDATE reservations SET ReservationId = '" + finalOriginalResID + "'"+  " where ReservationID = '" +s + "'");
                pstmt.executeUpdate();
                System.out.println("FIRST HALF COST: "+ firstHalfCost);
                pstmt = con.prepareStatement("UPDATE reservations SET Cost = " + (double)(res.getDouble("Cost") +rest.doubleValue() + firstHalfCost )+  " where ReservationID = '" +finalOriginalResID + "'");
                pstmt.executeUpdate();
                System.out.println("FINALIZED");
                pstmt = con.prepareStatement("UPDATE reservations SET checkedIn = " + checkedIn + " where ReservationID = '" +finalOriginalResID + "'");
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
            ResultSet res = stmt.executeQuery("SELECT * FROM Reservations where Username = '" + guest.getUsername()+"'");



            while(res.next()){
                System.out.println(res.getString("Roomnumber"));

                Date start = DateProcessor.stringToDate(res.getString("StartDate"));
                Date end = DateProcessor.stringToDate(res.getString("endDate"));

                Object [] obj = new Object[4];



                obj[0] = res.getString("ReservationID");
                obj[1] = res.getInt("RoomNumber");
                obj[2] = res.getString("StartDate");
                obj[3] = res.getString("endDate");

                System.out.println("adding");
                if(DateProcessor.inBetweenToday(start,end)){

                    activeModel.addRow(obj);
                    //activeReservations.add("RoomNumber: " + res.getString("RoomNumber") + " || Start Date: " + res.getString("StartDate") + "    End Date: " + res.getString("EndDate"));
                }
                else{
                    futureModel.addRow(obj);
                    //futureReservations.add("RoomNumber: " + res.getString("RoomNumber") + " || Start Date: " + res.getString("StartDate") + "    End Date: " + res.getString("EndDate"));
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
        //reservationsTextArea.setText(""); // Clear the text area
        //activeReservationsTextArea.setText(""); // Clear the text area
        for (String reservation : futureReservations) {
            //if(){
          //  reservationsTextArea.append(reservation + "\n");
            //}
            // Append each reservation to the text area
        }
        for (String reservation : activeReservations) {
            //if(){
           // activeReservationsTextArea.append(reservation + "\n");
            //}
            // Append each reservation to the text area
        }
    }

    private void exit(String SQL, Statement stmt){

        //getContentPane().removeAll();

        if(SQL != null){
            System.out.println("CALLLLLLEEDDDDD");

            CentralReservations.exectueSQL(SQL,stmt);
        }
        for(WindowListener al : this.getWindowListeners()){
            this.removeWindowListener(al);
        }
        defaultPanel();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //ReservationFrame reservationFrame = new ReservationFrame();
                //reservationFrame.setVisible(true);
            }
        });
    }
}