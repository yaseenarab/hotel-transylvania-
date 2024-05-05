package Hotel.AccountService;

import Hotel.Utilities.MyLogger;

import java.util.logging.Level;
/**
 * Represents a person within the hotel management system.
 * This class serves as a base class for different types of persons such as guests or employees,
 * providing common properties and validation mechanisms for personal and contact information.
 */
public class Person {
    
    // Constants (inclusive)
    public static final Integer
            NAME_MAX_LENGTH = 16,
            USERNAME_MAX_LENGTH = 16, USERNAME_MIN_LENGTH = 1,
            PASSWORD_MAX_LENGTH = 16, PASSWORD_MIN_LENGTH = 1,
            PHONE_LENGTH = 10;

    private String
            firstName,
            lastName,
            email,
            phoneNumber,
            username,
            password;
    /**
     * Constructs a new Person with specified personal and contact information.
     *
     * @param firstName   The first name of the person.
     * @param lastName    The last name of the person.
     * @param email       The email address of the person.
     * @param phoneNumber The phone number of the person.
     * @param username    The username for the person's account.
     * @param password    The password for the person's account.
     * @throws Exception If any provided attribute fails validation checks.
     */
    protected Person (String firstName, String lastName, String email,
            String phoneNumber, String username, String password) throws Exception {
        try {
            this.setFirstName(firstName);
            this.setLastName(lastName);
            this.setEmail(email);
            this.setPhoneNumber(phoneNumber);
            this.setUsername(username);
            this.setPassword(password);
        }
        catch (Exception e) {
            e.printStackTrace();
            MyLogger.logger.log(Level.SEVERE, "Error caught in Person constructor: Passed values were " + firstName + "," +
                    lastName + "," + email + "," + phoneNumber + "," + username + "," + password);
            throw new Exception();
        }
    }
    /**
     * Validates the complete set of personal and contact information for a person.
     * Checks include validation of name, email, phone number, username, and password.
     *
     * @param firstName   The first name to validate.
     * @param lastName    The last name to validate.
     * @param email       The email to validate.
     * @param phoneNumber The phone number to validate.
     * @param username    The username to validate.
     * @param password    The password to validate.
     * @return true if all inputs are valid, otherwise throws an exception.
     * @throws Exception If any validation fails.
     */

    public static boolean isValidPerson(String firstName, String lastName, String email, String phoneNumber,
                                        String username, String password) throws Exception {
        if (firstName == null) {
            throw new Exception("firstName is null");
        } else if (firstName.isEmpty()) {
            throw new Exception("firstName is empty");
        } else if (firstName.length() > NAME_MAX_LENGTH) {
            throw new Exception("firstName should be no more than" + NAME_MAX_LENGTH +
                    " characters, found " + firstName.length());
        } else if (lastName == null) {
            throw new Exception("lastName is null");
        } else if (lastName.isEmpty()) {
            throw new Exception("lastName is empty");
        } else if (lastName.length() > NAME_MAX_LENGTH) {
            throw new Exception("Error in Person.setLastName: lastName should be no more than " +
                    NAME_MAX_LENGTH + " characters, found " + lastName.length());
        } else if (email == null) {
            throw new Exception("email is null");
        } else if (email.isEmpty()) {
            throw new Exception("email is empty");
        } else if (!email.contains("@")) {
            throw new Exception("email is missing @");
        } else if (!email.contains(".com") && !email.contains(".net") &&
                !email.contains(".org") && !email.contains(".edu") &&
                !email.contains(".gov")) {
            throw new Exception("email is missing TopLevelDomain(.com, .org, etc)");
        } else if (phoneNumber == null) {
            throw new Exception("phoneNumber is null");
        } else if (phoneNumber.isEmpty()) {
            throw new Exception("phoneNumber is empty");
        } else if (phoneNumber.length() != PHONE_LENGTH) {
            throw new Exception("should be exactly " + PHONE_LENGTH + " characters, found "
                    + phoneNumber.length());
        } else if(!validPhoneNumber(phoneNumber)) {
            throw new Exception("phoneNumber is not numeric");
        } else if(username == null) {
            throw new Exception("username is null");
        } else if(username.isEmpty()) {
            throw new Exception("username is empty");
        } else if(username.contains(" ")) {
            throw new Exception("username contains whitespace");
        } else if(username.length() < USERNAME_MIN_LENGTH) {
            throw new Exception("username should be at least " +
                    USERNAME_MIN_LENGTH + " characters, found " + username.length());
        } else if (username.length() > USERNAME_MAX_LENGTH) {
            throw new Exception("username should be no more than " +
                    USERNAME_MAX_LENGTH + " characters, found " + username.length());
        } else if (password == null) {
            throw new Exception("password is null");
        } else if(password.isEmpty()) {
            throw new Exception("password is empty");
        } else if(password.contains(" ")) {
            throw new Exception("password contains whitespace");
        } else if(password.length() < PASSWORD_MIN_LENGTH) {
            throw new Exception("password should be at least " +
                    PASSWORD_MIN_LENGTH + " characters, found " + password.length());
        } else if (password.length() > PASSWORD_MAX_LENGTH) {
            throw new Exception("password should be no more than " +
                    PASSWORD_MAX_LENGTH + " characters, found " + password.length());
        }
        return true;
    }


