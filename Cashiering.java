import java.util.Map;
import java.util.TreeMap;

public class Cashiering {
    Map<GuestProfile, Card> guestCards;
    Cashiering() {
        this.guestCards = new TreeMap<>();
    }
    public void addCard(GuestProfile guest, Card card) {
        guestCards.put(guest, card);
    }
    public void removeCard(GuestProfile guestProfile) {
        guestCards.remove(guestProfile);
    }
    public boolean isCardOnFile(GuestProfile guestProfile) {
        return guestCards.containsKey(guestProfile);
    }
    public Card getCardOnFile(GuestProfile guestProfile) {
        if(guestCards.containsKey(guestProfile)) {
            return guestCards.get(guestProfile);
        }
        return null;
    }
    public GuestProfile getAccountFromCard(Card card) {
        for (GuestProfile guest : guestCards.keySet()) {
            if (guestCards.get(guest).equals(card)) {
                return guest;
            }
        }
        return null;
    }
}
