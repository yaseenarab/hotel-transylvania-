package Hotel.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Represents a guest profile in the hotel management system.
 * This class contains personal and contact details of a guest, as well as their reservation history
 * and account information necessary for online interactions.
 */

public class GuestProfile  {
    // Constants (inclusive)
    final int
            NAME_MAX_LENGTH = 16,
            PHONE_LENGTH = 10,
            PASSWORD_LENGTH = 8, START_RAND = 0, END_RAND = 25;
    // Class Variables
    private String
            username,
            password;
    private String
            firstName,
            lastName,
            email,
            phoneNumber;
    private Integer userID;
    private Card card;

    private List<Integer> roomNights;

    /**
     * Constructs a new GuestProfile with specified personal and contact details and a payment card.
     * Initializes the list of room nights and sets initial values for user identification.
     *
     * @param firstName   The guest's first name.
     * @param lastName    The guest's last name.
     * @param email       The guest's email address.
     * @param phoneNumber The guest's phone number.
     * @param card        The guest's payment card information.
     * @throws Exception If any input is invalid or missing.
     */
    GuestProfile(String firstName, String lastName, String email, String phoneNumber, Card card) throws Exception {
        roomNights = new ArrayList<>();

        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setCard(card);
        this.setUserID();

        // Online Account uninitialized
        this.username = null;
        this.password = null;
    }

    // Modifier Methods

    /**
     * Creates an online account for the guest with a username and password.
     *
     * @param username The username for the online account.
     * @param password The password for the online account.
     * @throws Exception If the username or password are invalid, or if an account already exists.
     */
    public void MakeOnlineAccount(String username, String password) throws Exception {
        if(this.username != null || this.password != null) {
            throw new IllegalAccessException();
        }
        if(username == null || username.isEmpty()) {
            throw new NullPointerException("No username provided");
        }
        if(password == null || password.isEmpty()) {
            throw new NullPointerException("No password provided");
        }
        if(password.length() > PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be no more than " + PASSWORD_LENGTH + " characters");
        }
        this.username = username;
        this.password = password;
    }

    /**
     * Sets the guest's first name after validation.
     *
     * @param firstName The first name to set.
     * @throws Exception If the first name is null, empty, or too long.
     */
    public void setFirstName(String firstName) throws Exception {
        if (firstName == null || firstName.isEmpty()) {
            throw new NullPointerException("First name not provided");
        }
        if (firstName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("First name must be no more than " + NAME_MAX_LENGTH + " characters");
        }
        this.firstName = firstName;
    }

    /**
     * Sets the guest's last name after validation.
     *
     * @param lastName The last name to set.
     * @throws Exception If the last name is null, empty, or too long.
     */
    public void setLastName (String lastName) throws Exception {
        if (lastName == null || lastName.isEmpty()) {
            throw new NullPointerException("Last name not provided");
        }
        if (lastName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Last name must be no more than " + NAME_MAX_LENGTH + " characters");
        }
        this.lastName = lastName;
    }


    /**
     * Sets the guest's email after validation.
     *
     * @param email The email address to set.
     * @throws Exception If the email is null, empty, or does not contain an '@' symbol.
     */
    public void setEmail (String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new NullPointerException("Email not provided");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email format: Username@DomainName.TopLevelDomain");
        }
        this.email = email;
    }


    /**
     * Sets the guest's phone number after validation.
     *
     * @param phoneNumber The phone number to set.
     * @throws Exception If the phone number is null, empty, or not the correct length.
     */
    public void setPhoneNumber(String phoneNumber) throws Exception {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new NullPointerException("No phone number provided");
        }
        if ( phoneNumber.length() != PHONE_LENGTH) {
            throw new IllegalArgumentException("Phone number format: XXXXXXXXXX");
        }
        this.phoneNumber = phoneNumber;
    }


    /**
     * Sets the payment card for the guest profile.
     *
     * @param card The card to associate with the guest profile.
     */
    public void setCard(Card card) {
        this.card = card;
    }



    /**
     * Generates a unique user ID for the guest using a hash code and random number.
     */
    public void setUserID() {
        Random rand= new Random(System.currentTimeMillis());
        this.userID = this.hashCode() * (rand.nextInt((END_RAND - START_RAND) + 1) + START_RAND);
    }


    /**
     * Compares this GuestProfile to another object for equality.
     *
     * @param obj The object to compare against.
     * @return true if the other object is a GuestProfile with the same username, password, and personal details.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuestProfile)) {
            return false;
        }
        return this.username.equals(((GuestProfile) obj).username) &&
                this.password.equals(((GuestProfile) obj).password) &&
                this.firstName.equals(((GuestProfile) obj).firstName) &&
                this.lastName.equals(((GuestProfile) obj).lastName) &&
                this.email.equals(((GuestProfile) obj).email) &&
                this.phoneNumber.equals(((GuestProfile) obj).phoneNumber);
    }


    // Accessor methods for guest profile attributes.
    // Get Methods
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public Integer getUserID() { return this.userID; }
    public Card getCard() { return card; }
    public List<Integer> getRoomNights() {
        return this.roomNights;
    }

    // Modifiers
    /**
     * Adds a reservation ID to the list of room nights for the guest.
     *
     * @param reservationID The reservation ID to add.
     */
    public void addReservation(Integer reservationID) {
        roomNights.add(reservationID);
    }
}
