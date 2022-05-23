package bikenhike;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShoppingBasketTest {

    @Test
    void firstShoppingBasketShouldMatchExpectedResult() {
        final var item1 = Item.createDomesticUntaxedItem("book", "12.49");
        final var item2 = Item.createDomesticTaxedItem("music CD", "14.99");
        final var item3 = Item.createDomesticUntaxedItem("chocolate bar", "0.85");

        final var expectedResult = """
                > 1 book: 12.49
                > 1 music CD: 16.49
                > 1 chocolate bar: 0.85
                > Sales Taxes: 1.50
                > Total: 29.83""";

        assertShoppingBasketIsSameAsExpected(expectedResult, item1, item2, item3);
    }

    @Test
    void secondShoppingBasketShouldMatchExpectedResult() {
        final var item1 = Item.createImportedUntaxedItem("box of chocolates", "10.00");
        final var item2 = Item.createImportedTaxedItem("bottle of perfume", "47.50");

        final var expectedResult = """
                > 1 imported box of chocolates: 10.50
                > 1 imported bottle of perfume: 54.65
                > Sales Taxes: 7.65
                > Total: 65.15""";

        assertShoppingBasketIsSameAsExpected(expectedResult, item1, item2);
    }

    @Test
    void thirdShoppingBasketShouldMatchExpectedResult() {
        final var item1 = Item.createImportedTaxedItem("bottle of perfume", "27.99");
        final var item2 = Item.createDomesticTaxedItem("bottle of perfume", "18.99");
        final var item3 = Item.createDomesticUntaxedItem("packet of headache pills", "9.75");
        final var item4 = Item.createImportedUntaxedItem("box of chocolates", "11.25");

        final var expectedResult = """
                > 1 imported bottle of perfume: 32.19
                > 1 bottle of perfume: 20.89
                > 1 packet of headache pills: 9.75
                > 1 imported box of chocolates: 11.85
                > Sales Taxes: 6.70
                > Total: 74.68""";

        assertShoppingBasketIsSameAsExpected(expectedResult, item1, item2, item3, item4);
    }

    private void assertShoppingBasketIsSameAsExpected(final String expectedResult, final Item... items) {
        Assertions.assertEquals(expectedResult, ShoppingBasket.of(items).generateReceiptDetails());
    }
}