    /**
     * Sets the first name of the person after validating it against specific criteria.
     *
     * @param firstName The first name to set.
     * @throws Exception If the first name is null, empty, or exceeds the maximum length.
     */
    protected void setFirstName(String firstName) throws Exception {
        if(firstName == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setFirstName: firstName is null");
            throw new Exception();
        }
        else if (firstName.isEmpty()) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setFirstName: firstName is empty");
            throw new Exception();
        }
        else if (firstName.length() > NAME_MAX_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setFirstName: firstName should be no more than " +
                    NAME_MAX_LENGTH + " characters, found " + firstName.length() + ", received " + firstName);
            throw new Exception();
        }
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the person after validating it against specific criteria.
     *
     * @param lastName The first name to set.
     * @throws Exception If the first name is null, empty, or exceeds the maximum length.
     */
    protected void setLastName (String lastName) throws Exception {
        if(lastName == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setLastName: firstName is null");
            throw new Exception();
        }
        else if (lastName.isEmpty()) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setLastName: firstName is empty");
            throw new Exception();
        }
        else if (lastName.length() > NAME_MAX_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setLastName: lastName should be no more than " +
                    NAME_MAX_LENGTH + " characters, found " + lastName.length() + ", received " + lastName);
            throw new Exception();
        }
        this.lastName = lastName;
    }
    /**
     * Sets the email of the person after validating it against specific criteria.
     *
     * @param email The first name to set.
     * @throws Exception If the first name is null, empty, or exceeds the maximum length.
     */
    protected void setEmail (String email) throws Exception {
        if(email == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setEmail: email is null");
            throw new Exception();
        }
        else if (email.isEmpty()) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setEmail: email is empty");
            throw new Exception();
        }
        else if (!email.contains("@")) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setEmail: email is missing @, received " + email);
            throw new Exception();
        }
        else if (!email.contains(".com") && !email.contains(".net") &&
                !email.contains(".org") && !email.contains(".edu") &&
                !email.contains(".gov")) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setEmail: email is missing " +
                    "TopLevelDomain(.com, .org, etc), received " + email);
            throw new Exception();
        }
        this.email = email;
    }
    /**
     * Sets the phone number of the person after validating it against specific criteria.
     *
     * @param phoneNumber The first name to set.
     * @throws Exception If the first name is null, empty, or exceeds the maximum length.
     */
    protected void setPhoneNumber(String phoneNumber) throws Exception {
        if(phoneNumber == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPhoneNumber: phoneNumber is null");
            throw new Exception();
        }
        else if (phoneNumber.isEmpty()) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPhoneNumber: phoneNumber is empty");
            throw new Exception();
        }
        else if (phoneNumber.length() != PHONE_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPhoneNumber: phoneNumber should be exactly " +
                    PHONE_LENGTH + " characters, found " + phoneNumber.length() + ", received " + phoneNumber);
            throw new Exception();
        }
        else if(!validPhoneNumber(phoneNumber)) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPhoneNumber: phoneNumber is not numeric, " +
                    "received " + phoneNumber);
            throw new Exception();
        }
        this.phoneNumber = phoneNumber;
    }
    /**
     * Sets the username of the person after validating it against specific criteria.
     *
     * @param username The first name to set.
     * @throws Exception If the first name is null, empty, or exceeds the maximum length.
     */
    protected void setUsername(String username) throws Exception {
        if(username == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setUsername: username is null");
            throw new Exception();
        }
        else if(username.isEmpty()) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setUsername: username is empty");
            throw new Exception();
        }
        else if(username.contains(" ")) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setUsername: username contains whitespace, received " + username);
            throw new Exception();
        }
        else if(username.length() < USERNAME_MIN_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setUsername: username should be at least " +
                    USERNAME_MIN_LENGTH + " characters, found " + username.length() + ", received " + username);
            throw new Exception();
        }
        else if (username.length() > USERNAME_MAX_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setUsername: username should be no more than " +
                    USERNAME_MAX_LENGTH + " characters, found " + username.length() + ", received " + username);
            throw new Exception();
        }
        this.username = username;
    }

    /**
     * Sets the password  of the person after validating it against specific criteria.
     *
     * @param password The first name to set.
     * @throws Exception If the first name is null, empty, or exceeds the maximum length.
     */
    protected void setPassword(String password) throws Exception {
        if(password == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPassword: password is null");
            throw new Exception();
        }
        else if(password.isEmpty()) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPassword: password is empty");
            throw new Exception();
        }
        else if(password.contains(" ")) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPassword: password contains whitespace, received " + password);
            throw new Exception();
        }
        else if(password.length() < PASSWORD_MIN_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPassword: password should be at least " +
                    PASSWORD_MIN_LENGTH + " characters, found " + password.length() + ", received " + password);
            throw new Exception();
        }
        else if (password.length() > PASSWORD_MAX_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Person.setPassword: password should be no more than " +
                    PASSWORD_MAX_LENGTH + " characters, found " + password.length() + ", received " + password);
            throw new Exception();
        }
        this.password = password;
    }

    // Get Methods

    // Additional setters follow a similar pattern, validating and setting each attribute.
    // These methods include setLastName, setEmail, setPhoneNumber, setUsername, and setPassword.

    // Getters for each attribute.
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
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }


    /**
     * Validates if the provided phone number is numeric and meets required length.
     *
     * @param phoneNumber The phone number to validate.
     * @return true if the phone number is valid, otherwise false.
     */
    private static boolean validPhoneNumber(String phoneNumber) {
        for(int i = 0; i < PHONE_LENGTH; ++i) {
            if(!Character.isDigit(phoneNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * Compares this person with another object for equality based on personal and contact details.
     *
     * @param obj The object to compare with.
     * @return true if the other object is a Person with the same details, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Guest)) {
            return false;
        }
        return this.firstName.equals(((Person) obj).firstName) &&
                this.lastName.equals(((Person) obj).lastName) &&
                this.email.equals(((Person) obj).email) &&
                this.phoneNumber.equals(((Person) obj).phoneNumber);
    }

    /**
     * Provides a string representation of this person, including all personal and contact details.
     *
     * @return A string that lists all personal and contact details.
     */
    public String toString() {
        return this.firstName + "," + this.lastName + "," + this.email + "," + this.phoneNumber + "," +
                this.username + "," + this.password;
    }
}
