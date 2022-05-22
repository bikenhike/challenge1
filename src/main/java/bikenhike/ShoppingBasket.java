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
            var grossItemPrice = item.getPrice();

            if (item.isTaxed()) {
                var tax = grossItemPrice.multiply(new BigDecimal("0.1")).round(ROUNDING_MODE);
                taxes = taxes.add(tax);
                grossItemPrice = grossItemPrice.add(tax);
            }

            total = total.add(grossItemPrice);
            stringBuilder.append(ItemFormatter.generateItemOutputLine(item.getName(), grossItemPrice));
        }

        return stringBuilder.append(String.format(RECEIPT_DETAILS_FORMAT, taxes, total)).toString();
    }
}
