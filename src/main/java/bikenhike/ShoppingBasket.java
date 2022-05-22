package bikenhike;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {

    private static final String RECEIPT_DETAILS_FORMAT = """
            > Sales Taxes: %s
            > Total: %s""";
    public static final MathContext ROUNDING_MODE = new MathContext(3, RoundingMode.HALF_UP);
    public static final MathContext ROUNDING_MODE_IMPORT = new MathContext(2, RoundingMode.UP);
    public static final BigDecimal IMPORT_TAX = new BigDecimal("0.05");
    public static final BigDecimal SALES_TAX = new BigDecimal("0.1");

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
                var tax = grossItemPrice.multiply(SALES_TAX).round(ROUNDING_MODE);
                taxes = taxes.add(tax);
                grossItemPrice = grossItemPrice.add(tax);
                if (!item.isImported()) {
                    stringBuilder.append(ItemFormatter.generateItemOutputLine(item.getName(), grossItemPrice));
                }
            }

            if (item.isImported()) {
                var importTax = itemPrice.multiply(IMPORT_TAX).round(ROUNDING_MODE_IMPORT);
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
}
