import java.time.ZonedDateTime;

public class Card {
    private String accountNumber;
    private ZonedDateTime expiration;
    private Double funds;
    Card (String accountNumber, ZonedDateTime expiration, Double funds) {
        this.accountNumber = accountNumber;
        this.expiration = expiration;
        this.funds = funds;
    }
    public void updateCard (Card card) {
        this.accountNumber = card.accountNumber;
        this.expiration = card.expiration;
        this.funds = card.funds;
    }
    public boolean chargeCard (Double amount) {
        if (this.funds - amount > 0.01 && this.expiration.compareTo(java.time.ZonedDateTime.now()) >= 0) {
            this.funds -= amount;
            return true;
        }
        return false;
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
}
