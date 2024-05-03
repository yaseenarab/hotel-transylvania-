package Hotel.Central;

import java.sql.*;
import Hotel.Enums.BedType;
import Hotel.Enums.QualityLevel;
import Hotel.Enums.RoomStatus;

public class CentralDatabase {
    final static private String DB_URLRes = "jdbc:derby:ReservationsData;";
    final static private String DB_URLPer = "jdbc:derby:PersonProfilesData;";
    final static private String DB_URLEmployee = "jdbc:derby:EmployeeProfilesData;";
    final static private String DB_URLAdmin = "jdbc:derby:AdminProfilesData;";
    final static private String DB_URLHot = "jdbc:derby:HotelRoomsData;";
    final static private String DB_URLCash = "jdbc:derby:CashieringData;";
    final static private String DB_URLCart = "jdbc:derby:ShoppingCartData";
    final static private String DB_URLCatalogue = "jdbc:derby:CatalogueDatabase";
    private static Connection conRes;
    private static Connection conGuest;
    private static Connection conEmployee;
    private static Connection conAdmin;
    private static Connection conHot;
    private static Connection conCash;
    private static Connection conCart;
    private static Connection conCatalogue;
    public static boolean init() {
        try {
            conRes = DriverManager.getConnection(DB_URLRes);
            conGuest = DriverManager.getConnection(DB_URLPer);
            conEmployee = DriverManager.getConnection(DB_URLEmployee);
            conAdmin = DriverManager.getConnection(DB_URLAdmin);
            conHot = DriverManager.getConnection(DB_URLHot);
            conCash = DriverManager.getConnection(DB_URLCash);
            conCart = DriverManager.getConnection(DB_URLCart);
            conCatalogue = DriverManager.getConnection(DB_URLCatalogue);
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
    public static Connection getConCashieringDatabase() { return conCash; }

    public static ResultSet getGuest(String username) {
        ResultSet res = null;
        try {
            Connection con = conGuest;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM PERSONPROFILES WHERE USERNAME = '"
                    + username + "'");
            if(res.next()) {
                return res;
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        return res;
    }
    public static String getUsernameFromRoom(int roomNum) {
        ResultSet res = null;
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM RESERVATIONS WHERE ROOMNUMBER = " + roomNum);
            if(res.next()) {
                return res.getString("USERNAME");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return null;
    }
    public static void updateHotelRoomsData(String line) {
        try {
            Statement stmt = conHot.createStatement();
            String [] split = line.split(",");

            int roomNum = Integer.parseInt(split[0]);
            boolean smoking = Boolean.valueOf(split[5]);
            stmt.executeUpdate("UPDATE ROOMS SET ROOMSTATUS = '" + split[1] + "', ROOMTYPE = '" + split[2] + "', BEDTYPE = '" + split[3] + "', QUALITYLEVEL = '" + split[4] + "', SMOKINGALLOWED = " + smoking + " WHERE ROOMNUMBER = " + roomNum);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateCardBalance(String user, double cost) {
        try {
            ResultSet res = null;
            Connection con = conCash;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM CASHIERINGDATA WHERE USERNAME = '" + user + "'");
            if(res.next()) {
                int curBal = res.getInt("RUNNINGBALANCE");

                String sqlStatement = "UPDATE CASHIERINGDATA SET RUNNINGBALANCE = ? WHERE USERNAME = ?";
                PreparedStatement statement = conCash.prepareStatement(sqlStatement);

                curBal += cost;

                statement.setInt(1, curBal);
                statement.setString(2, user);
                statement.executeUpdate();
            }
        }
        catch (Exception e) {
            System.out.println("Oops");
        }
    }
    public static ResultSet getReservation(int roomNum) {
        ResultSet res = null;
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM RESERVATIONS WHERE ROOMNUMBER = " + roomNum);
        } catch (Exception e) {}

        return res;
    }
    public static void removeReservation(int roomNum) {
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            stmt.execute("DELETE FROM RESERVATIONS WHERE (ROOMNUMBER = " + roomNum + ")");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    public static void checkIn(int roomNum) {
        try {
            String sqlStatement = "UPDATE RESERVATIONS SET CHECKEDIN = ? WHERE ROOMNUMBER = ?";
            PreparedStatement statement = conRes.prepareStatement(sqlStatement);
            statement.setString(1, Boolean.toString(true));
            statement.setString(2, Integer.toString(roomNum));
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    public static boolean isUsernameUnique(String username) {
        ResultSet employee = getEmployee(username);
        ResultSet admin = getAdmin(username);
        ResultSet person = getPerson(username);
        try {
            try {
                if(employee.next()) {
                    return false;
                }
            }
            catch (Exception a) {}

            try {
                if (admin.next()) {
                    return false;
                }
            }
            catch (Exception b) {}

            try {
                if (person.next()) {
                    return false;
                }
            }
            catch (Exception c) {}
        }
        catch (Exception e) {
            return false;
        }
        return true;
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
    public static void removeRoom(Integer roomNumber) {
        ResultSet res = null;
        try {
            Connection con = conHot;
            Statement stmt = con.createStatement();
            stmt.executeQuery("DELETE * FROM ROOMS WHERE ROOMNUMBER = " + roomNumber);
        } catch (Exception e) {}
    }
    public static void removeGuest(String username) {
        ResultSet res = null;
        try {
            Connection con = conGuest;
            Statement stmt = con.createStatement();
            stmt.executeQuery("DELETE * FROM PERSONPROFILES WHERE ROOMNUMBER = " + username);
        } catch (Exception e) {}
    }
    public static void removeEmployee(String username) {
        try {
            Connection con = conEmployee;
            Statement stmt = con.createStatement();
            stmt.executeQuery("DELETE * FROM EMPLOYEEPROFILES WHERE ROOMNUMBER = " + username);
        } catch (Exception e) {}
    }
    public static void removeReservations() {
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            stmt.execute("TRUNCATE TABLE RESERVATIONS");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    public static void removeRooms() {
        try {
            Connection con = conHot;
            Statement stmt = con.createStatement();
            stmt.execute("TRUNCATE TABLE ROOMS");
        } catch (Exception e) {}
    }

    public static ResultSet getReservations() {
        ResultSet res = null;
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM RESERVATIONS");
        } catch (Exception e) {
            System.out.println("Error");
        }

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
    public static ResultSet getReservations(String username) {
        ResultSet res = null;
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM RESERVATIONS WHERE USERNAME = '" + username + "'");
        } catch (Exception e) {
            System.out.println("Error");
        }

        return res;
    }
    public static ResultSet getEmployee(String username) {
        ResultSet res = null;
        try {
            Connection con = conEmployee;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM EMPLOYEEPROFILES WHERE USERNAME = '" + username + "'");
        } catch (Exception excE) {}

        return res;
    }
    public static ResultSet getAdmin(String username) {
        ResultSet res = null;
        try {
            Connection con = conAdmin;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM ADMINPROFILES WHERE USERNAME = '" + username + "'");
        } catch (Exception excE) {}

        return res;
    }
    public static ResultSet getPerson(String username) {
        ResultSet res = null;
        try {
            Connection con = conGuest;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM PERSONPROFILES WHERE USERNAME = '" + username + "'");
        } catch (Exception excE) {}

        return res;
    }
    public static void updatePassword(String username, String password) throws Exception {
        ResultSet employee = getEmployee(username);
        ResultSet admin = getAdmin(username);
        ResultSet person = getPerson(username);
        try {
            try {
                if(employee.next()) {
                    String sqlStatement = "UPDATE EMPLOYEEPROFILES SET PASSWORD = ? WHERE USERNAME = ?";
                    PreparedStatement statement = conEmployee.prepareStatement(sqlStatement);
                    statement.setString(1, password);
                    statement.setString(2, username);
                    statement.executeUpdate();
                }
            }
            catch (Exception a) {}

            try {
                if (admin.next()) {
                    String sqlStatement = "UPDATE ADMINPROFILES SET PASSWORD = ? WHERE USERNAME = ?";
                    PreparedStatement statement = conAdmin.prepareStatement(sqlStatement);
                    statement.setString(1, password);
                    statement.setString(2, username);
                    statement.executeUpdate();
                }
            }
            catch (Exception b) {}

            try {
                if (person.next()) {
                    String sqlStatement = "UPDATE PERSONPROFILES SET PASSWORD = ? WHERE USERNAME = ?";
                    PreparedStatement statement = conGuest.prepareStatement(sqlStatement);
                    statement.setString(1, password);
                    statement.setString(2, username);
                    statement.executeUpdate();

                    sqlStatement = "UPDATE CASHIERINGDATA SET PASSWORD = ? WHERE USERNAME = ?";
                    statement = conCash.prepareStatement(sqlStatement);
                    statement.setString(1, password);
                    statement.setString(2, username);
                    statement.executeUpdate();
                }
            }
            catch (Exception c) {}
        }
        catch (Exception e) {}
    }
    public static void insertIntoHotelRoomsData(String line) {
        try {
            Statement stmt = conHot.createStatement();
            String [] split = line.split(",");

            int roomNum = Integer.parseInt(split[0]);
            boolean smoking;
            if(split[5].equals("true")) {
                smoking = true;
            }
            else {
                smoking = false;
            }


            stmt.executeUpdate("insert into ROOMS(ROOMNUMBER, ROOMSTATUS, ROOMTYPE, BEDTYPE, QUALITYLEVEL, SMOKINGALLOWED) values(" + roomNum + ",'" + split[1] + "','"+ split[2]+ "','" + BedType.databaseFormat(split[3]) + "','" + QualityLevel.databaseFormat(split[4]) + "'," + smoking + ")");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public static  void insertIntoReservations(String line)  {
        try {
            Statement stmt = conRes.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into Reservations(RoomNumber, Username,StartDate, EndDate) values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ResultSet getCard(String username) {
        ResultSet res = null;
        try {
            Statement stmt = conCash.createStatement();
            res = stmt.executeQuery("SELECT * FROM CASHIERINGDATA WHERE USERNAME = '" + username + "'");
            return res;
        }
        catch (Exception e) {
            return null;
        }
    }
    public static boolean cardNumMatchExp(String username,String cardNumber, String expiration) {
        try {
            ResultSet res = getCard(username);
            if (res.next()) {
                String cardNum = res.getString("CARDNUMBER");
                String date = res.getString("EXPIRATIONDATE");
                return cardNumber.equals(cardNum) && expiration.equals(date);
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }
    public static void insertIntoCards(String line) {
        ResultSet res;
        try {
            Statement stmt = conCash.createStatement();
            String [] split = line.split(",");

            res = stmt.executeQuery("SELECT * FROM CASHIERINGDATA WHERE USERNAME = '" + split[0] + "'");
            if(res.next()) {
                String sqlStatement = "UPDATE CASHIERINGDATA SET CARDNUMBER = ? WHERE USERNAME = ?";
                PreparedStatement statement = conCash.prepareStatement(sqlStatement);
                statement.setString(1, split[2]);
                statement.setString(2, split[0]);
                statement.executeUpdate();

                sqlStatement = "UPDATE CASHIERINGDATA SET EXPIRATIONDATE = ? WHERE USERNAME = ?";
                statement = conCash.prepareStatement(sqlStatement);
                statement.setString(1, split[3]);
                statement.setString(2, split[0]);
                statement.executeUpdate();
            }
            else {
                stmt.executeUpdate("insert into CASHIERINGDATA(USERNAME, PASSWORD,CARDNUMBER, EXPIRATIONDATE, RUNNINGBALANCE) values('" +
                        split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] +"'," + Integer.parseInt(split[4]) + ")");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ResultSet getRooms() {
        ResultSet res = null;
        try {
            Connection con = conHot;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM ROOMS");
        } catch (Exception e) {
            System.out.println("Error");
        }

        return res;
    }
    public static void updateRoom(int roomNum, RoomStatus roomStatus ) {
        try {
            String sqlStatement = "UPDATE ROOMS SET ROOMSTATUS = ? WHERE ROOMNUMBER = ?";
            PreparedStatement statement = conHot.prepareStatement(sqlStatement);
            statement.setString(1, roomStatus.toString());
            statement.setString(2, Integer.toString(roomNum));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
