package Hotel.AccountService;

import Hotel.LoggerPackage.MyLogger;
import java.util.Date;
import java.util.logging.Level;
public class Card {
    private final int ACCOUNT_NUM_LENGTH = 16;
    private String accountNumber;
    private Date expiration;
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

    public String getAccountNumber() {
        return this.accountNumber;
    }
    public Date getExpiration() {
        return this.expiration;
    }
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }
        return this.accountNumber.equals(((Card) obj).accountNumber) &&
                this.expiration.equals(((Card) obj).expiration);
    }
    public String toString() {
        return this.accountNumber + "," + this.expiration;
    }
}
