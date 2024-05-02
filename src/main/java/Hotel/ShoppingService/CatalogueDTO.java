package ShoppingService;

import Central.CentralDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CatalogueDTO {
    public static void initCatalogue(HashMap<Long, ItemSpec> c) {
        if (c == null) { c = new HashMap<>(); }
        try {
            ResultSet rs = CentralDatabase.getCatalogue();
            while(rs.next()) {
                ItemSpec is = new ItemSpec(
                        rs.getString("itemName"),
                        rs.getString("itemDescription"),
                        rs.getBigDecimal("itemPrice"),
                        rs.getLong("itemId"),
                        rs.getString("itemImageURL"),
                        rs.getLong("itemQuantity")
                );
                c.put(is.getItemID(), is);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}