package Hotel;

import Hotel.AccountService.Person;
import Hotel.ReservationService.Reservations;
import Hotel.UI.LoginFrame;

import javax.swing.*;

public class main {
    public static void main(String[] args) throws Exception {
        Person.Arlow.init();
        Reservations.Marsha.init();

        JFrame login = new LoginFrame();

        login.setVisible(true);
    }

}