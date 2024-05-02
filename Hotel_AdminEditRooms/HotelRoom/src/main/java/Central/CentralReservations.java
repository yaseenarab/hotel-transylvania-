package Central;

import Databases.*;
import Utilities.MyLogger;
import Utilities.DateProcessor;
import Room.Marsha;
import Room.RentableRoom;
import Room.Reservation;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CentralReservations {
    private static final Marsha MARSHA = new Marsha();

    /*
    public static Reservation getReservation(String reservationID) {
        try {
            Method getReservation = ReservationDatabase.class.getDeclaredMethod("getReservation", String.class);
            getReservation.setAccessible(true);
            return ((Reservation) (getReservation.invoke(RESERVATION_DATABASE,reservationID)));
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralReservations.getReservation: " +
                    "Cannot invoke getReservation with " + reservationID);
            return null;
        }
    }*/

    public static String makeReservation(String roomID, String guestID, Date startDate, Date endDate) {
        RentableRoom room;
        Reservation myReservation;
        String reservationID;
        try {
            Method generateReservationID = Marsha.class.getDeclaredMethod("generateReservationID");
            generateReservationID.setAccessible(true);
            reservationID = generateReservationID.invoke(MARSHA).toString();
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error in CentralProfiles.makeReservation: cannot invoke Marsha");
            return null;
        }

        if((room = CentralRoom.getRoom(roomID)) == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in CentralProfiles.makeReservation: room is null");
            return null;
        }

        try {
            roomID = room.getRoomID();
            myReservation = new Reservation(reservationID, guestID, roomID, startDate, endDate);
            CentralDatabase.insertIntoReservations(myReservation.toString());
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error in CentralProfiles.makeReservation: cannot make reservation");
            return null;
        }
        return reservationID;
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
            Connection conectRooms = CentralDatabase.getConHotelRoomsDatabase();
            Connection connectReservations = CentralDatabase.getConReservationDatabase();
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

        Connection con = CentralDatabase.getConHotelRoomsDatabase();
        Statement stmt = con.createStatement();

        ResultSet resultSet = stmt.executeQuery(SQL);

        if(SQL.equals("")){
            throw new SQLException();
        }


        while (resultSet.next()) {


            Object[] obj = new Object[6];

            try {
	            obj[0] = resultSet.getInt("RoomNumber")+"";
	            obj[1] = resultSet.getString("Roomstatus");
	            obj[2] = resultSet.getString("roomtype");
	            obj[3] = resultSet.getString("bedtype");
	            String quality = resultSet.getString("Qualitylevel");
	            if (quality.toLowerCase().contains("level")) {
	            	obj[4] = quality.substring(0, quality.toLowerCase().indexOf("level"));
	            } else if (quality.toLowerCase().contains("executive") || quality.toLowerCase().contains("economy") || quality.toLowerCase().contains("comfort") || quality.toLowerCase().contains("business")) {
	            	obj[4] = quality;
	            } else {
	            	throw new Exception("invalid data");
	            }
	            obj[5] = resultSet.getString("smokingallowed");
	            model.insertRow(0, obj);
            } catch(Exception e) {
            	System.out.println("Failed to add room");
            	e.printStackTrace();
            }

        }
    }
}
