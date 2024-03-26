package org.example;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;

import java.util.List;
import java.util.Random;


public class ReserveRoomPanel extends JPanel {
	private Reservations reservations;
    private JTextField guestNameField, memberNumberField, cardNumberField;
    private JComboBox<String> hotelTypeComboBox, roomTypeComboBox, qualityLevelComboBox, bedTypeComboBox;
    private JFormattedTextField checkInDateField, checkOutDateField;
    private JCheckBox smokingCheckBox;
    private JTextArea reservationSummary;
    private JButton reserveButton;
    
    private Room roomNeeds;

    public ReserveRoomPanel() {
    	reservations = new Reservations();
    	
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("Guest Name:"));
        guestNameField = new JTextField();
        formPanel.add(guestNameField);

        formPanel.add(new JLabel("Member Number (optional):"));
        memberNumberField = new JTextField();
        formPanel.add(memberNumberField);

        formPanel.add(new JLabel("Credit Card Number:"));
        cardNumberField = new JTextField();
        formPanel.add(cardNumberField);

        formPanel.add(new JLabel("Hotel Type:"));
        hotelTypeComboBox = new JComboBox<>(new String[]{"Nature Retreat", "Urban Elegance", "Vintage Charm"});
        formPanel.add(hotelTypeComboBox);

        formPanel.add(new JLabel("Room Type:"));
        roomTypeComboBox = new JComboBox<>();
        hotelTypeComboBox.addActionListener(e -> updateRoomTypes());
        formPanel.add(roomTypeComboBox);
        
        formPanel.add(new JLabel("Quality Level:"));
        qualityLevelComboBox = new JComboBox<>(new String[]{"Executive", "Business", "Comfort", "Economy"});
        formPanel.add(qualityLevelComboBox);

        formPanel.add(new JLabel("Bed Type:"));
        bedTypeComboBox = new JComboBox<>(new String[] {"Twin", "Full", "Queen", "King"});
        formPanel.add(bedTypeComboBox);

        formPanel.add(new JLabel("Smoking Allowed:"));
        smokingCheckBox = new JCheckBox();
        formPanel.add(smokingCheckBox);

        formPanel.add(new JLabel("Check-in Date:"));
        checkInDateField = new JFormattedTextField(createFormatter("##/##/####"));
        checkInDateField.setColumns(10);
        checkInDateField.setValue(new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date()));
        formPanel.add(checkInDateField);

        formPanel.add(new JLabel("Check-out Date:"));
        checkOutDateField = new JFormattedTextField(createFormatter("##/##/####"));
        checkOutDateField.setColumns(10);
        checkOutDateField.setValue(new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date()));
        formPanel.add(checkOutDateField);

        add(formPanel, BorderLayout.CENTER);

        reservationSummary = new JTextArea(5, 20);
        reservationSummary.setEditable(false);
        add(new JScrollPane(reservationSummary), BorderLayout.EAST);

        reserveButton = new JButton("Reserve");
        add(reserveButton, BorderLayout.SOUTH);
        reserveButton.addActionListener(e -> performReservation());
    }

    private void updateRoomTypes() {
        String selectedHotel = (String) hotelTypeComboBox.getSelectedItem();
        roomTypeComboBox.removeAllItems();
        if ("Nature Retreat".equals(selectedHotel)) {
            roomTypeComboBox.addItem("Single");
            roomTypeComboBox.addItem("Double");
            roomTypeComboBox.addItem("Family");
        } else if ("Urban Elegance".equals(selectedHotel)) {
            roomTypeComboBox.addItem("Suite");
            roomTypeComboBox.addItem("Deluxe");
        } else if ("Vintage Charm".equals(selectedHotel)) {
            roomTypeComboBox.addItem("Standard");
            roomTypeComboBox.addItem("Deluxe");
        }
    }

    private void performReservation() {
        boolean isHigherStatus = checkGuestStatus();
        double rate = calculateRate(isHigherStatus, (String) roomTypeComboBox.getSelectedItem());
        try {
			roomNeeds = new Room(RoomStatus.VaCl, BedType.getEnum(bedTypeComboBox.getSelectedItem().toString()), RoomType.getEnum(roomTypeComboBox.getSelectedItem().toString()), 
					QualityLevel.getEnum(qualityLevelComboBox.getSelectedItem().toString()), smokingCheckBox.isSelected(), 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
        int roomNumber = assignRoomNumber((String) hotelTypeComboBox.getSelectedItem());

        reservationSummary.setText(
                "Reservation Summary:\n" +
                        "Guest Name: " + guestNameField.getText() + "\n" +
                        "Member Number: " + memberNumberField.getText() + "\n" +
                        "Hotel Type: " + hotelTypeComboBox.getSelectedItem() + "\n" +
                        "Room Type: " + roomTypeComboBox.getSelectedItem() + "\n" +
                        "Quality Level: " + qualityLevelComboBox.getSelectedItem() + "\n" +
                        "Bed Type: " + bedTypeComboBox.getSelectedItem() + "\n" +
                        "Smoking: " + (smokingCheckBox.isSelected() ? "Yes" : "No") + "\n" +
                        "Check-in Date: " + checkInDateField.getText() + "\n" +
                        "Check-out Date: " + checkOutDateField.getText() + "\n" +
                        "Rate: $" + rate + " per night\n" +
                        "Assigned Room Number: " + roomNumber
        );

        JOptionPane.showMessageDialog(this, "Reservation has been made successfully.");
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

    private int assignRoomNumber(String hotelType) {
        List<Room> roomOptions;
    	if ("Nature Retreat".equals(hotelType)) {
            roomOptions = reservations.getFirstFloorRooms();
        } else if ("Urban Elegance".equals(hotelType)) {
            roomOptions = reservations.getSecondFloorRooms();
        } else if ("Vintage Charm".equals(hotelType)) {
            roomOptions = reservations.getThirdFloorRooms();
        } else {
        	throw new IllegalArgumentException();
        }
    	
    	for (Room rm : roomOptions) {
        	if (rm.match(roomNeeds)) {
        		return rm.getRoomNumber();
        	}
        }
    	
        return 0;
    }

    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }
}
