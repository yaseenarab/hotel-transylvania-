package Hotel.UI.Panels;


import Hotel.AccountService.Guest;
import Hotel.Central.CentralCashiering;
import Hotel.Central.CentralDatabase;
import Hotel.Central.CentralReservations;
import Hotel.UI.DateLabelFormatter;
import Hotel.UI.Frames.Guest.GuestHomeFrame;
import Hotel.Utilities.DateProcessor;
import Hotel.Room.Reservation;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.*;

public class ReserveRoomPanel extends JPanel {

    //private Reservations reservations;
    private JTextField guestNameField, memberNumberField, cardNumberField, expirationField;
    private JComboBox<String> hotelTypeComboBox, roomTypeComboBox, qualityLevelComboBox, bedTypeComboBox;
    private JFormattedTextField checkInDateField, checkOutDateField;
    private JDatePickerImpl checkInDatePicker,checkOutDatePicker, cardExpiration;
    private JCheckBox smokingCheckBox;
    private JOptionPane jPane;
    private AvailableRoomsPanel availableRoomsPanel;
    private UtilDateModel checkOutDateModel,checkInDateModel,expirationMod;
    private JTextArea reservationSummary = new JTextArea();
    private JTable roomAvailability;
    private DefaultTableModel roomAvailModel;
    private JButton reserveButton, exitButton;
    //private RoomQuality roomNeeds;
    private Guest guest;
    private Date reservationStart,reservationEnd;

