package Hotel.ReservationService;

import Hotel.AccountService.Guest;
import Hotel.RoomService.Room;
import Hotel.RoomService.RoomQuality;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RoomReservation  {
    // Private Variables
    private Guest guest;
    private String resID;
    private Room room;
    private Date startDate, endDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public RoomReservation(Guest guest, RoomQuality room, String startDate, String endDate) throws Exception {
        try {
            this.setResID();
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
    public void setResID() {
        this.resID = Reservations.Marsha.generateReservationID();
    }
    public String getResID() {
        return this.resID;
    }
    public void setGuest(Guest guest) throws Exception {
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
    public void setRoom(Room room) throws Exception {
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
    public void setStartDate(String startDate) throws Exception {
        if(startDate == null) {
            throw new Exception("Fatal error thrown in setStartDate, value is NULL");
        }
        try {
            this.startDate = sdf.parse(startDate);
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in setStartDate", e);
        }
    }
    public void setEndDate(String endDate) throws Exception {
        if(endDate == null) {
            throw new Exception("Fatal error thrown in setEndDate, value is NULL");
        }
        try {
            this.endDate = sdf.parse(endDate);
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in setEndDate", e);
        }
    }
    public String csvOutput() {
        String str = "";
        str = str + room.getRoomNumber() + "," + guest.getUsername() + "," + sdf.format(startDate) + "," + sdf.format(endDate) + "\n";
        return str;
    }

    // Get Functions
    Guest getGuest() {
        return this.guest;
    }
    public Room getRoom() {
        return this.room;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }
}