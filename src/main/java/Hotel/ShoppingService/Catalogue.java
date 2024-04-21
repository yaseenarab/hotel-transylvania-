package Hotel.ShoppingService;

import java.util.HashMap;

public class Catalogue {
    // ID to Item Specification
    HashMap<Long, ItemSpec> items = new HashMap<>();

    public ItemSpec getItemSpecification(Long id) {
        // Logic to not return null if item doesn't exist?
        return items.get(id);
    }
}
