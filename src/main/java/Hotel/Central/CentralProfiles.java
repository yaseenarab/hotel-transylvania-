package Hotel.Central;

import Hotel.AccountService.Admin;
import Hotel.AccountService.Arlow;
import Hotel.AccountService.Employee;
import Hotel.AccountService.Guest;
import Hotel.Utilities.MyLogger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;


/**
 * Manages the retrieval and management of profiles for guests, employees, and administrators
 * within the hotel management system. This class facilitates the interaction with the database
 * for operations related to user profiles.
 */
public class CentralProfiles {
    private static final Arlow ARLOW = new Arlow();

    /**
     * Retrieves a guest profile from the database based on username and password.
     *
     * @param username The username of the guest.
     * @param password The password of the guest.
     * @return Guest object if credentials match a record, null otherwise.
     */
    public static Guest getGuest(String username, String password) {

        Guest guest = null;
        try {

            Connection con = CentralDatabase.getConGuestProfileDataBase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM personprofiles WHERE Username = '" + username + "' And password = '" + password+"'");

            //ResultSet res = stmt.executeQuery("SELECT * FROM personprofiles WHERE Username = '\" + username + \"' And password = '\" + password + "'");

            if(res.next()){
                String guestId = res.getString("personid");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                String email = res.getString("email");
                String phonenumber = res.getString("phonenumber");
                guest = new Guest(guestId,firstName,lastName,email,phonenumber,username,password);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralProfiles.getGuest: ");
            return null;
        }
        return guest;
    }


    /**
     * Retrieves the guest ID associated with a username.
     *
     * @param username The username of the guest.
     * @return The ID of the guest if found, null otherwise.
     */
    public static String getGuestID(String username) {

        try {

            Connection con = CentralDatabase.getConGuestProfileDataBase();

            Statement stmt = con.createStatement();

            ResultSet res =  stmt.executeQuery("Select * from PERSONPROFILES where USERNAME = '" +username +"'" );

            if(res.next()){
                String guestId = res.getString("personid");
                return guestId;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralProfiles.getGuest: ");
            return null;
        }
        return null;
    }

    /**
     * Authenticates a user based on username, password, and type.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param type     The type of user ("Guest", "Employee", "Admin").
     * @return true if authentication is successful, false otherwise.
     */
    public static Boolean authenticateUser(String username, String password, String type) {

        Guest guest = null;
        String SQL = null;
        Connection con = null;
        try{
            if(type.equals("Guest")){
                con = CentralDatabase.getConGuestProfileDataBase();
                SQL = "Select * from PersonProfiles where Username = '" + username+"' And password = '" + password +"'";

            }
            else if(type.equals("Employee")){
                con = CentralDatabase.getConEmployeeProfileDataBase();

                SQL = "Select * from Employeeprofiles where Username = '" + username+"' And password = '" + password +"'";

            }
            else if(type.equals("Admin")){
                con = CentralDatabase.getConAdminProfileDataBase();
                SQL = "Select * from AdminProfiles where Username = '" + username+"' And password = '" + password +"'";

            }

            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);


            if(res.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Checks if a guest profile exists in the database.
     *
     * @param username The username of the guest.
     * @return true if the guest profile exists, false otherwise.
     */
    public static Boolean guestisIn(String username) {
        Guest guest = null;
        try{
            Connection con = CentralDatabase.getConGuestProfileDataBase();
            Statement stmt = con.createStatement();

            ResultSet res = stmt.executeQuery("Select * from PersonProfiles where Username = '" + username+"'");
            if(res.next()){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    /**
     * Checks if an employee profile exists in the database.
     *
     * @param username The username of the employee.
     * @return true if the employee profile exists, false otherwise.
     */
    public static Boolean EmployeeisIn(String username) {

        Guest guest = null;
        try{
            Connection con = CentralDatabase.getConEmployeeProfileDataBase();
            Statement stmt = con.createStatement();

            ResultSet res = stmt.executeQuery("Select * from Employeeprofiles where Username = '" + username+"'");
            if(res.next()){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    /**
     * Creates a new guest profile in the database.
     *
     * @param firstName    First name of the guest.
     * @param lastName     Last name of the guest.
     * @param email        Email of the guest.
     * @param phoneNumber  Phone number of the guest.
     * @param username     Username for the guest profile.
     * @param password     Password for the guest profile.
     * @return The ID of the new guest profile, or null if the creation fails.
     */
    public static String makeGuestProfile(String firstName, String lastName,
                                          String email, String phoneNumber,
                                          String username, String password ) {
        try {
            Connection con = CentralDatabase.getConGuestProfileDataBase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM personprofiles WHERE personid=(SELECT max(Personid) FROM Personprofiles)");

            Integer idNum;
            if(res.next()){
                String idStr = res.getString("PersonId");
                idNum = Integer.parseInt( idStr.substring(4,idStr.length()));
            }
            else{
                idNum = 1000000000;
            }

            String guestID = Arlow.generateGuestID(idNum);
            stmt.executeUpdate("INSERT INTO PersonProfiles(PersonId,firstname,lastname,email,Phonenumber,Username,password) values ('"+guestID + "','"+firstName + "','" +lastName + "','" + email + "','" + phoneNumber + "','" + username + "','" + password+ "')");


            return "guestID";
        }
        catch (Exception e) {
            e.printStackTrace();
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralProfiles.makeGuestProfile: " +
                    "Values passed were " + firstName + "," + lastName + "," + email + "," +
                    phoneNumber + "," + username + "," + password);
            return null;
        }
    }
    /**
     * Retrieves employee details from the database based on username and password.
     *
     * @param username The username of the employee.
     * @param password The password of the employee.
     * @return Employee object if found, null otherwise.
     */
    public static Employee getEmployee(String username, String password){
        Employee employee = null;
        try {
            Connection con = CentralDatabase.getConGuestProfileDataBase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM PersonProfiles WHERE personid=(SELECT max(Personid) FROM Personprofiles where personId LiKE '%TVEI%')");


            while(res.next()){

                String emmployeeId = res.getString("personid");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                String email = res.getString("email");
                String phonenumber = res.getString("phonenumber");

                employee = new Employee(emmployeeId,firstName,lastName,email,phonenumber,username,password);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employee;
    }
    /**
     * Retrieves administrator details from the database based on username and password.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @return Admin object if found, null otherwise.
     */
    public static Admin getAdmin(String username, String password){

        Admin admin = null;
        try {

            Connection con = CentralDatabase.getConAdminProfileDataBase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM AdminProfiles WHERE personid=(SELECT max(Personid) FROM adminProfiles where personId LiKE '%TVAI%')");


            while(res.next()){

                String adminId = res.getString("personid");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                String email = res.getString("email");
                String phonenumber = res.getString("phonenumber");

                admin = new Admin(adminId,firstName,lastName,email,phonenumber,username,password);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return admin;
    }
    /**
     * Creates a new employee profile in the database.
     *
     * @param firstName    First name of the employee.
     * @param lastName     Last name of the employee.
     * @param email        Email of the employee.
     * @param phoneNumber  Phone number of the employee.
     * @param username     Username for the employee profile.
     * @param password     Password for the employee profile.
     * @return The ID of the new employee profile, or null if the creation fails.
     */
    public static String makeEmployeeProfile(String firstName, String lastName,
                                             String email, String phoneNumber,
                                             String username, String password ) {
        try {

            Connection con = CentralDatabase.getConEmployeeProfileDataBase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM Employeeprofiles WHERE personid=(SELECT max(Personid) FROM Employeeprofiles)");

            Integer idNum;
            if(res.next()){
                String idStr = res.getString("PersonId");
                idNum = Integer.parseInt( idStr.substring(4,idStr.length()));
            }
            else{
                idNum = 1000000000;
            }

            String employeeId = Arlow.generateEmployeeID(idNum);
            stmt.executeUpdate("INSERT INTO Employeeprofiles(PersonId,firstname,lastname,email,Phonenumber,Username,password) values ('"+ employeeId + "','"+firstName + "','" +lastName + "','" + email + "','" + phoneNumber + "','" + username + "','" + password+ "')");

            return "employeeID";
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralProfiles.makeEmployeeProfile: " +
                    "Values passed were " + firstName + "," + lastName + "," + email + "," +
                    phoneNumber + "," + username + "," + password);
            return null;
        }
    }

    /**
     * Checks if an admin profile exists in the database.
     *
     * @param text The username of the admin.
     * @return true if the admin profile exists, false otherwise.
     */
    public static boolean AdminisIn(String text) {
        Admin admin = null;
        try{

            Connection con = CentralDatabase.getConAdminProfileDataBase();
            Statement stmt = con.createStatement();

            ResultSet res = stmt.executeQuery("Select * from AdminProfiles where Username = '" + text+"'");
            if(res.next()){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    /**
     * Creates a new admin profile in the database.
     *
     * @param firstName    First name of the admin.
     * @param lastName     Last name of the admin.
     * @param email        Email of the admin.
     * @param phoneNumber  Phone number of the admin.
     * @param username     Username for the admin profile.
     * @param password     Password for the admin profile.
     * @return The ID of the new admin profile, or null if the creation fails.
     */
    public static String makeAdminProfile(String firstName, String lastName, String email, String phoneNumber, String username, String password) {
        try {

            Connection con = CentralDatabase.getConAdminProfileDataBase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM AdminProfiles WHERE personid=(SELECT max(Personid) FROM adminProfiles)");

            Integer idNum;
            if(res.next()){
                String idStr = res.getString("PersonId");
                idNum = Integer.parseInt( idStr.substring(4,idStr.length()));
            }
            else{
                idNum = 1000000000;
            }

            String employeeId = Arlow.generateEmployeeID(idNum);
            stmt.executeUpdate("INSERT INTO AdminProfiles(PersonId,firstname,lastname,email,Phonenumber,Username,password) values ('"+ employeeId + "','"+firstName + "','" +lastName + "','" + email + "','" + phoneNumber + "','" + username + "','" + password+ "')");

            return "employeeID";
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralProfiles.makeEmployeeProfile: " +
                    "Values passed were " + firstName + "," + lastName + "," + email + "," +
                    phoneNumber + "," + username + "," + password);
            return null;
        }
    }
}
