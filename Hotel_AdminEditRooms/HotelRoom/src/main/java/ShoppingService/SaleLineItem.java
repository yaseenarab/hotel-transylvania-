package ShoppingService;

import java.math.BigDecimal;

//
public class SaleLineItem extends LineItem {
    public String generateLineItem(Long id, BigDecimal price, String name, String description, Integer quantity) {
        return super.generateLineItem(id, price, name, description, quantity);
    }
}
