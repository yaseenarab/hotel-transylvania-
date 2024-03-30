package edu.baylor.hoteltransylvania;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

//import RoomEnums.BedType;
//import RoomEnums.QualityLevel;
//import RoomEnums.RoomStatus;
//import RoomEnums.RoomType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class ReserveRoomPanel extends JPanel {
	private Reservations reservations;
    private JTextField guestNameField, memberNumberField, cardNumberField;
    private JComboBox<String> hotelTypeComboBox, roomTypeComboBox, qualityLevelComboBox, bedTypeComboBox;
    private JFormattedTextField checkInDateField, checkOutDateField;
    private JCheckBox smokingCheckBox;
    private JTextArea reservationSummary;
    private JButton reserveButton;
    private JButton exitButton;
    private Room roomNeeds;
    private GuestProfile guest;

    public ReserveRoomPanel(GuestHomeFrame frame, GuestProfile guest) {
    	reservations = new Reservations();
    	this.guest = guest;
    	
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("Guest Name:"));
        guestNameField = new JTextField(guest.getFirstName() + " " + guest.getLastName());
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
        updateRoomTypes();
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

        // NEW ADDITION
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        reserveButton = new JButton("Make Reservation");
        //add(reserveButton, BorderLayout.SOUTH);
        bottomPanel.add(reserveButton);
        reserveButton.addActionListener(e -> performReservation());

        // NEW ADDITION
        exitButton = new JButton("Exit");
        bottomPanel.add(exitButton);
        add(bottomPanel, BorderLayout.SOUTH);
        exitButton.addActionListener(e -> {
            frame.cl.show(frame.container, "Home");
        });
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
	    try {
	    	boolean isHigherStatus = checkGuestStatus();
	        double rate = calculateRate(isHigherStatus, (String) roomTypeComboBox.getSelectedItem());
	        try {
				roomNeeds = new Room(RoomStatus.VaCl, BedType.getEnum(bedTypeComboBox.getSelectedItem().toString()), RoomType.getEnum(roomTypeComboBox.getSelectedItem().toString()), 
						QualityLevel.getEnum(qualityLevelComboBox.getSelectedItem().toString()), smokingCheckBox.isSelected(), 100);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        Reservation res = assignRoom((String) hotelTypeComboBox.getSelectedItem());
	        
	        if (res != null) {
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
		                        "Assigned Room Number: " + res.getRoomNumber()
		                        
		        );
		        
		        JOptionPane.showMessageDialog(this, "Reservation has been made successfully.");
		        try {
		        	FileWriter fw = new FileWriter("./src/main/resources/reservations.csv", true);
		        	BufferedWriter bw = new BufferedWriter(fw);
		        	bw.write(res.csvOutput());
		        	bw.close();
		        } catch (IOException e) {
		        	System.out.println("Error writing to reservations.csv");
		        	e.printStackTrace();
		        }
	        } else {
	        	JOptionPane.showMessageDialog(this, "No available rooms found. Please try again");
	        }
	    } catch (NullPointerException e) {
	    	JOptionPane.showMessageDialog(this, "Please fill all fields");
	    	e.printStackTrace();
	    }
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

    private Reservation assignRoom(String hotelType) {
        List<Room> roomOptions;
        Reservation match = null;;
        BufferedReader reader = null;
        List<Reservation> curRevs = new ArrayList<Reservation>();
        
        try {
        	reader = new BufferedReader(new FileReader(new File("./src/main/resources/reservations.csv")));
        	
        	String line = null;
        	while ((line = reader.readLine()) != null) {
        		String[] split = line.split(",");
        		try {
        			curRevs.add(new Reservation(Integer.valueOf(split[0]), split[2], split[3], null));
        		} catch (IllegalArgumentException e) {
        			System.out.println(e.getMessage());
        		} catch (ArrayIndexOutOfBoundsException e) {
        			System.out.println(e.getMessage());
        		} catch (Exception e) {
        			System.out.println(e.getStackTrace());
        		}
        	}
        } catch (FileNotFoundException e) {
        	System.out.println(e.getStackTrace());
        } catch (IOException e) {
        	System.out.println(e.getStackTrace());
        }
        
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
        		boolean conflict = false;
        		try {
					match = new Reservation(rm.getRoomNumber(), checkInDateField.getText(), checkOutDateField.getText(), guest);
	        		for (Reservation rev : curRevs) {
	        			if (match.conflicts(rev)) {
	        				conflict = true;
	        			}
	        		}
	        		if (!conflict) {
	        			return match;
	        		}
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(this, "Invalid date format. Please use MM/DD/YYYY");
				}
        	}
        }
    	
        return null;
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
