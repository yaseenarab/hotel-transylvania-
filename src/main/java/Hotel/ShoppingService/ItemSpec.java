package Hotel.ShoppingService;

import java.math.BigDecimal;

import static Hotel.Utilities.Utilities.primes;
import static java.lang.Math.abs;

/**
 * Class representing an item's specifications for the store's shopping system.
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

    /**
     * Default constructor
     */
    public ItemSpec() {
        price = new BigDecimal("-1.00");
        itemID = -1L;
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
    public ItemSpec(String n, String d, BigDecimal p, Long id, String url) {
        name = n;
        description = d;
        price = p;
        itemID = id;
        imageURL = url;
    }

    /**
     * Constructor with Double price parameter
     */
    public ItemSpec(String n, String d, Double p, Long id, String url) {
        name = n;
        description = d;
        price = BigDecimal.valueOf(p);
        itemID = id;
        imageURL = url;
    }


    /**
     * Constructor with String price parameter
     */
    public ItemSpec(String n, String d, String p, Long id, String url) {
        name = n;
        description = d;
        price = new BigDecimal(p);
        itemID = id;
        imageURL = url;
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

    public int hashCode() {
        int result = 1;

        result = primes[0] * result + name.hashCode();
        result = primes[1] * result + description.hashCode();
        result = primes[2] * result + price.hashCode();
        result = primes[3] * result + itemID.hashCode();
        result = primes[4] * result + imageURL.hashCode();

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