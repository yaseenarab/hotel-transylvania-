package Hotel.UI;


import Hotel.AccountService.Guest;
import Hotel.Central.CentralReservations;
import Hotel.DataBaseManipulation.DataGetSet;
//import Hotel.ReservationService.Reservation;
//import Hotel.ReservationService.Reservations;
//import Hotel.ReservationService.RoomReservation;
//import Hotel.Enums.RoomType;
//import Hotel.RoomQuality;
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
    private AvailableRoomsPanel availableRoomsPanel;
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
        if(reservationStart != null && reservationEnd != null && reservationEnd.after(reservationStart)&& reservationStart.after(today)){
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

    private void performReservation() {
	    try {
	    	boolean isHigherStatus = checkGuestStatus();
	        double rate = calculateRate(isHigherStatus, (String) roomTypeComboBox.getSelectedItem());

            if(!availableRoomsPanel.roomIsSelected()){
                throw new NullPointerException();
            }

            Integer roomID = availableRoomsPanel.getRoomID();
            Reservation reserve = CentralReservations.makeReservation(roomID, guest.getUsername(),guest.getGuestID(),reservationStart,reservationEnd);
            guest.addReservation(reserve);
            JOptionPane.showMessageDialog(this, "Reservation Successfully made!");
            updateTable();

	        //Reservation res = assignRoom((String) hotelTypeComboBox.getSelectedItem());
            /*
	        RoomQuality guestRoom;
	        if ((guestRoom = Reservations.RoomInitializer.getRoomAvailable(roomNeeds)) != null) {
                //String idRes = Reservations.Marsha.generateReservationID();
                RoomReservation myReservation = new RoomReservation(guest, guestRoom, checkInDateField.getText(),checkOutDateField.getText());
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
		                        "Assigned Room Number: " + guestRoom.getRoomNumber() + "\n" +
                                "Reservation ID: " + myReservation.getResID() + "\n");
                Reservations.Marsha.addReservation(myReservation);



		        JOptionPane.showMessageDialog(this, "Reservation has been made successfully. \nReservation ID: " + myReservation.getResID());
		        Connection con = null;
                try {
                    DataGetSet d = new DataGetSet();
                    con = d.getConReservationDatabase();
                    Statement stmt = con.createStatement();
                    //String [] split = myReservation.csvOutput().split(",");
                    stmt.executeUpdate("INSERT into reservations(roomnumber,username,startdate,enddate) values(" +Integer.parseInt(split[0])  + ",'" +split[1] + "','"+split[2] + "','" + split[3] +"')");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            } else {
	        	JOptionPane.showMessageDialog(this, "No available rooms found. Please try again");
	        }

             */

	    } catch (NullPointerException e) {
	    	JOptionPane.showMessageDialog(this, "Please select a row from the table.");
	    	e.printStackTrace();
	    } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkGuestStatus() {
        return memberNumberField.getText().length() > 0;
    }


/*
    private String displayAvailableRooms(){
        String SQL = null;
        try {
            DataGetSet d = new DataGetSet();
            Connection conectRooms = d.getConHottelRoomsDatabase();
            Connection connectReservations = d.getConReservationDatabase();
            Statement roomsStatement = conectRooms.createStatement();
            Statement reservationsStatement = connectReservations.createStatement();

            reservationStart = (Date)checkInDatePicker.getModel().getValue();
            reservationEnd = (Date)checkOutDatePicker.getModel().getValue();

            if(reservationStart == null || reservationEnd == null){
                throw new NullPointerException();
            }
            int low = 0;
            int high = 0;


            SQL = "SELECT  * from Rooms where ";// RoomNumber >=" + low + " AND RoomNumber < " + high +
            // " And RoomType = '" +roomTypeComboBox.getSelectedItem() + "'"+
            // " And QualityLevel = '" + (qualityLevelComboBox.getSelectedItem() + " Level") + "'"
            // + " And BedType = '" + bedTypeComboBox.getSelectedItem()+"'";
            if(!hotelTypeComboBox.getSelectedItem().equals("Any")) {
                if (hotelTypeComboBox.getSelectedItem().equals("Nature Retreat")) {
                    low = 100;
                    high = 200;

                } else if (hotelTypeComboBox.getSelectedItem().equals("Urban Elegance")) {
                    low = 200;
                    high = 300;

                } else if (hotelTypeComboBox.getSelectedItem().equals("Vintage Charm")) {
                    low = 300;
                    high = 400;

                }
                SQL+=  ("RoomNumber >=" + low + " AND RoomNumber < " + high);
            }
            else{
                SQL += "RoomNumber > 0";
            }


            if(!roomTypeComboBox.getSelectedItem().equals("Any")){
                SQL += " And RoomType = '" +roomTypeComboBox.getSelectedItem() + "'";
            }

            if(!qualityLevelComboBox.getSelectedItem().equals("Any")){
                SQL += " And QualityLevel = '" + (qualityLevelComboBox.getSelectedItem() + " Level") + "'";
            }

            if(!bedTypeComboBox.getSelectedItem().equals("Any")){
                SQL += " And BedType = '" + bedTypeComboBox.getSelectedItem()+"'";
            }



            //reservationsStatement.executeQuery();


            if(!smokingCheckBox.isSelected()){
                SQL += " And SmokingAllowed = False ";
            }

            ResultSet resFirst = roomsStatement.executeQuery(SQL);

            ResultSet resSecond;

            String SQL2 =null;
            Boolean availableRoomsFound = true;

            if(resFirst.next()){

                SQL2= "Select * from Reservations where RoomNumber = " + resFirst.getInt("RoomNumber");
                System.out.println(resFirst.getInt("RoomNumber"));
                while(resFirst.next()){
                    SQL2 += (" OR RoomNumber = " + resFirst.getInt("RoomNumber"));
                    System.out.println(resFirst.getInt("RoomNumber"));
                }

            }
            else{
                availableRoomsFound = false;

            }


            if(availableRoomsFound){
                System.out.println(SQL2);
                resSecond = reservationsStatement.executeQuery(SQL2);
                System.out.println("WE got this Far");
                Map<Integer, String> check = new HashMap<Integer, String>();

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                while(resSecond.next()){

                    if(!check.containsKey(resSecond.getInt("RoomNumber"))){
                        Date comparedStart = df.parse(resSecond.getString("StartDate"));
                        Date comparedEnd = df.parse(resSecond.getString("EndDate"));

                        Calendar start1 = Calendar.getInstance();
                        start1.setTime(reservationStart);
                        Calendar end1 = Calendar.getInstance();
                        end1.setTime(reservationEnd);

                        Calendar start2 = Calendar.getInstance();
                        start2.setTime(comparedStart);
                        Calendar end2 = Calendar.getInstance();
                        end2.setTime(comparedEnd);

                        if(DateProcessor.isOverlapUsingCalendarAndDuration(start1,end1,start2,end2) ){
                            SQL += " And Not RoomNumber = " + resSecond.getInt("RoomNumber");
                            check.put(resSecond.getInt("RoomNumber"), "");
                            System.out.println("Overlapped V");


                        }
                        System.out.println( resSecond.getInt("RoomNumber")+"   " + resSecond.getString("username") + "   " + resSecond.getString("StartDate") + "     " + resSecond.getString("EndDate"));

                    }
                }
                resFirst = roomsStatement.executeQuery(SQL);
                //availableRoomsPanel.updatePanel();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e){
            System.out.println("DATE IS NULL");
            SQL = null;
        }

        return SQL;

    }

 */

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


}
