package Hotel.Central;

import Hotel.Room.RentableRoom;
import Hotel.Utilities.MyLogger;
import Hotel.Room.RoomInitializer;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;



/**
 * Provides functionality related to room operations and initialization.
 */
public class CentralRoom {
    private static boolean roomsInitialized;
    private static final RoomInitializer ROOM_INITIALIZER = new RoomInitializer();
    //private static final RoomAvailabilityDatabase ROOM_AVAILABILITY_DATABASE = new RoomAvailabilityDatabase();
    //private static final RoomCostDatabase ROOM_COST_DATABASE = new RoomCostDatabase();


    /**
     * gets whether the room is initialized or not.
     *
     *
     * @return returns true if the room is initialized and false if not.
     */
    public static boolean initializeRooms() {
        /*
        try {
            Method initRooms = RoomInitializer.class.getDeclaredMethod("initRooms", Integer.class, Integer.class);
            initRooms.setAccessible(true);
            initRooms.invoke(ROOM_INITIALIZER,numFloors, numRooms);

            roomsInitialized = true;
        }
        catch (Exception e) {
            roomsInitialized = false;
        }


         */
        return roomsInitialized;
    }

    /**
     * Calculates the cost of a room based on its attributes.
     *
     * @param roomId The ID of the room.
     * @return The cost of the room as a BigDecimal.
     */
    public static BigDecimal calculatorCost(Integer roomId){
        BigDecimal b = null;

        try {

            Connection con = CentralDatabase.getConHotelRoomsDatabase();
            Statement stmt = con.createStatement();
            ResultSet res =  stmt.executeQuery("Select * from Rooms where Roomnumber = " + roomId);
            double rate = 1;
            if(res.next()){
                if(roomId >= 100 && roomId < 200){
                    rate = 100;
                }
                else if(roomId >= 200 && roomId < 300){
                    rate = 200;
                }
                else if(roomId >= 300 && roomId < 400){
                    rate = 500;
                }
                String roomType = res.getString("RoomType");

                if(roomType.equals("Single")){
                    rate = rate + 10;
                }
                if(roomType.equals("Double")){
                    rate = rate + 20;
                }
                if(roomType.equals("Family")){
                    rate = rate + 30;
                }
                if(roomType.equals("Deluxe")){
                    rate = rate + 40;
                }
                if(roomType.equals("Standard")){
                    rate = rate + 50;
                }

                String bedType = res.getString("bedType");

                if(bedType.equals("Twin")){
                    rate += 5;
                }
                else if(bedType.equals("Full")){
                    rate += 10;
                }
                else if(bedType.equals("Queen")){
                    rate += 20;
                }
                else if(bedType.equals("King")){
                    rate += 30;
                }

                String qualitylevel = res.getString("qualitylevel");

                if(qualitylevel.equals("Business Level")){
                    rate = rate*1.2;
                }
                else if(qualitylevel.equals("Economy Level")){
                    rate = rate *0.8;
                }
                else if(qualitylevel.equals("Executive Level")){
                    rate = rate * 1.5;
                }

                boolean smoke = res.getBoolean("Smokingallowed");
                if(!smoke){
                    rate = rate + 20;
                }
                b = new BigDecimal(rate);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return b;
    }
    /**
     * Adds a room to the system.
     *
     * @param roomID The ID of the room to add.
     * @param room   The RentableRoom object representing the room.
     */
    public static void addRoom(String roomID, RentableRoom room) {

        try {
            //Method addRoom = RoomAvailabilityDatabase.class.getDeclaredMethod("addRoom", String.class, RentableRoom.class);
            //addRoom.setAccessible(true);
            //addRoom.invoke(ROOM_AVAILABILITY_DATABASE, roomID, room);
        } catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralReservations.addRoom: " +
                    "Values passed were " + roomID + "," + room);
        }


    }
    /**
     * Quotes a room for a guest.
     *
     * @param roomID  The ID of the room to quote.
     * @return The quoted room rate as an Integer.
     */
    public static Integer quoteRoom(String roomID) {

        try {
            //Method calculateDailyRate = RoomCostDatabase.class.getDeclaredMethod("calculateDailyRate", String.class, String.class);
            //calculateDailyRate.setAccessible(true);
            //return ((Integer)(calculateDailyRate.invoke(ROOM_COST_DATABASE, roomID, guestID)));
        } catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralReservations.quoteRoom: " +
                    "Values passed were " + roomID);
            return null;
        }


        return 1;
    }

    /**
     * Initializes rooms based on the number of floors and rooms per floor.
     *
     * @param numFloors The number of floors in the hotel.
     * @param numRooms  The number of rooms per floor.
     * @param username  The username of the administrator initiating room initialization.
     * @return True if rooms are successfully initialized, false otherwise.
     */
    public static boolean initializeRooms(Integer numFloors, Integer numRooms, String username) {
        if(CentralProfiles.AdminisIn(username)) {
            try {
                Method initRooms = RoomInitializer.class.getDeclaredMethod("initRooms", Integer.class, Integer.class);
                initRooms.setAccessible(true);
                initRooms.invoke(ROOM_INITIALIZER,numFloors, numRooms);

                roomsInitialized = true;
            }
            catch (Exception e) {
                roomsInitialized = false;
            }
        }
        else {
            roomsInitialized = false;
        }

        return roomsInitialized;
    }

    /**
     * Retrieves a room based on its ID.
     *
     * @param roomID The ID of the room to retrieve.
     * @return The RentableRoom object representing the room.
     */
    public static RentableRoom getRoom(String roomID) {
        try {
            //Method getRoom = RoomAvailabilityDatabase.class.getDeclaredMethod("getRoom", String.class);
            //getRoom.setAccessible(true);
            //return ((RentableRoom)getRoom.invoke(ROOM_AVAILABILITY_DATABASE, roomID));
        } catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralReservations.getRoom: " +
                    "Values passed was " + roomID);
            return null;
        }
        return null;
    }
}
