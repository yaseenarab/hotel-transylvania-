package ShoppingService;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static Utilities.Utilities.primes;
import static java.lang.Math.abs;

/**
 * Class representing an item's specifications in-memory for the store's shopping system.
 *
 * @author Rafe Loya
 */
public class ItemSpec {
    private String name;

    private String description;

    private BigDecimal price;

    private Long itemID;

    /**
     * Name of the file in the .\resource associated with the item
     */
    private String imageURL;

    private Long quantity;

    /**
     * Default constructor
     */
    public ItemSpec() {
        price = new BigDecimal("-1.00");
        itemID = -1L;
        quantity = 0L;
    }

    /**
     * Constructor
     *
     * @param n Name of item
     * @param d Description for item
     * @param p Price of item
     * @param id ID of item in catalogue
     * @param url File name of image associated with item in .\resource
     */
    public ItemSpec(String n, String d, BigDecimal p, Long id, String url, Long q) {
        name = n;
        description = d;
        price = p;
        price = price.setScale(2, RoundingMode.DOWN);
        itemID = id;
        imageURL = url;
        quantity = q;
    }

    /**
     * Constructor with Double price parameter
     */
    public ItemSpec(String n, String d, Double p, Long id, String url, Long q) {
        name = n;
        description = d;
        price = BigDecimal.valueOf(p);
        itemID = id;
        imageURL = url;
        quantity = q;
    }

    /**
     * Constructor with String price parameter
     */
    public ItemSpec(String n, String d, String p, Long id, String url, Long q) {
        name = n;
        description = d;
        price = new BigDecimal(p);
        itemID = id;
        imageURL = url;
        quantity = q;
    }

    public String getName() { return name; }

    public void setName(String n) { name = n; }

    public String getDescription() { return description; }

    public void setDescription(String d) { description = d; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal p) { price = p; }

    public Long getItemID() { return itemID; }

    public void setItemID(Long i) { itemID = i; }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String i) { imageURL = i; }

    public Long getQuantity() { return quantity; }

    public void setQuantity(Long q) { quantity = q; }

    public int hashCode() {
        int result = 1;

        result = primes[0] * result + name.hashCode();
        result = primes[1] * result + description.hashCode();
        result = primes[2] * result + price.hashCode();
        result = primes[3] * result + itemID.hashCode();
        result = primes[4] * result + imageURL.hashCode();
        result = primes[5] * result + quantity.hashCode();

        return abs(result);
    }

    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null || obj.getClass() != this.getClass()) { return false; }

        ItemSpec is = (ItemSpec)obj;
        return (this.name != null && this.name.equals(is.name))
                && (this.itemID != null && this.itemID.equals(is.itemID))
                && (this.price != null && this.price.equals(is.price))
                && (this.description != null && this.description.equals(is.description));
    }
}