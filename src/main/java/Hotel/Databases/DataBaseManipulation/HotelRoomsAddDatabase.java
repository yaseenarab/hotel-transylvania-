package Hotel.DataBaseManipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class HotelRoomsAddDatabase {

    final private String DB_URL = "jdbc:derby:ReservationsData;";
    private Connection con;

    public static void main(String[] args) throws SQLException {
        HotelRoomsAddDatabase hotelRooms = new HotelRoomsAddDatabase();

        //hotelRooms.createTable();

        //hotelRooms.insertIntoDataBase();
        hotelRooms.updateSpot();
    }


    public void updateSpot() throws SQLException {
        con = DriverManager.getConnection(DB_URL);
        Statement stmt = null;

        try {
            Connection con = DriverManager.getConnection(DB_URL);
            stmt = con.createStatement();


            stmt = con.createStatement();
            PreparedStatement pstmt;

            //ResultSet res = stmt.executeQuery("select RoomType" + " FROM Rooms " + "WHERE RoomNumber = " + 302);
            pstmt = con.prepareStatement("Alter Table Reservations Add CheckedIn BOOLEAN");
            pstmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void insertIntoDataBase() throws SQLException {
        con = DriverManager.getConnection(DB_URL);
        Statement stmt = null;

        try {
            Connection con = DriverManager.getConnection(DB_URL);
            stmt = con.createStatement();


            Scanner s = new Scanner(new File("reservations.csv"));


            //s.nextLine();
            //s.nextLine();
            //s.nextLine();

            while(s.hasNextLine()) {

                String line = s.nextLine();

                String[] split = line.split(",");
                String level = "";
                System.out.println(line);
                if (split.length == 4) {
                    stmt.executeUpdate("insert into reservations(RoomNumber, Username,StartDate, EndDate) values(" + Integer.parseInt( split[0]) + ",'"+ split[1] + "','" + split[2] + "','" + split[3] + "')");
                    //stmt.executeUpdate("insert into reservations(PersonID, Username,StartDate, EndDate) values(" +Integer.parseInt( split[0]) + ",'"+ split[1] + "','" + split[2] + "','" + split[3]+   "')");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void createTable() throws SQLException {
    }



}
