package bikenhike;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShoppingBasketTest {

    @Test
    void firstShoppingBasketShouldMatchExpectedResult() {
        final var item1 = new Item("book", "12.49", false, false);
        final var item2 = new Item("music CD", "14.99", true, false);
        final var item3 = new Item("chocolate bar", "0.85", false, false);

        final var expectedResult = """
                > 1 book: 12.49
                > 1 music CD: 16.49
                > 1 chocolate bar: 0.85
                > Sales Taxes: 1.50
                > Total: 29.83""";

        Assertions.assertEquals(expectedResult, ShoppingBasket.of(item1, item2, item3).generateReceiptDetails());
    }
}
