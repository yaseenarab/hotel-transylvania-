package Hotel.AccountService;

import Hotel.Utilities.MyLogger;
import java.util.Date;
import java.util.logging.Level;

/**
 * Represents a credit or debit card used in the hotel management system.
 * This class includes methods to set and validate card details such as account number and expiration date.
 */
public class Card {
    private final int ACCOUNT_NUM_LENGTH = 16;
    private String accountNumber;
    private Date expiration;

    /**
     * Constructs a new Card with specified account number and expiration date.
     * Ensures that the card details are valid and logs any errors encountered during card setup.
     *
     * @param accountNumber The card's account number which must be 16 digits long.
     * @param expiration    The expiration date of the card; must be a future date.
     * @throws Exception if the account number or expiration date are invalid.
     */
    public Card(String accountNumber, Date expiration) throws Exception {
        try {
            this.setAccountNumber(accountNumber);
            this.setExpiration(expiration);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error in Card constructor: Passed values were " + accountNumber + "," + expiration);
            throw new Exception();
        }
        this.accountNumber = accountNumber;
        this.expiration = expiration;
    }

    /**
     * Sets the card's account number after validating its length.
     *
     * @param accountNumber The account number to set.
     * @throws Exception if the account number is null or not exactly 16 digits long.
     */
    private void setAccountNumber(String accountNumber) throws Exception {
        if(accountNumber == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Card.setAccountNumber: accountNumber is null");
            throw new Exception();
        }
        else if(accountNumber.length() != ACCOUNT_NUM_LENGTH) {
            MyLogger.logger.log(Level.SEVERE, "Error in Card.setAccountNumber: " +
                    "accountNumber is incorrect length. Should be " + ACCOUNT_NUM_LENGTH + " characters, received " + accountNumber.length());
            throw new Exception();
        }
        this.accountNumber = accountNumber;
    }


    /**
     * Sets the expiration date of the card after validating it.
     *
     * @param expiration The expiration date to set; must not be past or present.
     * @throws Exception if the expiration date is null or is not in the future.
     */
    private void setExpiration(Date expiration) throws Exception {
        if(expiration == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Card.setExpiration: expiration is null");
            throw new Exception();
        }
        else if(expiration.compareTo(new Date()) <= 0) {
            MyLogger.logger.log(Level.SEVERE, "Error in Card.setExpiration: " + expiration + " is before or today expiring");
            throw new Exception();
        }
        this.expiration = expiration;
    }


    /**
     * Returns the account number of the card.
     *
     * @return the account number as a String.
     */

    public String getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * Returns the expiration date of the card.
     *
     * @return the expiration date as a Date object.
     */
    public Date getExpiration() {
        return this.expiration;
    }

    /**
     * Updates the card's details with another card's information.
     *
     * @param card The new card whose details will replace the current card's details.
     * @throws Exception if setting the new card details fails.
     */
    public void updateCard (Card card) throws Exception {
        try {
            this.setAccountNumber(card.getAccountNumber());
            this.setExpiration(card.getExpiration());
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in Card.updateCard: Passed value was " + card);
            throw new Exception();
        }
    }
    /**
     * Compares this Card to another object for equality based on account number and expiration date.
     *
     * @param obj The object to compare against.
     * @return true if obj is a Card and has the same account number and expiration date, otherwise false.
     */

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }
        return this.accountNumber.equals(((Card) obj).accountNumber) &&
                this.expiration.equals(((Card) obj).expiration);
    }

    /**
     * Returns a string representation of the card which includes its account number and expiration date.
     *
     * @return a string describing the card.
     */
    public String toString() {
        return this.accountNumber + "," + this.expiration;
    }
}
