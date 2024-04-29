package Hotel.Room;

import Hotel.Central.CentralRoom;

import java.math.BigDecimal;
import java.util.Date;

public class Reservation {

    private final Integer MIL_TO_DAYS = 86400000;
    private final int RES_ID_LENGTH = 14, GUEST_ID_LENGTH = 14, ROOM_ID_LENGTH = 7;
    private String reservationID, guestUsername;
    private Integer roomID;
    private Date startDate, endDate;
    private BigDecimal roomCost;

    public Reservation(String reservationID, String guestID, Integer roomID,
                       Date startDate, Date endDate) throws Exception {
        this.setReservationID(reservationID);
        this.setGuestID(guestID);
        this.setRoomID(roomID);
        this.setStayLength(startDate, endDate);
        this.setRoomCost();
    }

    private void setReservationID(String reservationID) {
        assert(reservationID != null);
        assert(reservationID.length() == RES_ID_LENGTH);

        this.reservationID = reservationID;
    }
    private void setGuestID(String guestID) {
        assert(guestID != null);
        assert(guestID.length() == GUEST_ID_LENGTH);

        this.guestUsername = guestID;
    }
    private void setRoomID(Integer roomID) {
        assert(roomID != null);
        assert(RoomID.checkValidRoomID(roomID));

        this.roomID = roomID;
    }
    private void setStayLength(Date startDate, Date endDate) throws Exception{
        Date todayDate = new Date();
        if(startDate.after(endDate) || startDate.getTime() - endDate.getTime() == 0)
            throw new Exception("Invalid date");

        this.startDate = startDate;
        this.endDate = endDate;
    }
    private void setRoomCost() throws Exception {
        assert (this.startDate != null);
        assert (this.endDate != null);
        assert (this.roomID != null);
        assert (this.guestUsername != null);

        this.roomCost = new BigDecimal(((this.endDate.getTime() - this.startDate.getTime()) / MIL_TO_DAYS) * CentralRoom.quoteRoom(roomID, guestUsername));
    }


    public static void makeReservation(){


    }

    public String getReservationID() { return this.reservationID; }
    public String getGuestUsername() { return this.guestUsername; }
    public Integer getRoomID() { return this.roomID; }
    public Date getStartDate() { return this.startDate; }
    public Date getEndDate() { return this.endDate; }
    public BigDecimal getRoomCost() { return this.roomCost; }


}
