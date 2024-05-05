package Hotel.Central;

import Hotel.AccountService.Guest;

import Hotel.Utilities.MyLogger;
import Hotel.Utilities.DateProcessor;
import Hotel.Room.Marsha;
import Hotel.Room.RentableRoom;
import Hotel.Room.Reservation;

import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;


/**
 * Central management for hotel reservations, handling database interactions
 * for reservation data retrieval and updates.
 */
public class CentralReservations {
    private static Integer nightsStayed = 0;


    /**
     * Retrieves all reservations for a specific guest from the database.
     *
     * @param guest The guest for whom to retrieve reservations.
     * @return A map of reservation IDs to Reservation objects if successful, otherwise null.
     */
    public static Map<String,Reservation> getReservations(Guest guest) {
        Map<String,Reservation> reservations = new HashMap();

        try {

            Connection con = CentralDatabase.getConReservationDatabase();
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

    /**
     * Sets the number of nights a guest has stayed.
     *
     * @param nightsStayed The number of nights stayed.
     */
    public static void setNightsStayed(Integer nightsStayed) {
        CentralReservations.nightsStayed = nightsStayed;
    }
    /**
     * Gets the number of nights stayed.
     *
     * @return The number of nights stayed.
     */
    public static Integer getNightsStayed() {
        return nightsStayed;
    }



    /**
     * Creates a reservation in the system.
     *
     * @param roomID     The room ID to book.
     * @param username   The username of the guest making the booking.
     * @param guestID    The ID of the guest.
     * @param startDate  The start date of the reservation.
     * @param endDate    The end date of the reservation.
     * @return A new Reservation object.
     */
    public static Reservation makeReservation(Integer roomID, String username, String guestID, Date startDate, Date endDate) {
        RentableRoom room;
        Reservation myReservation=null;
        String reservationID;

        try {


            Connection con = CentralDatabase.getConReservationDatabase();
            Statement stmt = con.createStatement();
            String start = DateProcessor.dateToString(startDate);
            String end = DateProcessor.dateToString(endDate);
            ResultSet res = stmt.executeQuery("SELECT * FROM Reservations WHERE reservationid=(SELECT max(reservationid) FROM reservations)");
            Integer idNum;

            if(res.next()){
                String idStr = res.getString("reservationid");

                idNum = Integer.parseInt( idStr.substring(4,idStr.length()));
                double cost = res.getDouble("Cost");



            }
            else{
                idNum = 1000000000;
            }

            String resID = Marsha.generateReservationID(idNum);
            BigDecimal days = new BigDecimal( DateProcessor.differenceinDays(startDate,endDate));
            BigDecimal totalCost = CentralRoom.calculatorCost(roomID);
            totalCost = totalCost.multiply(days);

            double oneNightStayMultiplier  = handleOneNightStay(startDate,endDate);


            double cost = totalCost.doubleValue() * oneNightStayMultiplier;
            String SQL = "INSERT INTO Reservations(Reservationid,Roomnumber,username,startDate,endDate,cost,checkedin,nightsStayed) values('"+resID +"'," + roomID + ",'" + username + "','" + start  + "','" + end + "'," + cost+", false, " + nightsStayed + ")";
            exectueSQL(SQL,stmt);

            //;
            myReservation = new Reservation(resID, guestID, roomID,startDate,endDate);
            //myReservation.setCost(cost);

        } catch (SQLException ex) {
            MyLogger.logger.log(Level.SEVERE, "Error in CentralProfiles.makeReservation: cannot invoke Marsha");
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return myReservation;
    }

    /**
     * Calculates a cost multiplier for reservations that are only for one night.
     *
     * @param startDate The start date of the reservation.
     * @param endDate   The end date of the reservation.
     * @return The multiplier to be applied to the cost.
     */
    private static double handleOneNightStay(Date startDate, Date endDate) {

        double oneNightStayMultiplier = 1;
        if(nightsStayed == 0 && DateProcessor.differenceinDays(startDate,endDate) == 1){
            oneNightStayMultiplier = 1.25;
        }
        return oneNightStayMultiplier;
    }

    /**
     * Executes a given SQL statement.
     *
     * @param SQL  The SQL statement to be executed.
     * @param stmt The statement object used to execute the SQL.
     */
    public static void exectueSQL(String SQL, Statement stmt){

        try {

            stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resets the nights stayed count to zero.
     */
    public static void resetEditStatus(){
        nightsStayed = 0;
    }

    /**
     * Displays available rooms based on specified criteria and reservation dates.
     *
     * @param hotelType        The type of hotel.
     * @param roomType         The type of room.
     * @param qualityLevel     The quality level of the room.
     * @param bedType          The type of bed.
     * @param smokingStatus    Whether the room allows smoking.
     * @param reservationStart The start date of the reservation.
     * @param reservationEnd   The end date of the reservation.
     * @return An SQL query string that fetches available rooms.
     */
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
                while(resFirst.next()){
                    SQL2 += (" OR RoomNumber = " + resFirst.getInt("RoomNumber"));
                }

            }
            else{
                availableRoomsFound = false;

            }


            if(availableRoomsFound){
                resSecond = reservationsStatement.executeQuery(SQL2);
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


                        }
                    }
                }
                resFirst = roomsStatement.executeQuery(SQL);
                //availableRoomsPanel.updatePanel();
            }


        } catch (SQLException e) {
            //throw new RuntimeException(e);
        } catch (ParseException e) {
            //throw new RuntimeException(e);
        }catch (NullPointerException e){
            SQL = null;
        }

        return SQL;

    }

    /**
     * Inserts the values from a SQL query into a table model.
     *
     * @param SQL   The SQL query to execute.
     * @param model The table model to be populated with the query results.
     * @throws SQLException If an SQL error occurs.
     */
    public static void putValues(String SQL, DefaultTableModel model) throws SQLException {


        Connection con = CentralDatabase.getConHotelRoomsDatabase();
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
            if (quality.toLowerCase().contains("level")) {
            	obj[4] = quality.substring(0, quality.toLowerCase().indexOf("level"));
            } else if (quality.toLowerCase().contains("executive") || quality.toLowerCase().contains("economy") || quality.toLowerCase().contains("comfort") || quality.toLowerCase().contains("business")) {
            	obj[4] = quality;
            } else {
            	throw new SQLException("invalid data read");
            }
            obj[5] = resultSet.getString("smokingallowed");
            model.insertRow(0, obj);

        }
    }
    /**
     * Retrieves all reservation records from the database.
     *
     * @return A ResultSet containing all reservation records.
     * @throws SQLException If an SQL error occurs during the retrieval.
     */
    public static ResultSet getReservaions()  {

        try {

            Connection con = CentralDatabase.getConReservationDatabase();
            Statement stmt = con.createStatement();

            return stmt.executeQuery("SELECT * from RESERVATIONS");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    /**
     * Updates the cost associated with a specific reservation by adding an additional cost.
     *
     * @param resID     The reservation ID to be updated.
     * @param addedCost The additional cost to add.
     */

    public static void updateCost(String resID, double addedCost) {
        Connection con = CentralDatabase.getConReservationDatabase();
        try {

            PreparedStatement pstmt = con.prepareStatement("UPDATE Reservations Set cost = cost +" +  addedCost  + " where  reservationid = '" + resID + "'");
            pstmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
