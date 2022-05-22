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

    @Test
    void secondShoppingBasketShouldMatchExpectedResult() {
        final var item1 = new Item("box of chocolates", "10.00", false, true);
        final var item2 = new Item("bottle of perfume", "47.50", true, true);

        final var expectedResult = """
                > 1 imported box of chocolates: 10.50
                > 1 imported bottle of perfume: 54.65
                > Sales Taxes: 7.65
                > Total: 65.15""";

        Assertions.assertEquals(expectedResult, ShoppingBasket.of(item1, item2).generateReceiptDetails());
    }

    @Test
    void thirdShoppingBasketShouldMatchExpectedResult() {
        final var item1 = new Item("bottle of perfume", "27.99", true, true);
        final var item2 = new Item("bottle of perfume", "18.99", true, false);
        final var item3 = new Item("packet of headache pills", "9.75", false, false);
        final var item4 = new Item("box of chocolates", "11.25", false, true);

        final var expectedResult = """
                > 1 imported bottle of perfume: 32.19
                > 1 bottle of perfume: 20.89
                > 1 packet of headache pills: 9.75
                > 1 imported box of chocolates: 11.85
                > Sales Taxes: 6.70
                > Total: 74.68""";

        Assertions.assertEquals(expectedResult, ShoppingBasket.of(item1, item2, item3, item4).generateReceiptDetails());
    }
}
