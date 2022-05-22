package bikenhike;

import java.math.BigDecimal;

public final class ItemFormatter {

    private static final String ITEM_LINE_OUTPUT_FORMAT = "> 1 %s: %s\n";
    private static final String ITEM_LINE_OUTPUT_FORMAT_IMPORTED = "> 1 imported %s: %s\n";

    private ItemFormatter() {
    }

    public static String generateItemOutputLine(String name, BigDecimal grossPrice) {
        return String.format(ITEM_LINE_OUTPUT_FORMAT, name, grossPrice);
    }

    public static String generateItemOutputLineImported(String name, BigDecimal grossPrice) {
        return String.format(ITEM_LINE_OUTPUT_FORMAT_IMPORTED, name, grossPrice);
    }


}
