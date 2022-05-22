package bikenhike;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class RoundingHelper {
    private static final BigDecimal ONE_HALF = new BigDecimal("0.5");

    private RoundingHelper() {
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
    public static BigDecimal roundToClosestFiveCents(BigDecimal price) {
        return price.divide(ONE_HALF, 1, RoundingMode.UP).multiply(ONE_HALF);
    }
}
