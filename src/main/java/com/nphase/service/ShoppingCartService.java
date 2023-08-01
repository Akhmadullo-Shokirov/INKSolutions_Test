package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

//    Task 2
    public BigDecimal calculateTotalPriceWithDiscount(ShoppingCart shoppingCart) {
        List<Product> productsList = shoppingCart.getProducts();

        return productsList.stream()
                .map(product -> {
                    if (product.getQuantity() > 3) {
                        BigDecimal discount = product.getPricePerUnit().multiply(BigDecimal.valueOf(0.10));
                        return product.getPricePerUnit().subtract(discount).multiply(BigDecimal.valueOf(product.getQuantity()));
                    } else {
                        return product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    Task 3
    public BigDecimal calculateTotalPriceWithDiscountForCategory(ShoppingCart shoppingCart) {
        Map<String, List<Product>> productsByCategory = shoppingCart.getProducts().stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        BigDecimal totalPriceWithDiscount = BigDecimal.ZERO;
        for (List<Product> categoryProducts : productsByCategory.values()) {
            int totalQuantityInCategory = categoryProducts.stream().mapToInt(Product::getQuantity).sum();
            for (Product product : categoryProducts) {
                BigDecimal productPriceWithDiscount;
                if (totalQuantityInCategory > 3) {
                    BigDecimal discount = product.getPricePerUnit().multiply(BigDecimal.valueOf(0.10));
                    productPriceWithDiscount = product.getPricePerUnit().subtract(discount).multiply(BigDecimal.valueOf(product.getQuantity()));
                } else {
                    productPriceWithDiscount = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
                }
                totalPriceWithDiscount = totalPriceWithDiscount.add(productPriceWithDiscount);
            }
        }
        return totalPriceWithDiscount;
    }

//    For Task 4, I added the DiscountConfiguration class
//    that can be used to manage the discount in the service classes
}
