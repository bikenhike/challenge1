package bikenhike;

import java.math.BigDecimal;

public class Item {
    public static final BigDecimal IMPORT_TAX = new BigDecimal("0.05");
    public static final BigDecimal SALES_TAX = new BigDecimal("0.1");
    private static final String ITEM_LINE_OUTPUT_FORMAT = "> 1 %s: %s\n";
    private static final String ITEM_LINE_OUTPUT_FORMAT_IMPORTED = "> 1 imported %s: %s\n";

    private final String name;
    private final BigDecimal price;
    private final boolean isTaxed;
    private final boolean isImported;
    private final BigDecimal taxes;
    private final BigDecimal grossPrice;

    public static Item createDomesticUntaxedItem(String name, String price) {
        return new Item(name, price, false, false);
    }

    public static Item createDomesticTaxedItem(String name, String price) {
        return new Item(name, price, true, false);
    }

    public static Item createImportedUntaxedItem(String name, String price) {
        return new Item(name, price, false, true);
    }

    public static Item createImportedTaxedItem(String name, String price) {
        return new Item(name, price, true, true);
    }

    private Item(String name, String price, boolean isTaxed, boolean isImported) {
        this.name = name;
        this.price = new BigDecimal(price);
        this.isTaxed = isTaxed;
        this.isImported = isImported;
        this.taxes = calculateTaxes();
        this.grossPrice = this.price.add(this.taxes);
    }

    public String generateItemReceiptLine() {
        if (isImported) {
            return String.format(ITEM_LINE_OUTPUT_FORMAT_IMPORTED, name, grossPrice);
        } else {
            return String.format(ITEM_LINE_OUTPUT_FORMAT, name, grossPrice);
        }
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    private BigDecimal calculateTaxes() {
        var total = BigDecimal.ZERO;

        if (isTaxed) {
            total = total.add(RoundingHelper.roundToClosestFiveCents(price.multiply(SALES_TAX)));
        }

        if (isImported) {
            total = total.add(RoundingHelper.roundToClosestFiveCents(price.multiply(IMPORT_TAX)));
        }

        return total;
    }
}
