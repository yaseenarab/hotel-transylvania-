package Hotel.DataBaseManipulation;

import java.sql.*;

public class DataGetSet {

    final private String DB_URLRes = "jdbc:derby:ReservationsData;";
    final private String DB_URLPer = "jdbc:derby:PersonProfilesData;";
    final private String DB_URLEmployee = "jdbc:derby:EmployeeProfilesData;";
    final private String DB_URLAdmin = "jdbc:derby:AdminProfilesData;";
    final private String DB_URLHot = "jdbc:derby:HotelRoomsData;";
    private Connection conRes;
    private Connection conGuest;
    private Connection conEmployee;
    private Connection conAdmin;
    private Connection conHot;

    public DataGetSet() throws SQLException {
        conHot = DriverManager.getConnection(DB_URLHot);
        conGuest = DriverManager.getConnection(DB_URLPer);
        conRes = DriverManager.getConnection(DB_URLRes);
        conEmployee = DriverManager.getConnection(DB_URLEmployee);
        conAdmin = DriverManager.getConnection(DB_URLAdmin);
    }

    public Connection getConHottelRoomsDatabase() {
        return conHot;
    }

    public Connection getConReservationDatabase() {
        return conRes;
    }

    public Connection getConGuestProfileDataBase() {
        return conGuest;
    }
    public Connection getConEmployeeProfileDataBase() {
        return conEmployee;
    }

    public Connection getConAdminProfileDataBase() {
        return conAdmin;
    }


    public void insertIntoPersonProfiles(String line)  {
        try {
            Statement stmt = conGuest.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into PersonProfiles(PersonId, FirstName,LastName,Email, PhoneNumber,Username,Password) values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] + "','" + split[4] + "','" + split[5] + "','" + split[6]  +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertIntoHotelRoomsData(String line) {
        try {
            Statement stmt = conGuest.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into PersonProfiles(PersonId, FirstName,LastName,Email, PhoneNumber,Username,Password) values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] + "','" + split[4] + "','" + split[5] + "','" + split[6]  +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertIntoReservations(String line)  {
        try {
            Statement stmt = conRes.createStatement();
            String [] split = line.split(",");
            stmt.executeUpdate("insert into Reservations(RoomNumber, Username,StartDate, EndDate) values('" +split[0] + "','" + split[1] + "','"+ split[2]+ "','" + split[3] +"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
