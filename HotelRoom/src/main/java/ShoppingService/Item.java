package ShoppingService;

import static Utilities.Utilities.primes;
import static java.lang.Math.abs;

public class Item {
    // Data members
    private String name;
    private ItemSpec specification;

    // Methods
    public String getName() { return name; }

    public void setName(String n) { name = n; }

    public ItemSpec getSpecification() { return specification; }

    public void setSpecification(ItemSpec s) { specification = s; }

    public int hashCode() {
        int result = 1;

        result = primes[3] * result + name.hashCode();
        result = primes[4] * result + specification.hashCode();

        return abs(result);
    }
}
