package Hotel.Central;

import Hotel.AccountService.Card;
import Hotel.AccountService.Guest;
import Hotel.Utilities.DateProcessor;

import java.util.Date;


/**
 * CentralCashiering handles the processing and saving of credit card information to the system's database.
 * This class interacts with the central database to ensure credit card details are securely stored and maintained.
 */
public class CentralCashiering {

    /**
     * Saves the credit card information for a user specified by username and password.
     *
     * @param username   The username of the user whose card is being saved.
     * @param password   The password of the user.
     * @param account    The account number of the credit card.
     * @param expiration The expiration date of the credit card.
     * @return true if the card information was saved successfully, false if any parameter is null or saving fails.
     */
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

    /**
     * Saves the credit card information for a Guest instance.
     *
     * @param guest      The Guest object whose card is being saved.
     * @param account    The account number of the credit card.
     * @param expiration The expiration date of the credit card.
     * @return true if the card information was saved successfully, false if any parameter is null or saving fails.
     */
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
