package ShoppingService;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class storing the ItemSpecs stored in a database for referencing in-memory.
 * <p>
 * This class also provides a "trashbin" for ItemSpecs that may have been overwritten.
 * It stores all of the previous values of a given ItemSpec for later review by an Administrator or Clerk,
 * and can either be deleted or restored to these previous values.
 * </p>
 *
 * @author Rafe Loya
 * @see ItemSpec
 */
public class Catalogue {
    /**
     * Container for "catalogue" of items, can find by itemID & returns an ItemSpec
     */
    private static HashMap<Long, ItemSpec> items = new HashMap<>();

    static {
        CatalogueDTO.initCatalogue(items);
    }

    /**
     * Prevents instantiation of the class
     */
    private Catalogue() {
        throw new UnsupportedOperationException("Attempt at instantiating class Catalogue");
    }

    /**
     * Container for "trashbin" of items, can find by itemID & returns a HashSet of
     * all previous version of given ItemSpec
     */
    private static HashMap<Long, HashSet<ItemSpec>> trashbin = new HashMap<>();

    /**
     * @return Catalogue of ItemSpecs as HashMap w/ Long-ItemSpec key-value pairs
     */
    public static HashMap<Long, ItemSpec> getItems() { return items; }


    /**
     * @return Set of all ID values in the catalogue
     */
    public static Set<Long> getIDs() { return items.keySet(); }

    /**
     * @return Collection containing all ItemSpecs in the catalogue
     */
    public static Collection<ItemSpec> getItemSpecifications() { return items.values(); }

    /**
     * Provided the ID exists and is associated with an ItemSpec, this function returns
     * the ItemSpec associated with the given ID
     *
     * @param id Long used to request the specified ItemSpec, if it exists
     * @return The ItemSpec associated with the ID, or null if the ID is not associated
     * with an ItemSpec
     */
    public static ItemSpec getItemSpecification(Long id) {
        // Logic to not return null if item doesn't exist?
        return items.get(id);
    }

    /**
     * Removes the ItemSpec associated with the given ID without saving it in the trashbin
     * if it exists
     *
     * @param id Long used to delete the specified ItemSpec, if it exists
     */
    public static void removeItemSpecification(Long id) {
        items.remove(id);
    }

    /**
     * Add an ItemSpec to the catalogue, if it does not already exist. If the given
     * ItemSpec's ID already exists, the ItemSpec is not added
     *
     * @param is ItemSpec to add to the catalogue
     * @return True if the ItemSpec's ID was not already present in the catalogue,
     * False if the ID already existed.
     */
    public static boolean addItemSpecification(ItemSpec is) {
        if (!items.containsKey(is.getItemID())) {
            items.put(is.getItemID(), is);
            return true;
        }
        return false;
    }

    /**
     * Adds an ItemSpec to the catalogue. If the parameter savePreviousValue is true
     * and putting the ItemSpec returns a previous value associated with the ID,
     * this value is added to the trashbin. Otherwise, the previous value is lost.
     *
     * @param is                ItemSpec to add / merge into the catalogue
     * @param savePreviousValue Boolean to determine if an overwritten ItemSpec is saved
     *                          to the trashbin.
     * @return True if there was an overwritten value, False otherwise or if the value
     * was purposefully not saved
     */
    public static boolean mergeItemSpecification(ItemSpec is, Boolean savePreviousValue) {
        ItemSpec previousItemSpec = items.put(is.getItemID(), is);
        if (savePreviousValue && previousItemSpec != null) {
            if (!trashbin.containsKey(is.getItemID())) {
                trashbin.put(is.getItemID(), new HashSet<>());
            }
            trashbin.get(is.getItemID()).add(is);

            return true;
        }

        return false;
    }
}