package bikenhike;

import java.math.BigDecimal;

public class Item {

    private final String name;
    private final BigDecimal price;
    private final boolean isTaxed;
    private final boolean isImported;

    public Item(String name, String price, boolean isTaxed, boolean isImported) {
        this.name = name;
        this.price = new BigDecimal(price);
        this.isTaxed = isTaxed;
        this.isImported = isImported;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isTaxed() {
        return isTaxed;
    }

    public boolean isImported() {
        return isImported;
    }


}
