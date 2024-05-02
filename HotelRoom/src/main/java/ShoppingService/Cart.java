package ShoppingService;

import java.util.HashMap;

/**
 * Class that stores items (ItemSpec) that the customer has added to while shopping
 *
 * @author Rafe Loya
 * @see ItemSpec
 */
public class Cart {
    /**
     * Container for ItemSpecs in Cart
     */
    HashMap<ItemSpec, Integer> items = new HashMap<>(0);

    /**
     * @return Integer representing total quantity of items
     */
    public Integer getTotalItems() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Get quantity of specified item if it is in the Cart
     *
     * @param i ItemSpec to find quantity of
     * @return Integer, quantity of specified item OR -1 if item is not in cart
     */
    public Integer getItemQuantity(ItemSpec i) {
        Integer result = items.get(i);
        return (result == null) ? -1 : result;
    }

    /**
     * Does one of the following:
     * <ul>
     *     <li> If the given item was not already inside Cart, adds it as a key with the value (quantity) 1 </li>
     *     <li> If item was inside Cart, increments the quantity of the item by 1 </li>
     * </ul>
     * @param i ItemSpec to add to Cart
     */
    public void addItem(ItemSpec i) { items.merge(i, 1, Integer::sum); }

    /**
     * Does one of the following:
     * <ul>
     *     <li> If the given item was not already inside Cart, adds it as a key with given quantity </li>
     *     <li> If item was inside Cart, increments the quantity of the item by given quantity </li>
     * </ul>
     * @param i        ItemSpec to add to Cart
     * @param quantity Amount of the given item to add to Cart
     */
    public void addItem(ItemSpec i, Integer quantity) { items.merge(i, quantity, Integer::sum); }

    /**
     * Reduces the quantity of a given ItemSpec by 1.
     * If it was not an ItemSpec that was inside Cart OR the ItemSpec's quantity was reduced to 0,
     * the key-value pair is removed from the Cart
     *
     * @param i ItemSpec to remove from Cart
     */
    public void removeItem(ItemSpec i) {
        items.merge(i, -1, Integer::sum);
        if (items.get(i) <= 0) {
            items.remove(i);
        }
    }

    /**
     * Reduces quantity of given ItemSpec by given quantity.
     * If it was not an ItemSpec that was inside Cart OR the ItemSpec's quantity was reduced to 0,
     * the key-value pair is removed from the Cart
     *
     * @param i        ItemSpec to add to Cart
     * @param quantity Amount of the given item to remove from the Cart
     */
    public void removeItem(ItemSpec i, Integer quantity) {
        items.merge(i, -quantity, Integer::sum);
        if (items.get(i) <= 0) {
            items.remove(i);
        }
    }
}
