package com.hexad.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Pack {
    private int packQuantity;
    private int productQuantity;
    private BigDecimal unitPackPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pack pack = (Pack) o;
        return packQuantity == pack.packQuantity &&
                productQuantity == pack.productQuantity &&
                unitPackPrice.equals(pack.unitPackPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packQuantity, productQuantity, unitPackPrice);
    }

    public Pack(int packQuantity, int productQuantity, BigDecimal unitPackPrice) {
        this.packQuantity = packQuantity;
        this.productQuantity = productQuantity;
        this.unitPackPrice = unitPackPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    Pack(int productQuantity, BigDecimal unitPackPrice) {
        this.productQuantity = productQuantity;
        this.unitPackPrice = unitPackPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public int getPackQuantity() {
        return packQuantity;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public BigDecimal getUnitPackPrice() {
        return unitPackPrice;
    }

}
