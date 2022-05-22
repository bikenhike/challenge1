package bikenhike;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {

    private static final String RECEIPT_DETAILS_FORMAT = """
            > Sales Taxes: %s
            > Total: %s""";
    public static final BigDecimal IMPORT_TAX = new BigDecimal("0.05");
    public static final BigDecimal SALES_TAX = new BigDecimal("0.1");
    public static final BigDecimal ONE_HALF = new BigDecimal("0.5");

    private final List<Item> items = new ArrayList<>();

    private ShoppingBasket(Item... items) {
        this.items.addAll(List.of(items));
    }

    public static ShoppingBasket of(Item... items) {
        return new ShoppingBasket(items);
    }

    public String generateReceiptDetails() {
        final var stringBuilder = new StringBuilder();
        var taxes = BigDecimal.ZERO;
        var total = BigDecimal.ZERO;

        for (final var item : items) {
            final var itemPrice = item.getPrice();
            var grossItemPrice = itemPrice;

            if (item.isTaxed()) {
                final var tax = roundToClosestFiveCents(grossItemPrice.multiply(SALES_TAX));
                taxes = taxes.add(tax);
                grossItemPrice = grossItemPrice.add(tax);
                if (!item.isImported()) {
                    stringBuilder.append(ItemFormatter.generateItemOutputLine(item.getName(), grossItemPrice));
                }
            }

            if (item.isImported()) {
                final var importTax = roundToClosestFiveCents(itemPrice.multiply(IMPORT_TAX));
                taxes = taxes.add(importTax);
                grossItemPrice = grossItemPrice.add(importTax);
                stringBuilder.append(ItemFormatter.generateItemOutputLineImported(item.getName(), grossItemPrice));
            }

            if (!item.isImported() && !item.isTaxed()) {
                stringBuilder.append(ItemFormatter.generateItemOutputLine(item.getName(), grossItemPrice));
            }

            total = total.add(grossItemPrice);
        }

        return stringBuilder.append(String.format(RECEIPT_DETAILS_FORMAT, taxes, total)).toString();
    }

    /**
     * Rounds up to the closest full 5 cents by dividing by half (= doubling)
     * and rounding up to first significant decimal place after decimal point (= scale) and multiplying by half again.
     *
     * e.g. 0.5625 -> 0.6
     * 0.5625 / 0.5 = 1.125
     * 1.125 ≈ 1.2 (rounding up to first decimal place after point)
     * 1.2 * 0.5 = 0.6
     *
     * e.g. 4.75 -> 4.75 (no rounding)
     * 4.75 / 0.5 = 9.5
     * 9.5 ≈ 9.5 ( no rounding necessary)
     * 9.5 * 0.5 = 4.75
     */
    private static BigDecimal roundToClosestFiveCents(BigDecimal price) {
        return price.divide(ONE_HALF, 1, RoundingMode.UP).multiply(ONE_HALF);
    }
}
