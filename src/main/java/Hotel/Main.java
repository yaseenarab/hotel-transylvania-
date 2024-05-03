package Hotel;

import Hotel.Central.CentralDatabase;
import Hotel.UI.Frames.LoginFrame;
import Hotel.Utilities.MyLogger;

import java.util.logging.Level;

public class Main {
    //Hello
    public static void main(String[] args) {
        /*
        final String DB_URL = "jdbc:derby:CatalogueData;create=true";
        try{
            //Connection con = DriverManager.getConnection(DB_URL);
            //Statement stmt = con.createStatement();
            //System.out.println("Creating CatalogueData");

            /*
            stmt.execute("CREATE TABLE CatalogueData ("
                    + "GuestId VARCHAR(25) ,"
                    + "ItemId INTEGER, "
                    + "Quantity INTEGER)");
            //stmt.close();
            //con.close();

            //System.out.println("did it");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
         */

        if (!CentralDatabase.init()) {
            MyLogger.logger.log(Level.SEVERE, "Cannot initialize Central Database. Exiting program...");
            System.exit(1);
        }
        /*
        CentralDatabase.insertIntoCatalogue(
                "75348956",
                "Bottled Water",
                "Sourced from the glaciers of Antarctica.",
                "29.99",
                "bottled_water.png",
                "500"
        );
        CentralDatabase.insertIntoCatalogue(
                "23467856",
                "Dippin' Dot",
                "A singular speck of premium ice cream. Buy Now!",
                "1.99",
                "dippin_dot.png",
                "30000"
        );
        CentralDatabase.insertIntoCatalogue(
                "89325634",
                "Bag O' Toads",
                "A necessary ingredient for any respectable Witch's Brew.",
                "4.99",
                "toad.png",
                "30"
        );
        CentralDatabase.insertIntoCatalogue("53672490",
                "Bag O' Garlic",
                "Keep it away from Dracula!",
                "17.99",
                "garlic.png",
                "21"
        );
        CentralDatabase.insertIntoCatalogue(
                "56790084",
                "Boo-jie Toilet Paper",
                "6-ply wrapping of choice for any distinguished mummies",
                "6.99",
                "tp.png",
                "2"
        );
         */


        LoginFrame login = new LoginFrame();
        login.setVisible(true);
    }
}
