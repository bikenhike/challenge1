package bikenhike;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingBasket {

    private static final String RECEIPT_DETAILS_FORMAT = """
            > Sales Taxes: %s
            > Total: %s""";

    private final List<Item> items = new ArrayList<>();

    private ShoppingBasket(Item... items) {
        this.items.addAll(List.of(items));
    }

    public static ShoppingBasket of(Item... items) {
        return new ShoppingBasket(items);
    }

    public String generateReceiptDetails() {
        final var taxes = items.stream().map(Item::getTaxes).reduce(BigDecimal.ZERO, BigDecimal::add);
        final var total = items.stream().map(Item::getGrossPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        final var invoice = items.stream().map(Item::generateItemReceiptLine).collect(Collectors.joining());
        return invoice.concat(RECEIPT_DETAILS_FORMAT.formatted(taxes, total));
    }


}