    public ReserveRoomPanel( Guest guest ) throws Exception {
        //reservations = new Reservations();
        this.guest = guest;

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        JLabel guestLabel = new JLabel("Guest Name:");
        guestLabel.setPreferredSize(new Dimension(300,50));
        formPanel.add(guestLabel);
        guestNameField = new JTextField(guest.getFirstName() + " " + guest.getLastName());
        formPanel.add(guestNameField);

        formPanel.add(new JLabel("Credit Card Number:"));
        cardNumberField = new JTextField();
        formPanel.add(cardNumberField);

        Properties c = new Properties();
        c.put("text.today", "Today");
        c.put("text.month", "Month");
        c.put("text.year", "Year");
        formPanel.add(new JLabel("Card expiration:"));
        expirationMod = new UtilDateModel();
        JDatePanelImpl expirationPanel = new JDatePanelImpl(expirationMod,c);
        cardExpiration = new JDatePickerImpl(expirationPanel, new DateLabelFormatter());
        cardExpiration.addActionListener(e -> updateTable());
        formPanel.add(cardExpiration);

        formPanel.add(new JLabel("Hotel Type:"));
        hotelTypeComboBox = new JComboBox<>(new String[]{"Any","Nature Retreat", "Urban Elegance", "Vintage Charm"});
        hotelTypeComboBox.addActionListener(e -> updateTable());
        formPanel.add(hotelTypeComboBox);

        formPanel.add(new JLabel("Room Type:"));
        roomTypeComboBox = new JComboBox<>();
        updateRoomTypes();
        hotelTypeComboBox.addActionListener(e -> updateRoomTypes());
        roomTypeComboBox.addActionListener(e -> updateTable());
        formPanel.add(roomTypeComboBox);

        formPanel.add(new JLabel("Quality Level:"));
        qualityLevelComboBox = new JComboBox<>(new String[]{"Any","Executive", "Business", "Comfort", "Economy"});
        qualityLevelComboBox.addActionListener(e -> updateTable());
        formPanel.add(qualityLevelComboBox);

        formPanel.add(new JLabel("Bed Type:"));
        bedTypeComboBox = new JComboBox<>(new String[] {"Any","Twin", "Full", "Queen", "King"});
        bedTypeComboBox.addActionListener(e -> updateTable());
        formPanel.add(bedTypeComboBox);

        formPanel.add(new JLabel("Smoking Allowed:"));
        smokingCheckBox = new JCheckBox();
        smokingCheckBox.addActionListener(e -> updateTable());
        formPanel.add(smokingCheckBox);


        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        formPanel.add(new JLabel("Check-in Date:"));
        checkInDateModel = new UtilDateModel();

        JDatePanelImpl checkIndatePanel = new JDatePanelImpl(checkInDateModel,p);

        checkInDatePicker = new JDatePickerImpl(checkIndatePanel, new DateLabelFormatter());
        checkInDatePicker.addActionListener(e -> updateTable());

        formPanel.add(checkInDatePicker);

        formPanel.add(new JLabel("Check-out Date:"));

        checkOutDateModel = new UtilDateModel();

        JDatePanelImpl checkOutdatePanel = new JDatePanelImpl(checkOutDateModel,p);
        checkOutDatePicker = new JDatePickerImpl(checkOutdatePanel, new DateLabelFormatter());
        checkOutDatePicker.addActionListener(e -> updateTable());
        formPanel.add(checkOutDatePicker);


        availableRoomsPanel = new AvailableRoomsPanel(guest);
        add(availableRoomsPanel,BorderLayout.EAST);
        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        reserveButton = new JButton("Make Reservation");
        bottomPanel.add(reserveButton);
        reserveButton.addActionListener(e -> performReservation());

        exitButton = new JButton("Exit");
        bottomPanel.add(exitButton);
        add(bottomPanel, BorderLayout.SOUTH);
        exitButton.addActionListener(e -> {
            formPanel.disable(); // This will close the frame
        });
    }
    public ReserveRoomPanel(GuestHomeFrame frame, Guest guest) throws Exception {
        //reservations = new Reservations();
        this.guest = guest;

        setLayout(new BorderLayout());
        frame.setSize(new Dimension(900,500));

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        JLabel guestLabel = new JLabel("Guest Name:");
        guestLabel.setPreferredSize(new Dimension(300,50));
        formPanel.add(guestLabel);
        guestNameField = new JTextField(guest.getFirstName() + " " + guest.getLastName());
        formPanel.add(guestNameField);


        formPanel.add(new JLabel("Credit Card Number:"));
        String cardNum = "";
        ResultSet res = CentralDatabase.getCard(guest.getUsername());

        if(res.next()) {
            cardNum = res.getString("CARDNUMBER");
        }
        cardNumberField = new JTextField(cardNum);
        formPanel.add(cardNumberField);

        Properties c = new Properties();
        c.put("text.today", "Today");
        c.put("text.month", "Month");
        c.put("text.year", "Year");
        formPanel.add(new JLabel("Card expiration:"));
        UtilDateModel expirationMod = new UtilDateModel();
        JDatePanelImpl expirationPanel = new JDatePanelImpl(expirationMod,c);
        cardExpiration = new JDatePickerImpl(expirationPanel, new DateLabelFormatter());
        cardExpiration.addActionListener(e -> updateTable());
        formPanel.add(cardExpiration);

        formPanel.add(new JLabel("Hotel Type:"));
        hotelTypeComboBox = new JComboBox<>(new String[]{"Any","Nature Retreat", "Urban Elegance", "Vintage Charm"});
        hotelTypeComboBox.addActionListener(e -> updateTable());
        formPanel.add(hotelTypeComboBox);

        formPanel.add(new JLabel("Room Type:"));
        roomTypeComboBox = new JComboBox<>();
        updateRoomTypes();
        hotelTypeComboBox.addActionListener(e -> updateRoomTypes());
        roomTypeComboBox.addActionListener(e -> updateTable());
        formPanel.add(roomTypeComboBox);

        formPanel.add(new JLabel("Quality Level:"));
        qualityLevelComboBox = new JComboBox<>(new String[]{"Any","Executive", "Business", "Comfort", "Economy"});
        qualityLevelComboBox.addActionListener(e -> updateTable());
        formPanel.add(qualityLevelComboBox);

        formPanel.add(new JLabel("Bed Type:"));
        bedTypeComboBox = new JComboBox<>(new String[] {"Any","Twin", "Full", "Queen", "King"});
        bedTypeComboBox.addActionListener(e -> updateTable());
        formPanel.add(bedTypeComboBox);

        formPanel.add(new JLabel("Smoking Allowed:"));
        smokingCheckBox = new JCheckBox();
        smokingCheckBox.addActionListener(e -> updateTable());
        formPanel.add(smokingCheckBox);


        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        formPanel.add(new JLabel("Check-in Date:"));
        UtilDateModel checkIndateModel = new UtilDateModel();
        JDatePanelImpl checkIndatePanel = new JDatePanelImpl(checkIndateModel,p);

        checkInDatePicker = new JDatePickerImpl(checkIndatePanel, new DateLabelFormatter());

        formPanel.add(checkInDatePicker);

        formPanel.add(new JLabel("Check-out Date:"));

        UtilDateModel checkOutdateModel = new UtilDateModel();
        JDatePanelImpl checkOutdatePanel = new JDatePanelImpl(checkOutdateModel,p);
        checkOutDatePicker = new JDatePickerImpl(checkOutdatePanel, new DateLabelFormatter());
        checkOutDatePicker.addActionListener(e -> updateTable());
        formPanel.add(checkOutDatePicker);


        availableRoomsPanel = new AvailableRoomsPanel(guest);
        add(availableRoomsPanel,BorderLayout.EAST);
        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        reserveButton = new JButton("Make Reservation");
        bottomPanel.add(reserveButton);
        reserveButton.addActionListener(e -> performReservation());

        exitButton = new JButton("Exit");
        bottomPanel.add(exitButton);
        add(bottomPanel, BorderLayout.SOUTH);
        exitButton.addActionListener(e -> {
            frame.cl.show(frame.container, "Home");
        });
    }

