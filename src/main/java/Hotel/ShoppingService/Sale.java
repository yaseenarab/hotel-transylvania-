package Hotel.ShoppingService;

import java.util.Date;

import static Hotel.Utilities.Utilities.primes;
import static java.lang.Math.abs;

public class Sale {
    private Date date; // Also stores time of sale
    private Long ID;

    public int hashCode() {
        int result = 1;

        result = primes[0] * result + date.hashCode();
        result = primes[1] * result + ID.hashCode();

        return abs(result);
    }

    public SaleLineItem generateSLI() {
        return new SaleLineItem();
    }
}
