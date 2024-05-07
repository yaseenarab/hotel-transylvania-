package Hotel.Central;

import java.net.ConnectException;
import java.sql.*;
import Hotel.Enums.BedType;
import Hotel.Enums.QualityLevel;
import Hotel.Enums.RoomStatus;

/**
 * Centralized database management class for handling all database operations
 * within the hotel management system. This class establishes connections to multiple
 * databases and provides methods to interact with these databases effectively.
 */
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

    /**
     * Initializes connections to all specified databases.
     *
     * @return true if all connections are successfully established, false otherwise.
     */
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
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * Gets the connection object for the hotel rooms database.
     *
     * @return Connection object connected to the hotel rooms database.
     */
    public static Connection getConHotelRoomsDatabase() {
        return conHot;
    }

    /**
     * Gets the connection object for the reservations' database.
     *
     * @return Connection object connected to the reservations' database.
     */
    public static Connection getConReservationDatabase() {
        return conRes;
    }
    /**
     * Gets the connection object for the guest profiles database.
     *
     * @return Connection object connected to the guest profiles database.
     */
    public static Connection getConGuestProfileDataBase() {
        return conGuest;
    }
    /**
     * Gets the connection object for the employee profiles database.
     *
     * @return Connection object connected to the employee profiles database.
     */
    public static Connection getConEmployeeProfileDataBase() {
        return conEmployee;
    }
    /**
     * Gets the connection object for the admin profiles database.
     *
     * @return Connection object connected to the admin profiles database.
     */
    public static Connection getConAdminProfileDataBase() {
        return conAdmin;
    }
    /**
     * Gets the connection object for the cashiering database.
     *
     * @return Connection object connected to the cashiering database.
     */
    public static Connection getConCashieringDatabase() { return conCash; }
    /**
     * Retrieves the first name of the guest based on the username from the guest profiles database.
     *
     * @param username The username to look up.
     * @return The first name of the guest if found, null otherwise.
     */
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

    /**
     * Retrieves the username associated with a room from the reservations database.
     *
     * @param roomNum The room number to look up.
     * @return The username associated with that room if found, null otherwise.
     */
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

    /**
     * Updates the card balance for a user in the cashiering database.
     *
     * @param user The username whose balance needs updating.
     * @param cost The amount to add to the current balance.
     */
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

    /**
     * Retrieves reservation details for a specific room from the reservations database.
     *
     * @param roomNum The room number to look up reservations for.
     * @return A ResultSet containing the reservation details, null if no reservation is found.
     */
    public static ResultSet getReservation(int roomNum) {
        ResultSet res = null;
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM RESERVATIONS WHERE ROOMNUMBER = " + roomNum);
        } catch (Exception e) {}

        return res;
    }

    /**
     * Removes a specific reservation based on the room number from the reservations database.
     *
     * @param roomNum The room number of the reservation to remove.
     */
    public static void removeReservation(int roomNum) {
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            stmt.execute("DELETE FROM RESERVATIONS WHERE (ROOMNUMBER = " + roomNum + ")");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    /**
     * Checks in a guest by updating the check-in status in the reservations database.
     *
     * @param roomNum The room number where the guest is checking in.
     */
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

    /**
     * Checks if a username is unique across all profile databases (guests, employees, admins).
     *
     * @param username The username to check for uniqueness.
     * @return true if the username is unique, false if it exists in any of the profile databases.
     */
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

    /**
     * Retrieves details of a room from the hotel rooms database based on the room number.
     *
     * @param roomNumber The room number to retrieve details for.
     * @return A ResultSet containing the room details, null if no details are found.
     */
    public static ResultSet getRoom(int roomNumber) {
        ResultSet res = null;
        try {
            Connection con = conHot;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM ROOMS WHERE ROOMNUMBER = " + roomNumber);
        } catch (Exception e) {}

        return res;
    }

    /**
     * Removes a room from the hotel rooms database based on the room number.
     *
     * @param roomNumber The room number to remove.
     */
    public static void removeRoom(Integer roomNumber) {
        ResultSet res = null;
        try {
            Connection con = conHot;
            Statement stmt = con.createStatement();
            stmt.executeQuery("DELETE * FROM ROOMS WHERE ROOMNUMBER = " + roomNumber);
        } catch (Exception e) {}
    }

    /**
     * Removes a guest profile from the guest profiles database based on the username.
     *
     * @param username The username of the guest to remove.
     */
    public static void removeGuest(String username) {
        ResultSet res = null;
        try {
            Connection con = conGuest;
            Statement stmt = con.createStatement();
            stmt.executeQuery("DELETE * FROM PERSONPROFILES WHERE ROOMNUMBER = " + username);
        } catch (Exception e) {}
    }

    /**
     * Removes an employee profile from the employee profiles database based on the username.
     *
     * @param username The username of the employee to remove.
     */
    public static void removeEmployee(String username) {
        try {
            Connection con = conEmployee;
            Statement stmt = con.createStatement();
            stmt.executeQuery("DELETE * FROM EMPLOYEEPROFILES WHERE ROOMNUMBER = " + username);
        } catch (Exception e) {}
    }


    /**
     * Removes all reservations from the reservations database.
     */
    public static void removeReservations() {
        try {
            Connection con = conRes;
            Statement stmt = con.createStatement();
            stmt.execute("TRUNCATE TABLE RESERVATIONS");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Removes all rooms from the hotel rooms database.
     */
    public static void removeRooms() {
        try {
            Connection con = conHot;
            Statement stmt = con.createStatement();
            stmt.execute("TRUNCATE TABLE ROOMS");
        } catch (Exception e) {}
    }


    /**
     * Retrieves all reservations from the reservations database.
     *
     * @return ResultSet containing all reservations, null if there are no reservations.
     */
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

    /**
     * Inserts a new person profile into the person profiles database.
     *
     * @param line String containing comma-separated values for the person's profile (ID, name, email, etc.).
     * @throws SQLException If an error occurs during the insert operation.
     */
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


    /**
     * Inserts a new employee profile into the employee profiles database.
     *
     * @param line String containing comma-separated values for the employee's profile.
     * @throws SQLException If an error occurs during the insert operation.
     */
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

    /**
     * Inserts a new admin profile into the admin profiles database.
     *
     * @param line String containing comma-separated values for the admin's profile.
     * @throws SQLException If an error occurs during the insert operation.
     */
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

    /**
     * Retrieves reservations based on a username from the reservations' database.
     *
     * @param username The username to look up reservations for.
     * @return ResultSet containing the reservations for the specified username, null if none are found.
     */
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


    /**
     * Retrieves an employee profile based on the username from the employee profiles database.
     *
     * @param username The username to retrieve the employee profile for.
     * @return ResultSet containing the employee profile, null if not found.
     */
    public static ResultSet getEmployee(String username) {
        ResultSet res = null;
        try {
            Connection con = conEmployee;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM EMPLOYEEPROFILES WHERE USERNAME = '" + username + "'");
        } catch (Exception excE) {}

        return res;
    }

    /**
     * Retrieves an admin profile based on the username from the admin profiles database.
     *
     * @param username The username to retrieve the admin profile for.
     * @return ResultSet containing the admin profile, null if not found.
     */
    public static ResultSet getAdmin(String username) {
        ResultSet res = null;
        try {
            Connection con = conAdmin;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM ADMINPROFILES WHERE USERNAME = '" + username + "'");
        } catch (Exception excE) {}

        return res;
    }

    /**
     * Retrieves a person profile based on the username from the person profiles database.
     *
     * @param username The username to retrieve the person profile for.
     * @return ResultSet containing the person profile, null if not found.
     */
    public static ResultSet getPerson(String username) {
        ResultSet res = null;
        try {
            Connection con = conGuest;
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM PERSONPROFILES WHERE USERNAME = '" + username + "'");
        } catch (Exception excE) {}

        return res;
    }

    /**
     * Updates the password for a user across all profile databases (guest, employee, admin).
     *
     * @param username The username whose password is to be updated.
     * @param password The new password to set.
     * @throws Exception If the update operation fails.
     */
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

    /**
     * Inserts new hotel room data into the hotel rooms database.
     *
     * @param line A string containing the details of the room to be inserted.
     * @throws SQLException If an SQL error occurs during the insert operation.
     */
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

            stmt.executeUpdate("insert into ROOMS(ROOMNUMBER, ROOMSTATUS, ROOMTYPE, BEDTYPE, QUALITYLEVEL, SMOKINGALLOWED) values(" + roomNum + ",'" + split[1] + "','"+ split[2]+ "','" + split[3] + "','" + QualityLevel.databaseFormat(split[4]) + "'," + smoking + ")");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the entire catalogue from the catalogue database.
     *
     * @return ResultSet containing all catalogue items, throws SQLException if retrieval fails.
     * @throws SQLException If an SQL error occurs during data retrieval.
     */
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

    /**
     * Inserts a new reservation into the reservations' database.
     *
     * @param line A string containing the details of the reservation to be inserted.
     *
     */
    public static  void insertIntoReservations(String line)  {
        try {
            Statement stmt = conRes.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into Reservations(RoomNumber, Username,StartDate, EndDate) values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a specific card's details from the cashiering data based on the username.
     *
     * @param username The username associated with the card.
     * @return ResultSet containing the card details, null if not found.
     */
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

    /**
     * Retrieves a specific card's details from the cashiering data based on the username.
     *
     * @param username The username associated with the card.
     * @return ResultSet containing the card details, null if not found.
     */
    public static void setCard(String username, String password, String cardNumber, String date) {
        try {

            Connection con  = conCash;
            PreparedStatement pstmt = con.prepareStatement("INSERT into CASHIERINGDATA(username, password, cardnumber, expirationdate, runningbalance) values('" + username +"', '" + password +"', '"+cardNumber +"','"+date +"',"+ 50000 +")");
            pstmt.executeUpdate();

        } catch (Exception e) {}
    }

    /**
     * Checks if a given card number and expiration date match the records in the cashiering database.
     *
     * @param username The username associated with the card.
     * @param cardNumber The card number to match.
     * @param expiration The expiration date to match.
     * @return true if the card number and expiration date match the records, false otherwise.
     */
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


    /**
     * Inserts or updates a card's details in the cashiering database based on the provided username.
     *
     * @param line A string containing the card details to be inserted or updated.
     * @throws SQLException If an SQL error occurs during the insert or update operation.
     */
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

    /**
     * Retrieves all rooms from the hotel rooms database.
     *
     * @return ResultSet containing all room records.
     */
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

    /**
     * Updates the status of a specific room in the hotel rooms database.
     *
     * @param roomNum The room number whose status needs updating.
     * @param roomStatus The new status to set for the room.
     * @throws SQLException If an SQL error occurs during the update operation.
     */
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
