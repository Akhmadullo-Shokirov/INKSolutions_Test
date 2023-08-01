package com.nphase.entity;

import java.math.BigDecimal;

public class DiscountConfiguration {

    private final int minItemsForDiscount;
    private final BigDecimal discountPercentage;

    public DiscountConfiguration(int minItemsForDiscount, BigDecimal discountPercentage) {
        this.minItemsForDiscount = minItemsForDiscount;
        this.discountPercentage = discountPercentage;
    }

    public int getMinItemsForDiscount() {
        return minItemsForDiscount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }
}
