package org.example;

import java.util.Date;

public class GuestProfile  extends Reservations{
    // Constants (inclusive)
    final int
            NAME_MAX_LENGTH = 16,
            PHONE_LENGTH = 10,
            PASSWORD_LENGTH = 8;
    // Class Variables
    private String
            username,
            password;
    private String
            firstName,
            lastName,
            email,
            phoneNumber;
    private Room[] roomNights;

    Reservation reservation;
    GuestProfile(String firstName, String lastName, String email, String phoneNumber) throws Exception {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);

        // Online Account uninitialized
        this.username = null;
        this.password = null;
        this.reservation = null;
    }

    // Set Methods
    protected void setFirstName(String firstName) throws Exception {
        if (firstName == null || firstName.isEmpty()) {
            throw new NullPointerException("First name not provided");
        }
        if (firstName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("First name must be no more than " + NAME_MAX_LENGTH + " characters");
        }
        this.firstName = firstName;
    }
    protected void setLastName (String lastName) throws Exception {
        if (lastName == null || lastName.isEmpty()) {
            throw new NullPointerException("Last name not provided");
        }
        if (lastName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Last name must be no more than " + NAME_MAX_LENGTH + " characters");
        }
        this.lastName = lastName;
    }
    protected void setEmail (String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new NullPointerException("Email not provided");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email format: Username@DomainName.TopLevelDomain");
        }
        this.email = email;
    }
    protected void setPhoneNumber(String phoneNumber) throws Exception {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new NullPointerException("No phone number provided");
        }
        if ( phoneNumber.length() != PHONE_LENGTH) {
            throw new IllegalArgumentException("Phone number format: XXXXXXXXXX");
        }
        this.phoneNumber = phoneNumber;
    }


    public void reserveRoom(Integer [] roomNumbers, Date startDate, Date endDate) throws Exception {
        roomNights = new Room[roomNumbers.length];
        for(int i = 0 ; i < roomNumbers.length; i++){
            roomNights[i] = getRoom(roomNumbers[i]);

        }

        reservation = new Reservation(roomNights, startDate, endDate);

    }

    // Get Methods
    protected String getFirstName() {
        return this.firstName;
    }
    protected String getLastName() {
        return this.lastName;
    }
    protected String getEmail() {
        return this.email;
    }
    protected String getPhoneNumber() {
        return this.phoneNumber;
    }

    // Modifier Methods
    protected void MakeOnlineAccount(String username, String password) throws Exception {
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

    public String getUsername() {
        return this.username;
    }
    public String getPassword(){
        return this.password;

    }
}
