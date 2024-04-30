package Central;

import java.sql.*;

public class CentralDatabase {
    final static private String DB_URLRes = "jdbc:derby:/Users/desirablehemlock/Software Engineering/Hotel_Final/HotelRoom/src/main/java/Databases/ReservationsData";
    final static private String DB_URLPer = "jdbc:derby:/Users/desirablehemlock/Software Engineering/Hotel_Final/HotelRoom/src/main/java/Databases/PersonProfilesData";
    final static private String DB_URLEmployee = "jdbc:derby:/Users/desirablehemlock/Software Engineering/Hotel_Final/HotelRoom/src/main/java/Databases/EmployeeProfilesData";
    final static private String DB_URLAdmin = "jdbc:derby:/Users/desirablehemlock/Software Engineering/Hotel_Final/HotelRoom/src/main/java/Databases/AdminProfilesData";
    final static private String DB_URLHot = "jdbc:derby:/Users/desirablehemlock/Software Engineering/Hotel_Final/HotelRoom/src/main/java/Databases/HotelRoomsData";
    private static Connection conRes;
    private static Connection conGuest;
    private static Connection conEmployee;
    private static Connection conAdmin;
    private static Connection conHot;
    public static boolean init() {
        try {
            conRes = DriverManager.getConnection(DB_URLRes);
            conGuest = DriverManager.getConnection(DB_URLPer);
            conEmployee = DriverManager.getConnection(DB_URLEmployee);
            conAdmin = DriverManager.getConnection(DB_URLAdmin);
            conHot = DriverManager.getConnection(DB_URLHot);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Connection getConHotelRoomsDatabase() {
        return conHot;
    }

    public static Connection getConReservationDatabase() {
        return conRes;
    }

    public static Connection getConGuestProfileDataBase() {
        return conGuest;
    }
    public static Connection getConEmployeeProfileDataBase() {
        return conEmployee;
    }

    public static Connection getConAdminProfileDataBase() {
        return conAdmin;
    }

    public static ResultSet getGuest(String username, String password) {
        ResultSet res = null;
        try {
            Connection con = conGuest;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM personprofiles WHERE Username = '"
                    + username + "' And password = '" + password+"'");
        } catch (Exception e) {}

        return res;
    }
    public static ResultSet getRoom(int roomNumber) {
        ResultSet res = null;
        try {
            Connection con = conHot;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM ROOMS WHERE ROOMNUMBER = " + roomNumber);
        } catch (Exception e) {}

        return res;
    }

    public static void insertIntoPersonProfiles(String line)  {
        try {
            Statement stmt = conGuest.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into PERSONPROFILES(PersonId, FirstName,LastName,Email, PhoneNumber,Username,Password) " +
                    "values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] + "','" + split[4] + "','" + split[5] + "','" + split[6]  +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertIntoEmployeeProfiles(String line)  {
        try {
            Statement stmt = conEmployee.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into EMPLOYEEPROFILES(PersonId, FirstName,LastName,Email, PhoneNumber,Username,Password) " +
                    "values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] + "','" + split[4] + "','" + split[5] + "','" + split[6]  +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertIntoAdminProfiles(String line)  {
        try {
            Statement stmt = conAdmin.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into ADMINPROFILES(PersonId, FirstName,LastName,Email,PhoneNumber,Username,Password) " +
                    "values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] + "','" + split[4] + "','" + split[5] + "','" + split[6]  +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertIntoHotelRoomsData(String line) {
        try {
            Statement stmt = conHot.createStatement();
            String [] split = line.split(",");

            int roomNum = Integer.parseInt(split[0]);
            boolean smoking = Boolean.getBoolean(split[5]);
            stmt.executeUpdate("insert into ROOMS(ROOMNUMBER, ROOMSTATUS, ROOMTYPE, BEDTYPE, QUALITYLEVEL, SMOKINGALLOWED) values(" + roomNum + ",'" + split[1] + "','"+ split[2]+ "','" + split[3] + "','" + split[4] + "'," + smoking +")");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateHotelRoomsData(String line) {
        try {
            Statement stmt = conHot.createStatement();
            String [] split = line.split(",");

            int roomNum = Integer.parseInt(split[0]);
            boolean smoking = Boolean.getBoolean(split[4]);
            stmt.executeUpdate("UPDATE ROOMS SET ROOMSTATUS = " + split[1] + ", ROOMTYPE = " + split[2] + ", QUALITYLEVEL = " + split[3] + ", SMOKINGALLOWED = " + smoking + " WHERE ROOMNUMBER = " + roomNum);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static  void insertIntoReservations(String line)  {
        try {
            Statement stmt = conRes.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into Reservations(RoomNumber, Username,StartDate, EndDate) values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
