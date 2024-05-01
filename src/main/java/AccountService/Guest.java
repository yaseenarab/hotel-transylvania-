package AccountService;

import Utilities.MyLogger;
import Room.Reservation;
import ShoppingService.Cart;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Guest extends Person {
    private String guestID;
    private Cart cart;
    private Card card;
    private Map<String, Reservation> reservations = new HashMap<>();
    public Guest(String guestID, String firstName, String lastName, String email, String phoneNumber, String username, String password) throws Exception {
        super(firstName, lastName, email, phoneNumber, username, password);
        try {
            this.setGuestID(guestID);
            //this.setCart(cart);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in Guest constructor: Passed values were " +
                    guestID + "," + firstName + "," + lastName + "," + email + "," + phoneNumber + "," +
                    username + "," + password);
            throw new Exception();
        }
    }
    private void setGuestID(String guestID) throws Exception {
        if(guestID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Guest.setGuestID: guestID is null");
            throw new Exception();
        }
        else if(guestID.length() != Arlow.ID_LENGTH ) {
            MyLogger.logger.log(Level.SEVERE, "Error in Guest.setGuestID: guestID should be " +
                    Arlow.ID_LENGTH + " characters, found " + guestID.length() + ", received " + guestID);
            throw new Exception();
        }
        this.guestID = guestID;
    }
    private void setCard(Card card) throws Exception {
        if(card == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Guest.setCard: card is null");
            throw new Exception();
        }
        this.card = card;
    }
    public void setCart(Cart cart) {
        //if(cart == null) {
        //    MyLogger.logger.log(Level.SEVERE, "Error in Guest.setCart: cart is null");
        //    throw new Exception();
        //}
        this.cart = cart;
    }
    public void setReservations(Map<String, Reservation> reservations) throws Exception {
        if(reservations == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Guest.setReservations: reservations is null");
            throw new Exception();
        }
        this.reservations = reservations;
    }
    public String getGuestID() {
        return this.guestID;
    }
    public Map<String,Reservation> getReservations() {
        return this.reservations;
    }
    public Cart getCart() { return this.cart; }
    public Card getCard() { return this.card; }

    public String toString() {
        return this.guestID + "," + super.toString();
    }
}
