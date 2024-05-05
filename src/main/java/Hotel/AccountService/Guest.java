package Hotel.AccountService;

import Hotel.Utilities.MyLogger;
import Hotel.Room.Reservation;
import Hotel.ShoppingService.Cart;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Represents a guest in the hotel management system.
 * This class extends the {@link Person} class and includes specific properties and functionalities
 * for a hotel guest, such as handling reservations and managing a shopping cart.
 */
public class Guest extends Person {
    private String guestID;
    private Map<String, Reservation> reservations = new HashMap<>();

    private Cart cart;

    /**
     * Constructs a new Guest with specified identity and contact details.
     * Initializes a new shopping cart and sets the guest ID, validating its format.
     *
     * @param guestID     The unique identifier for the guest, which must meet specified length requirements.
     * @param firstName   The first name of the guest.
     * @param lastName    The last name of the guest.
     * @param email       The email address of the guest.
     * @param phoneNumber The phone number of the guest.
     * @param username    The username for the guest's account.
     * @param password    The password for the guest's account.
     * @throws Exception If any of the parameters are invalid, including an incorrect guest ID format.
     */
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

    /**
     * Sets the guest ID after validating its correctness.
     *
     * @param guestID The guest ID to set; must match a specific length defined in {@link Arlow#GUEST_ID_LENGTH}.
     * @throws Exception if the guest ID is null or does not meet the length requirement.
     */
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

    /**
     * Retrieves the current map of reservations held by the guest.
     *
     * @return A map containing the guest's reservations, keyed by reservation IDs.
     */
    public Map<String,Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the reservations for the guest.
     *
     * @param reservations A map of reservations to be set for the guest.
     */
    public void setReservations(Map<String, Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a reservation to the guest's list of reservations.
     *
     * @param r The reservation to add.
     */
    public void addReservation(Reservation r){
        reservations.put(r.getReservationID(),r);
    }

    /**
     * Sets a new shopping cart for the guest.
     *
     * @param c The shopping cart to set.
     */
    public void setCart(Cart c) { cart = c; }
    /**
     * Retrieves the shopping cart associated with the guest.
     *
     * @return The shopping cart of the guest.
     */
    public Cart getCart() { return cart; }

    /**
     * Retrieves the guest ID.
     *
     * @return the guest ID as a string.
     */
    public String getGuestID() {
        return this.guestID;
    }

    /**
     * Provides a string representation of this Guest including the guest ID and the personal information from the superclass.
     *
     * @return a string describing the guest, formatted as guestID followed by other personal details.
     */
    public String toString() {
        return this.guestID + "," + super.toString();
    }
}
