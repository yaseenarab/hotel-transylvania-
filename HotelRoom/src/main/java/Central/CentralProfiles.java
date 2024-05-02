package Central;

import AccountService.Admin;
import AccountService.Arlow;
import AccountService.Employee;
import AccountService.Guest;
//import DataBaseManipulation.DataGetSet;
//import Hotel.Databases.EmployeeDatabase;
//import Hotel.Databases.GuestDatabase;
import LoggerPackage.MyLogger;
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
                System.out.println("Username: " + username );
                System.out.println("Password: " + username );
                SQL = "Select * from Employeeprofiles where Username = '" + username+"' And password = '" + password +"'";

            }
            else if(type.equals("Admin")){
                con = CentralDatabase.getConAdminProfileDataBase();
                SQL = "Select * from AdminProfiles where Username = '" + username+"' And password = '" + password +"'";

            }

            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);


            if(res.next()){
                System.out.println("TRUE BITCH");
                return true;
            }
            System.out.println("TRUE BITCH");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
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

                System.out.println(res.getString("Personid"));

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employee;
    }
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

                System.out.println(res.getString("Personid"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return admin;
    }
    
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

    
    public static boolean resetGuestPassword(String username) {
	    if (guestisIn(username)) {
    		try {
	            Connection con = CentralDatabase.getConGuestProfileDataBase();
	            Statement stmt = con.createStatement();
	            int numAffected = stmt.executeUpdate("UPDATE PersonProfiles SET password='password' WHERE username = '" + username + "'");
	
	
	            if (numAffected > 0) {
	            	return true;
	            }
	            return false;
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
    	
    	return false;
    }
    
    public static boolean resetEmployeePassword(String username) {
	    if (EmployeeisIn(username)) {
    		try {
	            Connection con = CentralDatabase.getConEmployeeProfileDataBase();
	            Statement stmt = con.createStatement();
	            ResultSet res = stmt.executeQuery("UPDATE PersonProfiles SET password='password' WHERE username = " + username + " RETURNING personid, username, password");
	
	
	            while(res.next()){
	
	                String employeeid = res.getString("personid");
	                String employeeUsername = res.getString("firstname");
	                String password = res.getString("lastname");
	                
	                if (employeeUsername.equals(username) && password.equals("password")) {
	                	System.out.println("Reset " + employeeid + ": " + employeeUsername + " password");
	                	return true;
	                }
	
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
    	
    	return false;
    }
    
    public static boolean resetAdminPassword(String username) {
    	if (AdminisIn(username)) {
	    	try {
	            Connection con = CentralDatabase.getConGuestProfileDataBase();
	            Statement stmt = con.createStatement();
	            ResultSet res = stmt.executeQuery("UPDATE PersonProfiles SET password='password' WHERE username = " + username + " RETURNING personid, username, password");
	
	
	            while(res.next()){
	
	                String adminid = res.getString("personid");
	                String adminUsername = res.getString("firstname");
	                String password = res.getString("lastname");
	                
	                if (adminUsername.equals(username) && password.equals("password")) {
	                	System.out.println("Reset " + adminid + ": " + adminUsername + " password");
	                	return true;
	                }
	
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
    	}
    	
    	return false;
    }
}
