package Room;

import Central.CentralRoom;
import Central.CentralRoomID;
import Utilities.MyLogger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;

public class Reservation {
    private final int RES_ID_LENGTH = 14, GUEST_ID_LENGTH = 14, ROOM_ID_LENGTH = 7;
    private String reservationID, guestID, roomID;
    private Date startDate, endDate;
    private BigDecimal roomCost;
    public Reservation(String reservationID, String guestID, String roomID,
                       Date startDate, Date endDate) throws Exception {
        try {
            this.setReservationID(reservationID);
            this.setGuestID(guestID);
            this.setRoomID(roomID);
            this.setStayLength(startDate, endDate);
            this.setRoomCost();
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in Reservation constructor:  received " +
                    reservationID + "," + guestID + "," + roomID + "," + startDate + "," + endDate);
            throw new Exception();
        }
    }
    private void setReservationID(String reservationID) throws Exception {
        if(reservationID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setReservationID: reservationId is null");
            throw new Exception();
        }
        else if (reservationID.length() != RES_ID_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setReservationID: " +
                    "reservatinID should be " + RES_ID_LENGTH + " characters, found " +
                    reservationID.length() + ", received " + reservationID);
            throw new Exception();
        }

        this.reservationID = reservationID;
    }
    private void setGuestID(String guestID) throws Exception {
        if(guestID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setGuestID: guestID is null");
            throw new Exception();
        }
        else if (guestID.length() != GUEST_ID_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setGuestID: " +
                    "guestID should be " + GUEST_ID_LENGTH + " characters, found " +
                    guestID.length() + ", received " + guestID);
            throw new Exception();
        }

        this.guestID = guestID;
    }
    private void setRoomID(String roomID) throws Exception {
        if(roomID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setRoomID: roomID is null");
            throw new Exception();
        }
        else if (roomID.length() != ROOM_ID_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setRoomID: roomID should be " +
                    ROOM_ID_LENGTH + " characters, found " + roomID.length() + ", received " + roomID);
            throw new Exception();
        }
        else if (!RoomID.checkValidRoomID(roomID)) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setRoomID: roomID is invalid, received " +
                    roomID);
            throw new Exception();
        }

        this.roomID = roomID;
    }
    private void setStayLength(Date startDate, Date endDate) throws Exception{
        if(startDate == null) {
            MyLogger.logger.log(Level.SEVERE,
                    "Error in Reservation.setStayLength: " + startDate + " is null");
            throw new Exception();
        }
        else if(endDate == null) {
            MyLogger.logger.log(Level.SEVERE,
                    "Error in Reservation.setStayLength: " + endDate + " is null");
            throw new Exception();
        }
        else if(startDate.after(endDate)) {
            MyLogger.logger.log(Level.SEVERE,
                    "Error in Reservation.setStayLength: " + startDate + " is after " + endDate);
            throw new Exception();
        }
        else if(startDate.equals(endDate)) {
            MyLogger.logger.log(Level.SEVERE,
                    "Error in Reservation.setStayLength: " + startDate + " == " + endDate);
            throw new Exception();
        }
        else if((new Date()).after(startDate)) {
            MyLogger.logger.log(Level.SEVERE,
                    "Error in Reservation.setStayLength: " + startDate + "is before current date");
            throw new Exception();
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }
    private void setRoomCost() throws Exception {
        if (this.startDate == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setRoomCost: startDate is null");
            throw new Exception();
        }
        else if (this.endDate == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setRoomCost: endDate is null");
            throw new Exception();
        }
        else if (this.roomID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setRoomCost: roomID is null");
            throw new Exception();
        }
        else if (this.guestID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Reservation.setRoomCost: guestID is null");
            throw new Exception();
        }
        try {
            this.roomCost = CentralRoom.quoteRoom(roomID);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in Reservation.setRoomCost: " +
                    "Not able to process room cost");
            throw new Exception();
        }
    }

    public String getReservationID() { return this.reservationID; }
    public String getGuestID() { return this.guestID; }
    public String getRoomID() { return this.roomID; }
    public Date getStartDate() { return this.startDate; }
    public Date getEndDate() { return this.endDate; }
    public BigDecimal getRoomCost() { return this.roomCost; }

    public String toString() {
        return CentralRoomID.extractRoomNumber(roomID) + "," + this.guestID + "," + this.startDate + "," + this.endDate;
    }
}
