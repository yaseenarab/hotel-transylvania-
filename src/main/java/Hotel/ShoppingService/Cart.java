package Hotel.ShoppingService;

import java.util.HashMap;

public class Cart {
    // Item to quantity of Item in current Cart
    HashMap<Item, Integer> Items = new HashMap<>();

    public Integer getItemQuantity(Item i) {
        Integer result = Items.get(i);
        return (result == null) ? -1 : result;
    }
}
