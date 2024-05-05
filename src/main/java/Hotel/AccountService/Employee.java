package Hotel.AccountService;

import Hotel.Utilities.MyLogger;

import java.util.logging.Level;


/**
 * Represents an employee within the hotel management system.
 * This class extends the {@link Person} class and includes additional properties
 * specific to employees, such as an employee ID.
 */
public class Employee extends Person {
    protected  String employeeID;

    /**
     * Constructs a new Employee with specified details including an employee ID, personal information,
     * and account credentials. It ensures that the employee ID is valid.
     *
     * @param employeeID   The unique identifier for the employee.
     * @param firstName    The first name of the employee.
     * @param lastName     The last name of the employee.
     * @param email        The email address of the employee.
     * @param phoneNumber  The phone number of the employee.
     * @param username     The username for the employee's account.
     * @param password     The password for the employee's account.
     * @throws Exception   If any of the parameters are invalid, particularly the employee ID.
     */
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


    /**
     * Sets the employee ID after validating its correctness.
     *
     * @param employeeID The employee ID to set; must match a specific length defined in {@link Arlow#EMPLOYEE_ID_LENGTH}.
     * @throws Exception if the employee ID is null or does not meet the length requirement.
     */
    public void setEmployeeID(String employeeID) throws Exception {
        if(employeeID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Employee.setEmployeeID: employeeID is null");
            throw new Exception();
        }
        else if(employeeID.length() != Arlow.EMPLOYEE_ID_LENGTH ) {
            MyLogger.logger.log(Level.SEVERE, "Error in Employee.setEmployeeID: employeeID should be " +
                    Arlow.EMPLOYEE_ID_LENGTH + " characters, found " + employeeID.length() + ", received " + employeeID);
            throw new Exception();
        }
        this.employeeID = employeeID;
    }


    /**
     * Returns the employee ID of this employee.
     *
     * @return the employee ID as a string.
     */
    public String getEmployeeID() {
        return this.employeeID;
    }

    /**
     * Provides a string representation of this Employee including the employee ID
     * and the personal information from the superclass.
     *
     * @return a string describing the employee, formatted as employeeID followed by other personal details.
     */
    public String toString() {
        return this.employeeID + "," + super.toString();
    }
}
