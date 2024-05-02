package AccountService;

import Utilities.MyLogger;

import java.util.logging.Level;

public class Employee extends Person {
    private String employeeID;
    public Employee(String employeeID, String firstName, String lastName, String email, String phoneNumber, String username, String password) throws Exception {
        super(firstName, lastName, email, phoneNumber, username, password);
        try {
            this.setEmployeeID(employeeID);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in Employee constructor: Passed values were " +
                    employeeID + "," + firstName + "," + lastName + "," + email + "," + phoneNumber + "," +
                    username + "," + password);
            throw new Exception();
        }
    }
    private void setEmployeeID(String employeeID) throws Exception {
        if(employeeID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Employee.setEmployeeID: employeeID is null");
            throw new Exception();
        }
        else if(employeeID.length() != Arlow.ID_LENGTH ) {
            MyLogger.logger.log(Level.SEVERE, "Error in Employee.setEmployeeID: employeeID should be " +
                    Arlow.ID_LENGTH + " characters, found " + employeeID.length() + ", received " + employeeID);
            throw new Exception();
        }
        this.employeeID = employeeID;
    }
    public String getEmployeeID() {
        return this.employeeID;
    }
    public String toString() {
        return this.employeeID + "," + super.toString();
    }
}