    private void updateTable() {
        reservationStart = (Date)checkInDatePicker.getModel().getValue();
        reservationEnd = (Date)checkOutDatePicker.getModel().getValue();

        boolean noConflict = DateProcessor.dateTimeConflict(reservationStart,reservationEnd);

        if(reservationStart != null && reservationEnd != null && noConflict){
            String hotelType = (String)hotelTypeComboBox.getSelectedItem();
            String roomType = (String)roomTypeComboBox.getSelectedItem();
            String qualityLevel = (String)qualityLevelComboBox.getSelectedItem();
            String bedType = (String)bedTypeComboBox.getSelectedItem();
            boolean smokingStatus = smokingCheckBox.isSelected();
            availableRoomsPanel.updateTable(CentralReservations.displayAvailableRooms(hotelType,roomType,qualityLevel,bedType,smokingStatus,reservationStart,reservationEnd), reservationStart,reservationEnd);
        }
        else{
            availableRoomsPanel.updateTable("", null,null);
        }

    }

    private void updateRoomTypes() {
        String selectedHotel = (String) hotelTypeComboBox.getSelectedItem();
        roomTypeComboBox.removeAllItems();
        if ("Nature Retreat".equals(selectedHotel)) {
            roomTypeComboBox.addItem("Any");
            roomTypeComboBox.addItem("Single");
            roomTypeComboBox.addItem("Double");
            roomTypeComboBox.addItem("Family");
        } else if ("Urban Elegance".equals(selectedHotel)) {
            roomTypeComboBox.addItem("Any");
            roomTypeComboBox.addItem("Suite");
            roomTypeComboBox.addItem("Deluxe");
        } else if ("Vintage Charm".equals(selectedHotel)) {
            roomTypeComboBox.addItem("Any");
            roomTypeComboBox.addItem("Standard");
            roomTypeComboBox.addItem("Deluxe");
        }
        else if ("Any".equals(selectedHotel)) {
            roomTypeComboBox.addItem("Any");
        }
    }

    private void quoteReservation() {
        double rate = calculateRate(checkGuestStatus(), (String) roomTypeComboBox.getSelectedItem());
        JOptionPane.showMessageDialog(this, "$" + rate + " per night." );
    }


