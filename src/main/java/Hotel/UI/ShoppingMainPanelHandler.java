package UI;

import AccountService.Guest;
import ShoppingService.Cart;
import ShoppingService.Catalogue;
import ShoppingService.ItemSpec;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ShoppingMainPanelHandler {
    private static final Logger SMPH_Logger
            = Logger.getLogger(ShoppingMainPanelHandler.class.getName());

    public static void initGuestCart(Guest g)  {
        g.setCart(new Cart());
    }

    public static void clearGuestCart(Guest g) {
        g.setCart(null);
    }

    public static ArrayList<ItemSpec> loadItems() {
        SMPH_Logger.info("Requested all item specifications for main panel's content");
        return new ArrayList<>(Catalogue.getItemSpecifications());
    }
    public static String getNumberItemsInCart(Cart c) {
        SMPH_Logger.info("Requested number of items in cart for main panel's header");
        if(c == null) { c = new Cart(); }
        return c.getTotalItems().toString();
    }
}
