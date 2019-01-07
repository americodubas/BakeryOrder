package com.hexad.service;

import com.hexad.model.Product;

import java.util.HashMap;
import java.util.Map;

class ProductService {

    /**
     * Return a map with all products and available packs.<br>
     * Packs come in decreasing order.
     * @return a map where the string key is the product code and the the value is the product
     */
    Map<String, Product> getAllProductMap() {
        Map<String, Product> productMap = new HashMap<>();

        Product product = new Product("VS5", "Vegemite Scroll", "5 8.99", "3 6.99");
        productMap.put(product.getCode(), product);

        product = new Product("MB11", "Blueberry Muffin", "8 24.95", "5 16.95", "2 9.95");
        productMap.put(product.getCode(), product);

        product = new Product("CF", "Croissant", "9 16.99", "5 9.95", "3 5.95");
        productMap.put(product.getCode(), product);

        return productMap;
    }
}
