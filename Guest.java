package ReservationSystem.Dependencies;

import java.util.ArrayList;
import java.util.List;

public class Guest extends Person {
    private String guestID;
    Guest(String guestID, String firstName, String lastName, String email, String phoneNumber, String username, String password) throws Exception {
        super(firstName, lastName, email, phoneNumber, username, password);

        this.guestID = guestID;
    }
    public Guest(String firstName, String lastName, String email, String phoneNumber, String username, String password) throws Exception {
        super(firstName, lastName, email, phoneNumber, username, password);
        setGuestID();
    }
    public void setGuestID() throws Exception{
        do { guestID = Arlow.generateGuestID(); }
        while(Person.idInFile(this));
    }
    public String getGuestID() {
        return this.guestID;
    }

    public String toString() {
        return this.guestID + "," + super.toString();
    }
}
