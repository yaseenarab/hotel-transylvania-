package Central;

import AccountService.Admin;
import AccountService.Arlow;
import AccountService.Employee;
import AccountService.Guest;
import AccountService.Person;
import Utilities.MyLogger;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class CentralProfiles {
    private static final Arlow ARLOW = new Arlow();

    public static String getProfileType(String profileID) {
        return Arlow.getProfileType(profileID);
    }

    public static Guest getGuest(String username, String password) {

        Guest guest = null;
        try {
            ResultSet res = CentralDatabase.getGuest(username, password);

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
    public static Boolean guestisIn(String username) {
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
    public static Boolean AdminisIn(String username) {
        try{
            Connection con = CentralDatabase.getConAdminProfileDataBase();
            Statement stmt = con.createStatement();

            ResultSet res = stmt.executeQuery("Select * from AdminProfiles where Username = '" + username+"'");
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
            Method generateGuestID = Arlow.class.getDeclaredMethod("generateGuestID");
            generateGuestID.setAccessible(true);
            String guestID = generateGuestID.invoke(ARLOW).toString();

            Guest guest = new Guest(guestID, firstName, lastName, email, phoneNumber, username, password);
            CentralDatabase.insertIntoPersonProfiles(guest.toString());

            return guestID;
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralProfiles.makeGuestProfile: " +
                    "Values passed were " + firstName + "," + lastName + "," + email + "," +
                    phoneNumber + "," + username + "," + password);
            return null;
        }
    }
    public static String makeAdminProfile(String firstName, String lastName,
                                          String email, String phoneNumber,
                                          String username, String password ) {
        try {
            Method generateAdminID = Arlow.class.getDeclaredMethod("generateAdminId");
            generateAdminID.setAccessible(true);
            String adminID = generateAdminID.invoke(ARLOW).toString();

            Admin admin = new Admin(adminID, firstName, lastName, email, phoneNumber, username, password);
            CentralDatabase.insertIntoAdminProfiles(admin.toString());

            return adminID;
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralProfiles.makeGuestProfile: " +
                    "Values passed were " + firstName + "," + lastName + "," + email + "," +
                    phoneNumber + "," + username + "," + password);
            return null;
        }
    }
    public static String makeEmployeeProfile(String firstName, String lastName,
                                             String email, String phoneNumber,
                                             String username, String password ) {
        try {
            Method generateEmployeeID = Arlow.class.getDeclaredMethod("generateEmployeeID");
            generateEmployeeID.setAccessible(true);
            String employeeID = generateEmployeeID.invoke(ARLOW).toString();

            Employee employee = new Employee(employeeID, firstName, lastName, email, phoneNumber, username, password);
            CentralDatabase.insertIntoEmployeeProfiles(employee.toString());

            return employeeID;
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralProfiles.makeEmployeeProfile: " +
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
            ResultSet res = stmt.executeQuery("SELECT * FROM ADMINPROFILES WHERE personid=(SELECT max(Personid) FROM ADMINPROFILES where personId LiKE '%TVAI%')");


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
    
    public static boolean resetGuestPassword(String username) {
	    if (guestisIn(username)) {
    		try {
	            Connection con = CentralDatabase.getConGuestProfileDataBase();
	            Statement stmt = con.createStatement();
	            ResultSet res = stmt.executeQuery("UPDATE PersonProfiles SET password='password' WHERE username = " + username + " RETURNING personid, username, password");
	
	
	            while(res.next()){
	
	                String guestid = res.getString("personid");
	                String guestUsername = res.getString("firstname");
	                String password = res.getString("lastname");
	                
	                if (guestUsername.equals(username) && password.equals("password")) {
	                	System.out.println("Reset " + guestid + ": " + guestUsername + " password");
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
