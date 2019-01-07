package com.hexad.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private String productCode;
    private int productQuantity;
    private BigDecimal totalPrice;
    private List<Pack> packList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return productQuantity == order.productQuantity &&
                productCode.equals(order.productCode) &&
                totalPrice.equals(order.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, productQuantity, totalPrice);
    }

    public Order(int productQuantity, String productCode, BigDecimal totalPrice) {
        this.productCode = productCode;
        this.productQuantity = productQuantity;
        this.totalPrice = totalPrice;
        packList = new ArrayList<>();
    }

    public Order(int productQuantity, String productCode) {
        this.productCode = productCode;
        this.productQuantity = productQuantity;
        this.totalPrice = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.packList = new ArrayList<>();
    }

    public String getProductCode() {
        return productCode;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<Pack> getPackList() {
        return packList;
    }

}
