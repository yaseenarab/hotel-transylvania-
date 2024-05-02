package ShoppingService;

import java.math.BigDecimal;

// Parent of -LineItem objects (finalized cart purchases and reservations)
// Used to generate billing information in final bill
// Might be Interface ?
public class LineItem {
    public String generateLineItem(Long id, BigDecimal price, String name, String description, Integer quantity) {
        return id + ","
                + price + ","
                + name + ","
                + description + ","
                + quantity;
    }
}
