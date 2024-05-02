package AccountService;

import Utilities.MyLogger;

import java.util.logging.Level;

public class Admin extends Person {
    private String adminID;

    public Admin(String adminID, String firstName, String lastName, String email, String phoneNumber, String username, String password)
            throws Exception {
        super(firstName, lastName, email, phoneNumber, username, password);
        this.setAdminID(adminID);
    }
    public void setAdminID(String adminID) throws Exception {
        if(adminID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Employee.setEmployeeID: employeeID is null");
            throw new Exception();
        }
        else if(adminID.length() != Arlow.ID_LENGTH ) {
            MyLogger.logger.log(Level.SEVERE, "Error in Employee.setEmployeeID: employeeID should be " +
                    Arlow.ID_LENGTH + " characters, found " + adminID.length() + ", received " + adminID);
            throw new Exception();
        }
        this.adminID = adminID;
    }
    public String getAdminID() {
        return this.adminID;
    }
    public String toString() {
        return adminID.toString() + "," + super.toString();
    }

}