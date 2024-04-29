package Hotel.ShoppingHandlers;

import Hotel.ShoppingService.Cart;
import Hotel.ShoppingService.Catalogue;
import Hotel.ShoppingService.ItemSpec;

import java.util.*;
import java.util.logging.Logger;


public class ShoppingMainPanelHandler {
    private static final Logger SMPH_Logger
            = Logger.getLogger(ShoppingMainPanelHandler.class.getName());
    public static ArrayList<ItemSpec> loadItems() {
        SMPH_Logger.info("Requested all item specifications for main panel's content");
        return new ArrayList<>(Catalogue.getItemSpecifications());
    }
    public static String getNumberItemsInCart(Cart c) {
        SMPH_Logger.info("Requested number of items in cart for main panel's header");
        return c.getTotalItems().toString();
    }
}
