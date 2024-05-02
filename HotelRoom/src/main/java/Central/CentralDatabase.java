package Central;

import java.math.BigDecimal;
import java.sql.*;

public class CentralDatabase {
    final static private String DB_URLRes = "jdbc:derby:ReservationsData";
    final static private String DB_URLPer = "jdbc:derby:PersonProfilesData";
    final static private String DB_URLEmployee = "jdbc:derby:EmployeeProfilesData";
    final static private String DB_URLAdmin = "jdbc:derby:AdminProfilesData";
    final static private String DB_URLHot = "jdbc:derby:HotelRoomsData";
    final static private String DB_URLCart = "jdbc:derby:ShoppingCartData";
    final static private String DB_URLCatalogue = "jdbc:derby:CatalogueDatabase";
    private static Connection conRes;
    private static Connection conGuest;
    private static Connection conEmployee;
    private static Connection conAdmin;
    private static Connection conHot;
    private static Connection conCart;
    private static Connection conCatalogue;
    public static boolean init() {
        try {
            conRes = DriverManager.getConnection(DB_URLRes);
            conGuest = DriverManager.getConnection(DB_URLPer);
            conEmployee = DriverManager.getConnection(DB_URLEmployee);
            conAdmin = DriverManager.getConnection(DB_URLAdmin);
            conHot = DriverManager.getConnection(DB_URLHot);
            conCart = DriverManager.getConnection(DB_URLCart);
            conCatalogue = DriverManager.getConnection(DB_URLCatalogue);
        } catch (Exception e) {
            e.printStackTrace();
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
    public static Connection getConCart() { return conCart; }

    public static Connection getConCatalogue() { return conCatalogue; }
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
    public static ResultSet getGuest(String username) {
        ResultSet res = null;
        try {
            Connection con = conGuest;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM personprofiles WHERE Username = '"
                    + username + "'");
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

    public static ResultSet getCatalogue() throws SQLException {
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conCatalogue.createStatement();
            rs = stmt.executeQuery("SELECT * FROM CATALOGUE");
        } catch (SQLException e) {
            //System.out.println("[ERR]: Fatal error with database update");
            throw new RuntimeException(e);
        }

        return rs;
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
            boolean smoking = Boolean.valueOf(split[5]);
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
            boolean smoking = Boolean.valueOf(split[5]);
            System.out.println(split[5]);
            System.out.println(smoking);
            stmt.executeUpdate("UPDATE ROOMS SET ROOMSTATUS = '" + split[1] + "', ROOMTYPE = '" + split[2] + "', BEDTYPE = '" + split[3] + "', QUALITYLEVEL = '" + split[4] + "', SMOKINGALLOWED = " + smoking + " WHERE ROOMNUMBER = " + roomNum);
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

    public static void insertIntoCatalogue(String id, String name, String desc, String price, String url, String quantity) {
        try {
            PreparedStatement ps = conCatalogue.prepareStatement("insert into Catalogue(itemid, itemname, itemdescription, itemprice, itemimageurl, itemquantity) values(?, ?, ?, ?, ?, ?)");
            ps.setLong(1, Long.parseLong(id));
            ps.setString(2, name);
            ps.setString(3, desc);
            ps.setBigDecimal(4, new BigDecimal(price));
            ps.setString(5, url);
            ps.setLong(6, Long.parseLong(quantity));
            ps.executeUpdate();
            conCatalogue.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
