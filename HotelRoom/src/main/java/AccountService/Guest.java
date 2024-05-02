package AccountService;

import LoggerPackage.MyLogger;
import Room.Reservation;
import ShoppingService.Cart;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Guest extends Person {
    private String guestID;
    private Map<String, Reservation> reservations = new HashMap<>();

    private Cart cart;

    public Guest(String guestID, String firstName, String lastName, String email, String phoneNumber, String username, String password) throws Exception {
        super(firstName, lastName, email, phoneNumber, username, password);
        try {
            this.setGuestID(guestID);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in Guest constructor: Passed values were " +
                    guestID + "," + firstName + "," + lastName + "," + email + "," + phoneNumber + "," +
                    username + "," + password);
            throw new Exception();
        }
        // Read from database?
        cart = new Cart();
    }
    private void setGuestID(String guestID) throws Exception {
        if(guestID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Guest.setGuestID: guestID is null");
            throw new Exception();
        }
        else if(guestID.length() != Arlow.GUEST_ID_LENGTH ) {
            MyLogger.logger.log(Level.SEVERE, "Error in Guest.setGuestID: guestID should be " +
                    Arlow.GUEST_ID_LENGTH + " characters, found " + guestID.length() + ", received " + guestID);
            throw new Exception();
        }
        this.guestID = guestID;
        // Read from database?
        cart = new Cart();
    }

    public Map<String,Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Map<String, Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation r){
        reservations.put(r.getReservationID(),r);
    }
    public void setCart(Cart c) { cart = c; }

    public Cart getCart() { return cart; }
    public String getGuestID() {
        return this.guestID;
    }
    public String toString() {
        return this.guestID + "," + super.toString();
    }
}
