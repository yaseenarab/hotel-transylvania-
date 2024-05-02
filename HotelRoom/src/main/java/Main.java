
import Central.CentralDatabase;
import UI.Frames.LoginFrame;
import Utilities.MyLogger;

import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        if(!CentralDatabase.init()) {
            MyLogger.logger.log(Level.SEVERE, "Cannot initialize Central Database. Exiting program...");
            System.exit(1);
        }
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
    }
}
