package Hotel.Central;

//import Hotel.Databases.*;
//import Hotel.Databases.GuestDatabase;
//import Hotel.Databases.ReservationDatabase;
import Hotel.AccountService.Guest;
import Hotel.DataBaseManipulation.DataGetSet;
import Hotel.LoggerPackage.MyLogger;
import Hotel.Misc.DateProcessor;
import Hotel.Room.Marsha;
import Hotel.Room.RentableRoom;
import Hotel.Room.Reservation;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

public class CentralReservations {
    private static final Marsha MARSHA = new Marsha();

    public static Map<String,Reservation> getReservations(Guest guest) {
        Map<String,Reservation> reservations = new HashMap();

        try {
            DataGetSet d = new DataGetSet();
            Connection con = d.getConReservationDatabase();
            Statement stmt = con.createStatement();

            ResultSet res= stmt.executeQuery("Select * from Reservations where Username = '" + guest.getUsername() + "'");
            Date start;
            Date end;


            while(res.next()){
                start = DateProcessor.stringToDate( res.getString("startDate"));
                end = DateProcessor.stringToDate( res.getString("endDate"));
                Integer roomId = res.getInt("roomnumber");
                String reservationID = res.getString("reservationid");
                Reservation reserve = new Reservation(reservationID, guest.getGuestID(),roomId, start,end );
                reservations.put(reservationID,reserve);
            }



        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralReservations.getReservation: " +
                    "Cannot invoke getReservation with " + guest.getUsername());
            return null;
        }
        return reservations;
    }

    public static Reservation makeReservation(Integer roomID, String username, String guestID,Date startDate, Date endDate) {
        RentableRoom room;
        Reservation myReservation=null;
        String reservationID;

        try {

            DataGetSet d = new DataGetSet();
            Connection con = d.getConReservationDatabase();
            Statement stmt = con.createStatement();
            String start = DateProcessor.dateToString(startDate);
            String end = DateProcessor.dateToString(endDate);
            ResultSet res = stmt.executeQuery("SELECT * FROM Reservations WHERE reservationid=(SELECT max(reservationid) FROM reservations)");

            Integer idNum;
            if(res.next()){
                String idStr = res.getString("reservationid");
                idNum = Integer.parseInt( idStr.substring(4,idStr.length()));
            }
            else{
                idNum = 1000000000;
            }



            String resID = Marsha.generateReservationID(idNum);
            BigDecimal days = new BigDecimal( DateProcessor.differenceinDays(startDate,endDate));
            BigDecimal totalCost = CentralRoom.calculatorCost(roomID);
            totalCost = totalCost.multiply(days);
            double cost = totalCost.doubleValue();
            String SQL = "INSERT INTO Reservations(Reservationid,Roomnumber,username,startDate,endDate,cost,checkedin) values('"+resID +"'," + roomID + ",'" + username + "','" + start  + "','" + end + "'," + cost +", false)";
            exectueSQL(SQL,stmt);
            //;
            myReservation = new Reservation(resID, guestID, roomID,startDate,endDate);

        } catch (SQLException ex) {
            MyLogger.logger.log(Level.SEVERE, "Error in CentralProfiles.makeReservation: cannot invoke Marsha");
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return myReservation;
    }

    public static void exectueSQL(String SQL, Statement stmt){

        try {
            System.out.println(SQL);
            stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static String displayAvailableRooms(String hotelType, String roomType, String qualityLevel, String bedType, boolean smokingStatus, Date reservationStart, Date reservationEnd){
        String SQL = null;
        try {
            DataGetSet d = new DataGetSet();
            Connection conectRooms = d.getConHottelRoomsDatabase();
            Connection connectReservations = d.getConReservationDatabase();
            Statement roomsStatement = conectRooms.createStatement();
            Statement reservationsStatement = connectReservations.createStatement();


            if(reservationStart == null || reservationEnd == null){
                throw new NullPointerException();
            }
            int low = 0;
            int high = 0;


            SQL = "SELECT  * from Rooms where ";// RoomNumber >=" + low + " AND RoomNumber < " + high +
            // " And RoomType = '" +roomTypeComboBox.getSelectedItem() + "'"+
            // " And QualityLevel = '" + (qualityLevelComboBox.getSelectedItem() + " Level") + "'"
            // + " And BedType = '" + bedTypeComboBox.getSelectedItem()+"'";
            if(!hotelType.equals("Any")) {
                if (hotelType.equals("Nature Retreat")) {
                    low = 100;
                    high = 200;

                } else if (hotelType.equals("Urban Elegance")) {
                    low = 200;
                    high = 300;

                } else if (hotelType.equals("Vintage Charm")) {
                    low = 300;
                    high = 400;

                }
                SQL+=  ("RoomNumber >=" + low + " AND RoomNumber < " + high);
            }
            else{
                SQL += "RoomNumber > 0";
            }


            if(!roomType.equals("Any")){
                SQL += " And RoomType = '" +roomType + "'";
            }

            if(!qualityLevel.equals("Any")){
                SQL += " And QualityLevel = '" + (qualityLevel + " Level") + "'";
            }

            if(!bedType.equals("Any")){
                SQL += " And BedType = '" + bedType+"'";
            }



            //reservationsStatement.executeQuery();


            if(!smokingStatus){
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


    public static void putValues(String SQL, DefaultTableModel model) throws SQLException {

        DataGetSet d = new DataGetSet();
        Connection con = d.getConHottelRoomsDatabase();
        Statement stmt = con.createStatement();

        ResultSet resultSet = stmt.executeQuery(SQL);

        if(SQL.equals("")){
            throw new SQLException();
        }


        while (resultSet.next()) {


            Object[] obj = new Object[6];


            obj[0] = resultSet.getInt("RoomNumber")+"";
            obj[1] = resultSet.getString("Roomstatus");
            obj[2] = resultSet.getString("roomtype");
            obj[3] = resultSet.getString("bedtype");
            String quality = resultSet.getString("Qualitylevel");
            obj[4] = quality.substring(0, quality.length()-5);
            obj[5] = resultSet.getString("smokingallowed");
            model.insertRow(0, obj);

        }
    }
}
