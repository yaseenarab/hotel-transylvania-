package ReservationSystem.Dependencies;

import java.util.Date;
interface IRoomReservation {
    void setGuest(Guest guest) throws Exception;
    void setRoom(Room room) throws Exception;
    void setStartDate(Date startDate) throws Exception;
    void setEndDate(Date endDate) throws Exception;

    Guest getGuest();
    Room getRoom();
    Date getStartDate();
    Date getEndDate();
}

public class RoomReservation  {
    // Private Variables
    private Guest guest;
    private Room room;
    private Date startDate, endDate;
    RoomReservation(Guest guest, Room room, Date startDate, Date endDate) throws Exception {
        try {
            this.setGuest(guest);
            this.setRoom(room);
            this.setStartDate(startDate);
            this.setEndDate(endDate);
            System.out.println("Successfully Initialized RoomReservation");
        }
        catch(Exception e) {
            throw new Exception("Exception caught in RoomReservation constructor", e);
        }
    }

    // Set Function               s
    void setGuest(Guest guest) throws Exception {
        if(guest == null) {
            throw new Exception("Fatal error thrown in setGuest, value is NULL");
        }
        try {
            this.guest = guest;
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in setGuest", e);
        }
    }
    void setRoom(Room room) throws Exception {
        if(room == null) {
            throw new Exception("Fatal error thrown in setRoom, value is NULL");
        }
        try {
            this.room = room;
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in setRoom", e);
        }
    }
    void setStartDate(Date startDate) throws Exception {
        if(startDate == null) {
            throw new Exception("Fatal error thrown in setStartDate, value is NULL");
        }
        try {
            this.startDate = startDate;
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in setStartDate", e);
        }
    }
    void setEndDate(Date endDate) throws Exception {
        if(endDate == null) {
            throw new Exception("Fatal error thrown in setEndDate, value is NULL");
        }
        try {
            this.endDate = endDate;
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in setEndDate", e);
        }
    }

    // Get Functions
    Guest getGuest() {
        return this.guest;
    }
    Room getRoom() {
        return this.room;
    }
    Date getStartDate() {
        return this.startDate;
    }
    Date getEndDate() {
        return this.endDate;
    }
}