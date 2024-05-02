package AccountService;

import LoggerPackage.MyLogger;
import java.util.Date;
import java.util.logging.Level;
public class Card {
    private final int ACCOUNT_NUM_LENGTH = 16;
    private String accountNumber;
    private Date expiration;
    private Double funds;
    Card (String accountNumber, Date expiration, Double funds) throws Exception {
        try {
            this.setAccountNumber(accountNumber);
            this.setExpiration(expiration);
            this.setFunds(funds);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error in Card constructor: Passed values were " + accountNumber + "," + expiration + "," + funds);
            throw new Exception();
        }
        this.accountNumber = accountNumber;
        this.expiration = expiration;
        this.funds = funds;
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
    private void setFunds(Double funds) throws Exception{
        if(funds == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Card.setFunds: funds is null");
            throw new Exception();
        }
        if(funds <= 0.001) {
            MyLogger.logger.log(Level.SEVERE, "Error in Card.setFunds: funds is " + funds + " which is <= 0.001");
            throw new Exception();
        }
        this.funds = funds;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }
    public Date getExpiration() {
        return this.expiration;
    }
    public Double getFunds() {
        return this.funds;
    }
    public void updateCard (Card card) throws Exception {
        try {
            this.setAccountNumber(card.getAccountNumber());
            this.setExpiration(card.getExpiration());
            this.setFunds(card.getFunds());
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in Card.updateCard: Passed value was " + card);
            throw new Exception();
        }
    }
    
    public void chargeCard (Double amount) throws Exception {
        try {
            if(amount == null) {
                MyLogger.logger.log(Level.SEVERE, "Error in Card.chargeCard: amount is null");
                throw new Exception();
            }
            else if(this.funds - amount < 0.001) {
                MyLogger.logger.log(Level.SEVERE, "Error in Card.chargeCard: " +
                        "Insufficient funds when charging " + amount + ". Card balance is " + this.funds);
                throw new Exception();
            }
            this.setFunds(this.funds - amount);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in Card.chargeCard: Passed value was " + amount);
            throw new Exception();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }
        return this.accountNumber.equals(((Card) obj).accountNumber) &&
                this.funds.equals(((Card) obj).funds) &&
                this.expiration.equals(((Card) obj).expiration);
    }
    public String toString() {
        return this.accountNumber + "," + this.expiration + "," + this.funds;
    }
}
