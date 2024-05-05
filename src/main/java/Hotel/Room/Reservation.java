package Hotel.Room;

import java.math.BigDecimal;
import java.util.Date;



/**
 * Represents a reservation in the hotel system, encapsulating all details necessary
 * for managing a reservation including the reservation ID, guest details, room assignment,
 * and dates of the stay.
 */
public class Reservation {

    private final Integer MIL_TO_DAYS = 86400000;
    private final int RES_ID_LENGTH = 14, GUEST_ID_LENGTH = 14, ROOM_ID_LENGTH = 7;

    private String reservationID, guestUsername;
    private Integer roomID;
    private Date startDate, endDate;
    private BigDecimal cost;


    /**
     * Constructs a Reservation with necessary details.
     *
     * @param reservationID Unique identifier for the reservation.
     * @param guestID       Identifier for the guest who made the reservation.
     * @param roomID        Identifier for the reserved room.
     * @param startDate     Start date of the reservation.
     * @param endDate       End date of the reservation.
     * @throws Exception if the dates are invalid or any required info is missing.
     */
    public Reservation(String reservationID, String guestID, Integer roomID,
                       Date startDate, Date endDate) throws Exception {
        this.setReservationID(reservationID);
        this.setGuestID(guestID);
        this.setRoomID(roomID);
        this.setStayLength(startDate, endDate);
    }


    /**
     * Sets the reservation ID after validating its correctness.
     *
     * @param reservationID the reservation ID to set.
     */
    private void setReservationID(String reservationID) {
        assert(reservationID != null);
        assert(reservationID.length() == RES_ID_LENGTH);

        this.reservationID = reservationID;
    }


    /**
     * Sets the guest ID after validating its correctness.
     *
     * @param guestID the guest ID to set.
     */
    private void setGuestID(String guestID) {
        assert(guestID != null);
        assert(guestID.length() == GUEST_ID_LENGTH);

        this.guestUsername = guestID;
    }

    /**
     * Sets the room ID for the reservation.
     *
     * @param roomID the room ID to set.
     */
    private void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }


    /**
     * Sets the start and end dates for the stay, ensuring the end date is after the start date.
     *
     * @param startDate the start date of the reservation.
     * @param endDate   the end date of the reservation.
     * @throws Exception if the end date is before the start date or they are the same day.
     */
    private void setStayLength(Date startDate, Date endDate) throws Exception{
        Date todayDate = new Date();
        if(startDate.after(endDate) || startDate.getTime() - endDate.getTime() == 0)
            throw new Exception("Invalid date");

        this.startDate = startDate;
        this.endDate = endDate;
    }



    // Getters
    public String getReservationID() { return this.reservationID; }
    public String getGuestUsername() { return this.guestUsername; }
    public Integer getRoomID() { return this.roomID; }
    public Date getStartDate() { return this.startDate; }
    public Date getEndDate() { return this.endDate; }
    public BigDecimal getCost() { return this.cost; }

    /**
     * Sets the cost of the reservation.
     *
     * @param cost the cost of the reservation.
     */
    public void setCost(double cost) {this.cost = new BigDecimal(""+cost); }
}
