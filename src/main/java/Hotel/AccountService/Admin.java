package Hotel.AccountService;

/**
 * Represents an administrative user with extended privileges in the hotel management system.
 * This class extends the {@link Employee} class, inheriting its basic user properties and adding
 * administrative-specific functionalities.
 */
public class Admin extends Employee {

    /**
     * Constructs a new Admin with specified details.
     * This constructor initializes a new Admin with attributes such as employee ID, first name,
     * last name, email, phone number, username, and password by calling the superclass constructor.
     *
     * @param employeeID   The unique identifier for this admin.
     * @param firstName    The first name of the admin.
     * @param lastName     The last name of the admin.
     * @param email        The email address of the admin.
     * @param phoneNumber  The phone number of the admin.
     * @param username     The username for the admin's account.
     * @param password     The password for the admin's account.
     * @throws Exception   If an error occurs during the creation process, such as validation failure.
     */

    public Admin(String employeeID, String firstName, String lastName, String email, String phoneNumber, String username, String password)
            throws Exception {
        super(employeeID, firstName, lastName, email, phoneNumber, username, password);
    }

}