    public String performReservation() {
        Reservation reserve =null;

        if(cardNumberField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a card. Please try again" );
            throw new NullPointerException();
        }
        if(cardExpiration.getModel().getValue() == null){
            JOptionPane.showMessageDialog(this, "Please enter a card. Please try again" );
            throw new NullPointerException();
        }
        try {
            ResultSet guestCard = CentralDatabase.getCard(guest.getUsername());
            String tableCardNum = null;
            if(guestCard.next()) {
                tableCardNum = guestCard.getString("CARDNUMBER");
            }
            else{
                CentralDatabase.setCard(guest.getUsername(),guest.getPassword(),cardNumberField.getText(),DateProcessor.dateToString( (Date) cardExpiration.getModel().getValue()));
                tableCardNum = cardNumberField.getText();
            }

            if(!availableRoomsPanel.roomIsSelected()) {
                throw new NullPointerException();
            }
            boolean validCard = true;
            if(tableCardNum.equals(cardNumberField.getText())) {
                if(!CentralDatabase.cardNumMatchExp(guest.getUsername(), cardNumberField.getText(),
                        DateProcessor.dateToString((Date)cardExpiration.getModel().getValue()))) {
                    validCard = false;
                    JOptionPane.showMessageDialog(this, "Invalid card. Please try again" );
                }
            }
            else if (!CentralCashiering.saveCard(guest, cardNumberField.getText(), (Date)cardExpiration.getModel().getValue())) {
                validCard = false;
                JOptionPane.showMessageDialog(this, "Invalid card. Please try again" );

            }
            if(validCard) {
                Integer roomID = availableRoomsPanel.getRoomID();
                reserve = CentralReservations.makeReservation(roomID, guest.getUsername(),guest.getGuestID(),reservationStart,reservationEnd);
                guest.addReservation(reserve);

                JOptionPane.showMessageDialog(this, "Reservation Successfully made!");
                updateTable();

                CentralReservations.resetEditStatus();
            }

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Please select a row from the table.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return reserve.getReservationID();
    }
    private boolean checkGuestStatus() {
        return memberNumberField.getText().length() > 0;
    }
    private double calculateRate(boolean isHigherStatus, String roomType) {
        double baseRate = 100;
        if (isHigherStatus) {
            return baseRate * 0.8;
        }
        return baseRate;
    }
    public void fillBoxes(Integer roomID, String roomType, String bedType, String qualityLevel, boolean smoking, String startDate, String endDate) {

        if (roomID >= 100 && roomID < 200) {hotelTypeComboBox.setSelectedIndex(1);}
        else if (roomID >= 200 && roomID < 300) {hotelTypeComboBox.setSelectedIndex(2);}
        else if (roomID >= 300 && roomID < 400) {hotelTypeComboBox.setSelectedIndex(3);}

        if (roomType.equals("Single")) {roomTypeComboBox.setSelectedIndex(1);}
        else if (roomType.equals("Double")) {roomTypeComboBox.setSelectedIndex(2);}
        else if (roomType.equals("Family")) {roomTypeComboBox.setSelectedIndex(3);}
        else if (roomType.equals("Deluxe")) {roomTypeComboBox.setSelectedIndex(2);}
        else if (roomType.equals("Standard")) {roomTypeComboBox.setSelectedIndex(1);}

        if (bedType.equals("Twin")) {bedTypeComboBox.setSelectedIndex(1);}
        else if (bedType.equals("Full")) {bedTypeComboBox.setSelectedIndex(2);}
        else if (bedType.equals("Queen")) {bedTypeComboBox.setSelectedIndex(3);}
        else if (bedType.equals("King")) {bedTypeComboBox.setSelectedIndex(4);}


        if (qualityLevel.equals("Business Level")) {qualityLevelComboBox.setSelectedIndex(2);}
        else if (qualityLevel.equals("Economy Level")) {qualityLevelComboBox.setSelectedIndex(4);}
        else if (qualityLevel.equals("Executive Level")) {qualityLevelComboBox.setSelectedIndex(1);}
        else if (qualityLevel.equals("Comfort Level")) {qualityLevelComboBox.setSelectedIndex(3);}

        smokingCheckBox.setSelected(smoking);

        Date start = DateProcessor.stringToDate(startDate);
        Date end = DateProcessor.stringToDate(endDate);

        checkInDateModel.setDate(DateProcessor.getYear(startDate),DateProcessor.getMonth(startDate),DateProcessor.getDay(startDate));
        checkInDateModel.setSelected(true);

        checkOutDateModel.setDate(DateProcessor.getYear(endDate),DateProcessor.getMonth(endDate),DateProcessor.getDay(endDate));
        checkOutDateModel.setSelected(true);
        updateTable();

    }



    public JButton getExitButton() {
        return exitButton;
    }
    public JButton getReserveButton(){
        return reserveButton;
    }
}