package Hotel.UI;


import Hotel.AccountService.Guest;
import Hotel.Central.CentralReservations;
import Hotel.DataBaseManipulation.DataGetSet;
//import Hotel.ReservationService.Reservation;
//import Hotel.ReservationService.Reservations;
//import Hotel.ReservationService.RoomReservation;
//import Hotel.Enums.RoomType;
//import Hotel.RoomQuality;
import Hotel.Enums.QualityLevel;
import Hotel.Misc.DateProcessor;
import Hotel.Room.Reservation;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.Duration;
import java.util.List;
import java.util.*;



public class ReserveRoomPanel extends JPanel {

    //private Reservations reservations;
    private JTextField guestNameField, memberNumberField, cardNumberField;
    private JComboBox<String> hotelTypeComboBox, roomTypeComboBox, qualityLevelComboBox, bedTypeComboBox;
    private JFormattedTextField checkInDateField, checkOutDateField;
    private JDatePickerImpl checkInDatePicker,checkOutDatePicker;
    private JCheckBox smokingCheckBox;
    private JOptionPane jPane;
    private AvailableRoomsPanel availableRoomsPanel;
    private UtilDateModel checkOutDateModel,checkInDateModel;
    private JTextArea reservationSummary = new JTextArea();
    private JTable roomAvailability;
    private DefaultTableModel roomAvailModel;
    private JButton reserveButton, exitButton, quoteButton, roomButton,showAvailableRoomsButton;
    //private RoomQuality roomNeeds;
    private Guest guest;
    private Date reservationStart,reservationEnd;
    public ReserveRoomPanel( Guest guest) throws Exception {
        //reservations = new Reservations();
        this.guest = guest;

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        JLabel guestLabel = new JLabel("Guest Name:");
        guestLabel.setPreferredSize(new Dimension(300,50));
        formPanel.add(guestLabel);
        guestNameField = new JTextField(guest.getFirstName() + " " + guest.getLastName());
        formPanel.add(guestNameField);

        JLabel member = new JLabel("Member Number (optional):");
        member.setBorder(new EmptyBorder(0, 0, 0, 0));
        //member.setFont(new Font("hel", Font.BOLD,10));
        //member.setPreferredSize(new Dimension(400,300));
        formPanel.add(member);
        memberNumberField = new JTextField();
        formPanel.add(memberNumberField);

        formPanel.add(new JLabel("Credit Card Number:"));
        cardNumberField = new JTextField();
        formPanel.add(cardNumberField);

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
        p.put("text.year        bottomPanel.add(quoteButton);", "Year");
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

        quoteButton = new JButton("Quote");
        //bottomPanel.add(quoteButton);
        quoteButton.addActionListener(e -> quoteReservation());

        roomButton = new JButton("Show Rooms");
        bottomPanel.add(roomButton);
        roomButton.addActionListener(e -> displayRoomAvailability());

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


        JLabel member = new JLabel("Member Number (optional):");
        member.setBorder(new EmptyBorder(0, 0, 0, 0));
        //member.setFont(new Font("hel", Font.BOLD,10));
        //member.setPreferredSize(new Dimension(400,300));
        formPanel.add(member);
        memberNumberField = new JTextField();
        formPanel.add(memberNumberField);

        formPanel.add(new JLabel("Credit Card Number:"));
        cardNumberField = new JTextField();
        formPanel.add(cardNumberField);

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
        p.put("text.year        bottomPanel.add(quoteButton);", "Year");
        formPanel.add(new JLabel("Check-in Date:"));
        UtilDateModel checkIndateModel = new UtilDateModel();
        JDatePanelImpl checkIndatePanel = new JDatePanelImpl(checkIndateModel,p);

        checkInDatePicker = new JDatePickerImpl(checkIndatePanel, new DateLabelFormatter());
        checkInDatePicker.addActionListener(e -> updateTable());

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

        quoteButton = new JButton("Quote");
        //bottomPanel.add(quoteButton);
        quoteButton.addActionListener(e -> quoteReservation());

        roomButton = new JButton("Show Rooms");
        bottomPanel.add(roomButton);
        roomButton.addActionListener(e -> displayRoomAvailability());

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

        Date currentDate = new Date();
        Date today = new Date(currentDate.getTime() - Duration.ofDays(1).toMillis());
        System.out.println(today.toString());
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
        try {
            boolean isHigherStatus = checkGuestStatus();
            double rate = calculateRate(isHigherStatus, (String) roomTypeComboBox.getSelectedItem());

            if(!availableRoomsPanel.roomIsSelected()){
                throw new NullPointerException();
            }

            Integer roomID = availableRoomsPanel.getRoomID();
            reserve = CentralReservations.makeReservation(roomID, guest.getUsername(),guest.getGuestID(),reservationStart,reservationEnd);
            guest.addReservation(reserve);

            System.out.println("ADDING THISKR ROOM NUMBER ASFWEABVJHU" + roomID);
            JOptionPane.showMessageDialog(this, "Reservation Successfully made!");
            updateTable();

            //Reservation res = assignRoom((String) hotelTypeComboBox.getSelectedItem());

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

    private void displayRoomAvailability() {
        JFrame tableFrame = new JFrame();
        JPanel tablePanel= new JPanel();

        String[] columnNames = {"Rm #", "Rm Typ", "Qual Lvl", "Rm Stat", "Bd Typ", "Smokng"};
        roomAvailModel = new DefaultTableModel();
        for(String name : columnNames) {
            roomAvailModel.addColumn(name);
        }
        try {
            DataGetSet d = new DataGetSet();
            Connection con = d.getConHottelRoomsDatabase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * from Rooms");

            while(res.next()){
                String[] roomKey = {
                        res.getInt("RoomNumber")+"",
                        res.getString("Roomstatus"),
                        res.getString("RoomType"),
                        res.getString("BedType"),
                        res.getString("QualityLevel"),
                        res.getBoolean("SmokingAllowed") +""};
                roomAvailModel.addRow(roomKey);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JTable availRooms = new JTable(roomAvailModel);
        tableFrame.getContentPane().add(new JScrollPane(availRooms));
        availRooms.setFillsViewportHeight(true);

        tablePanel.setLayout(new BorderLayout());
        tableFrame.setSize(500,500);
        tableFrame.setVisible(true);
    }


    private double calculateRate(boolean isHigherStatus, String roomType) {
        double baseRate = 100;
        if (isHigherStatus) {
            return baseRate * 0.8;
        }
        return baseRate;
    }

    private Reservation assignRoom(String hotelType) {
        //List<RoomQuality> roomOptions;
        Reservation match = null;;
        BufferedReader reader = null;
        List<Reservation> curRevs = new ArrayList<Reservation>();

        try {
            reader = new BufferedReader(new FileReader(new File("reservations.csv")));

            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                try {
                    //curRevs.add(new Reservation(Integer.valueOf(split[0]), split[2], split[3], null));
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


        /*
    	if (("Nature Retreat").equals(hotelType)) {
            roomOptions = reservations.getFirstFloorRooms();
        } else if ("Urban Elegance".equals(hotelType)) {
            roomOptions = reservations.getSecondFloorRooms();
        } else if ("Vintage Charm".equals(hotelType)) {
            roomOptions = reservations.getThirdFloorRooms();
        } else {
        	throw new IllegalArgumentException();
        }

         */

        /*
    	for (RoomQuality rm : roomOptions) {


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

         */

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

    public void fillBoxes(Integer roomID, String roomType, String bedType, String qualityLevel, boolean smoking, String startDate, String endDate) {

        if (roomID >= 100 && roomID < 200) {hotelTypeComboBox.setSelectedIndex(1);}
        else if (roomID >= 200 && roomID < 300) {hotelTypeComboBox.setSelectedIndex(2);}
        else if (roomID >= 300 && roomID < 400) {hotelTypeComboBox.setSelectedIndex(3);}

        if (roomType.equals("Single")) {roomTypeComboBox.setSelectedIndex(1);}
        else if (roomType.equals("Double")) {roomTypeComboBox.setSelectedIndex(2);}
        else if (roomType.equals("Family")) {roomTypeComboBox.setSelectedIndex(3);}
        else if (roomType.equals("Deluxe")) {roomTypeComboBox.setSelectedIndex(2);}
        else if (roomType.equals("Standard")) {roomTypeComboBox.setSelectedIndex(1);}

        if (bedType.equals("Twin")) {
            bedTypeComboBox.setSelectedIndex(1);
        } else if (bedType.equals("Full")) {
            bedTypeComboBox.setSelectedIndex(2);
        } else if (bedType.equals("Queen")) {
            bedTypeComboBox.setSelectedIndex(3);
        } else if (bedType.equals("King")) {
            bedTypeComboBox.setSelectedIndex(4);
        }


        if (qualityLevel.equals("Business Level")) {
            qualityLevelComboBox.setSelectedIndex(2);
        } else if (qualityLevel.equals("Economy Level")) {
            qualityLevelComboBox.setSelectedIndex(4);
        } else if (qualityLevel.equals("Executive Level")) {
            qualityLevelComboBox.setSelectedIndex(1);
        } else if (qualityLevel.equals("Comfort Level")) {
            qualityLevelComboBox.setSelectedIndex(3);
        }

        smokingCheckBox.setSelected(smoking);

        Date start = DateProcessor.stringToDate(startDate);
        Date end = DateProcessor.stringToDate(endDate);

        System.out.println("Start DAY: " + DateProcessor.getDay(startDate));
        System.out.println("Start Month: " + DateProcessor.getMonth(startDate));
        System.out.println("Start Year: " + DateProcessor.getYear(startDate));

        System.out.println("End DAY: " + DateProcessor.getDay(endDate));
        System.out.println("End Month: " + DateProcessor.getMonth(endDate));
        System.out.println("End Year: " + DateProcessor.getYear(endDate));

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