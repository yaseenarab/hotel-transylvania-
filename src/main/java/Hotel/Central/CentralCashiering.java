package Hotel.Central;

import Hotel.AccountService.Card;
import Hotel.AccountService.Guest;
import Hotel.Utilities.DateProcessor;

import java.util.Date;

public class CentralCashiering {
    public static boolean saveCard(String username, String password, String account, Date expiration) {
        if (username == null || password == null || account == null || expiration == null) {
            return false;
        }
        try {
            Card c = new Card(account, expiration);
            String cardData = username + "," + password + "," +
                    account + "," + DateProcessor.dateToString(expiration) + ",0";
            CentralDatabase.insertIntoCards(cardData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean saveCard(Guest guest, String account, Date expiration) {
        if (guest == null || account == null || expiration == null) {
            return false;
        }
        try {
            Card c = new Card(account, expiration);
            String cardData = guest.getUsername() + "," + guest.getPassword() + "," +
                    account + "," + DateProcessor.dateToString(expiration) + ",0";
            CentralDatabase.insertIntoCards(cardData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
