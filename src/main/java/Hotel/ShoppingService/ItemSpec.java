package Hotel.ShoppingService;

import java.math.BigDecimal;

import static Hotel.Utilities.Utilities.primes;
import static java.lang.Math.abs;

public class ItemSpec {
    // Data members
    private String description;
    private BigDecimal price;
    private Long itemID;

    public String getDescription() { return description; }

    public void setDescription(String d) { description = d; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal p) { price = p; }

    public Long getItemID() { return itemID; }

    public void setItemID(Long i) { itemID = i; }

    public int hashCode() {
        int result = 1;

        result = primes[0] * result + description.hashCode();
        result = primes[1] * result + price.hashCode();
        result = primes[2] * result + itemID.hashCode();

        return abs(result);
    }
}
