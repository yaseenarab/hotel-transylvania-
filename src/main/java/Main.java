import Hotel.Central.CentralProfiles;
//import Hotel.Central.CentralRoom;
//import Hotel.Central.CentralRoomID;
//import Hotel.Central.CentralReservations;
import Hotel.Central.CentralReservations;
import Hotel.Central.CentralRoom;
import Hotel.Central.CentralRoomID;
import Hotel.LoggerPackage.MyLogger;
import Hotel.Misc.DateProcessor;
import Hotel.Room.Reservation;
import Hotel.UI.LoginFrame;

import java.util.Date;
import java.util.logging.Level;

import static Hotel.Central.CentralRoom.initializeRooms;

//import static Hotel.Central.CentralRoom.initializeRooms;

public class Main {
    public static void main(String[] args) {

        System.out.println(CentralRoom.calculatorCost(310));
        //CentralProfiles.getEmployeeProfiles();
        /*
        int numFloors = 3, numRooms = 36;
        if(!initializeRooms(numFloors, numRooms)) {
            MyLogger.logger.log(Level.SEVERE, "Failed to initialize rooms. Exiting program");
            System.exit(1);
        }
        String myID = CentralProfiles.makeGuestProfile(
                "Mitchell", "Thompson", "MDT2001@icloud.com",
                "9497690969", "username", "password");
        if(myID != null) {
            System.out.println(myID);
        }

        Integer roomID = 128;
        if(roomID != null) {
            System.out.println(roomID);
        }

        Date startDate = DateProcessor.stringToDate("04/22/24");
        Date endDate = DateProcessor.stringToDate("4/25/24");

        Integer cost = CentralRoom.quoteRoom(roomID, myID);
        if(cost != null) {
            System.out.println(cost);
        }

        String reservationID = CentralReservations.makeReservation(roomID, myID, startDate, endDate);
        if(reservationID != null) {
            System.out.println(reservationID);
        }

        Reservation myReservation = CentralReservations.getReservation(reservationID);
        if(myReservation != null) {
            System.out.println(myReservation.getRoomCost());
            System.out.println(myReservation.getRoomID());
        }

         */
        LoginFrame login = new LoginFrame();

        login.setVisible(true);
    }


}
