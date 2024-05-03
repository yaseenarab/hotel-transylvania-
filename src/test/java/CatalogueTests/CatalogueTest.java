package CatalogueTests;

import Central.CentralDatabase;
import Hotel.ShoppingService.Catalogue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CatalogueTest {

    @BeforeAll
    static void setup() {
        CentralDatabase.init();
    }

    @Test
    void CatalogueGetItemsTest() {
        var catalogue = Catalogue.getItems();
        assert(!catalogue.isEmpty());
    }
}
