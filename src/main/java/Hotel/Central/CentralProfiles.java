package Hotel.Central;

import Hotel.AccountService.Arlow;
import Hotel.AccountService.Employee;
import Hotel.AccountService.Guest;
import Hotel.DataBaseManipulation.DataGetSet;
//import Hotel.Databases.EmployeeDatabase;
//import Hotel.Databases.GuestDatabase;
import Hotel.LoggerPackage.MyLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class CentralProfiles {
    private static final Arlow ARLOW = new Arlow();



    public static Guest getGuest(String username, String password) {

        Guest guest = null;
        try {
            DataGetSet d = new DataGetSet();
            Connection con = d.getConGuestProfileDataBase();
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


    public static Boolean authenticateUser(String username, String password, String type) {

        Guest guest = null;
        String SQL = null;
        Connection con = null;
        try{
            DataGetSet d = new DataGetSet();

            if(type.equals("Guest")){
                con = d.getConGuestProfileDataBase();
                SQL = "Select * from PersonProfiles where Username = '" + username+"' And password = '" + password +"'";

            }
            else if(type.equals("Employee")){
                con = d.getConEmployeeProfileDataBase();
                SQL = "Select * from Employeeprofiles where Username = '" + username+"' And password = '" + password +"'";

            }
            else if(type.equals("Admin")){
                con = d.getConAdminProfileDataBase();
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
    public static Boolean guestisIn(String username, String password, String email, String phoneNumber) {

        Guest guest = null;
        try{
            DataGetSet d = new DataGetSet();
            Connection con = d.getConGuestProfileDataBase();
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

    public static Boolean EmployeeisIn(String username, String password, String email, String phoneNumber) {

        Guest guest = null;
        try{
            DataGetSet d = new DataGetSet();
            Connection con = d.getConEmployeeProfileDataBase();
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
    public static String makeGuestProfile(String firstName, String lastName,
                                          String email, String phoneNumber,
                                          String username, String password ) {
        try {
            DataGetSet d = new DataGetSet();
            Connection con = d.getConGuestProfileDataBase();
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



    public static Employee getEmployee(String username, String password){
        Employee employee = null;
        try {
            DataGetSet d = new DataGetSet();
            Connection con = d.getConGuestProfileDataBase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM personprofiles WHERE personid=(SELECT max(Personid) FROM Personprofiles where personId LiKE '%TVEI%')");


            while(res.next()){

                String emmployeeId = res.getString("personid");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                String email = res.getString("email");
                String phonenumber = res.getString("phonenumber");

                employee = new Employee(emmployeeId,firstName,lastName,email,phonenumber,username,password);

                System.out.println(res.getString("Personid"));

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employee;
    }
    public static void getAdmin(String username, String password){

        Employee employee = null;
        try {
            DataGetSet d = new DataGetSet();
            Connection con = d.getConGuestProfileDataBase();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM personprofiles WHERE personid=(SELECT max(Personid) FROM Personprofiles where personId LiKE '%TVAI%')");


            while(res.next()){

                String adminId = res.getString("personid");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                String email = res.getString("email");
                String phonenumber = res.getString("phonenumber");

                employee = new Employee(adminId,firstName,lastName,email,phonenumber,username,password);

                System.out.println(res.getString("Personid"));

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String makeEmployeeProfile(String firstName, String lastName,
                                             String email, String phoneNumber,
                                             String username, String password ) {
        try {
            DataGetSet d = new DataGetSet();
            Connection con = d.getConEmployeeProfileDataBase();
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
            stmt.executeUpdate("INSERT INTO PersonProfiles(PersonId,firstname,lastname,email,Phonenumber,Username,password) values ('"+ employeeId + "','"+firstName + "','" +lastName + "','" + email + "','" + phoneNumber + "','" + username + "','" + password+ "')");

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
