package CatalogueTests;

import Hotel.AccountService.Guest;
import Hotel.Central.CentralDatabase;
import Hotel.Central.CentralProfiles;
import Hotel.Central.CentralReservations;
import Hotel.Central.CentralRoom;
import Hotel.Room.Marsha;
import Hotel.Room.Reservation;
import Hotel.Utilities.DateProcessor;
import Hotel.UI.Frames.LoginFrame;
import org.junit.jupiter.api.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MakeReservationTestCase {



    @BeforeAll
    static void initAll() {
        System.out.println("initAll");



    }

    @BeforeEach
    void init() {
        System.out.println("initEach");

    }

    @Test
    public void testReservationID(){


        Integer resID  = 1000000049;

        assertEquals("TVRI1000000050",Marsha.generateReservationID(resID));
    }

    @Test
    public void testTimeConflict(){
        Date start1Date;
        Date end1Date;
        start1Date = DateProcessor.stringToDate("4/25/2024");
        end1Date = DateProcessor.stringToDate("5/25/2024");
        Date start2Date;
        Date end2Date;
        start2Date = DateProcessor.stringToDate("4/30/2024");
        end2Date = DateProcessor.stringToDate("5/20/2024");


        Calendar start1 = Calendar.getInstance();
        start1.setTime(start1Date);
        Calendar end1 = Calendar.getInstance();
        end1.setTime(end1Date);

        Calendar start2 = Calendar.getInstance();
        start2.setTime(start2Date);
        Calendar end2 = Calendar.getInstance();
        end2.setTime(end2Date);


        assertEquals(true, DateProcessor.isOverlapUsingCalendarAndDuration(start1,end1,start2,end2));
    }

    @Test
    public void testNoTimeConflict(){
        Date start1Date;
        Date end1Date;
        start1Date = DateProcessor.stringToDate("4/25/2024");
        end1Date = DateProcessor.stringToDate("5/25/2024");
        Date start2Date;
        Date end2Date;
        start2Date = DateProcessor.stringToDate("5/26/2024");
        end2Date = DateProcessor.stringToDate("6/15/2024");


        Calendar start1 = Calendar.getInstance();
        start1.setTime(start1Date);
        Calendar end1 = Calendar.getInstance();
        end1.setTime(end1Date);

        Calendar start2 = Calendar.getInstance();
        start2.setTime(start2Date);
        Calendar end2 = Calendar.getInstance();
        end2.setTime(end2Date);



        assertEquals(false, DateProcessor.isOverlapUsingCalendarAndDuration(start1,end1,start2,end2));
    }

    @Test
    public void testStartAndEndConflict() {
        Date start;
        Date end;
        start = DateProcessor.stringToDate("5/30/2024");
        end = DateProcessor.stringToDate("5/27/2024");
        assertEquals(false, DateProcessor.dateTimeConflict(start,end));

    }



    @Test
    public void testOneNightStay(){
        BigDecimal cost = CentralRoom.calculatorCost(101);
        Reservation r =  CentralReservations.makeReservation(101,"b","TVGI1000000000",DateProcessor.stringToDate("05/01/2024"),DateProcessor.stringToDate("05/02/2024"));
        cost = cost.multiply(new BigDecimal("1.25"));

        assertEquals(cost,r.getCost());

    }



    @Test
    public void testRegularCost(){
        BigDecimal cost = CentralRoom.calculatorCost(101);
        Reservation r =  CentralReservations.makeReservation(101,"b","TVGI1000000000",DateProcessor.stringToDate("05/01/2024"),DateProcessor.stringToDate("05/03/2024"));

        cost = cost.multiply(new BigDecimal("2.0"));

        assertEquals(cost,r.getCost());

    }


    /*
    @Test
    public void testDatabaseSaveCost(){


        try {
            Connection con = CentralDatabase.getConReservationDatabase();
            Statement stmt = con.createStatement();
            Date currentDate = new Date();

            Integer startingCost = DateProcessor.differenceinDays(DateProcessor.stringToDate( "04/15/2024"), DateProcessor.stringToDate( "05/10/2024"));

            Integer startDays = DateProcessor.differenceinDays(DateProcessor.stringToDate( "04/15/2024"), DateProcessor.stringToDate( "04/30/2024"));
            Integer lastDays = DateProcessor.differenceinDays(DateProcessor.stringToDate( "04/30/2024"), DateProcessor.stringToDate( "05/10/2024"));


            Integer firstCost = startDays * CentralRoom.calculatorCost(330).intValue();
            Integer lastCost = startDays * CentralRoom.calculatorCost(101).intValue();

            //Cost from 4/15- 4/30 in room 330: 10260
            //Cost from 4/30 - 5/10 in room 101:2450

            stmt.executeUpdate("insert into reservations(roomnumber,username,startDAte,endDate, reservationid,cost,checkedin,onenight) values(330,'test','04/15/2024','05/10/2024','testID',"+ (double)startingCost +",true, false)");

            Guest test = CentralProfiles.getGuest("test","a");
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);

            loginFrame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    try {
                        ResultSet res = stmt.executeQuery("Select * from reservations where ReservaitonId = 'testID'");
                        res.next();
                        assertEquals(12710.0,res.getDouble("cost"));

                        PreparedStatement pstmt = con.prepareStatement("Delete from Reservations where ReservaitonId = 'testID'");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                    e.getWindow().dispose();
                }
            });
            //PreparedStatement pstmt = con.prepareStatement("");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

     */



    @AfterEach
    void tearDown() {
        System.out.println("afterEach");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("afterAll");
    }



}
