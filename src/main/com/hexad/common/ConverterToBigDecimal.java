package com.hexad.common;

import java.math.BigDecimal;

/**
 * A helper to convert double to decimal and set the default scale and round mode
 */
public interface ConverterToBigDecimal {

    int DEFAULT_SCALE = 2;
    int DEFAULT_ROUND = BigDecimal.ROUND_HALF_UP;

    /**
     * Create a BigDecimal with the double value
     * @param d double value
     * @return BigDecimal with the default scale and round mode
     */
    default BigDecimal toBigDecimal(double d) {
        return new BigDecimal(d).setScale(DEFAULT_SCALE, DEFAULT_ROUND);
    }

    /**
     * Set the default scale and round mode in a BigDecimal
     * @param b the BigDecimal
     * @return BigDecimal with the default scale and round mode
     */
    default BigDecimal setScaleAndRound(BigDecimal b) {
        return b.setScale(DEFAULT_SCALE, DEFAULT_ROUND);
    }

}
