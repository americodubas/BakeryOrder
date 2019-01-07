package com.hexad.service;

import com.hexad.exception.OrderNotFilledException;
import com.hexad.exception.ProductNotFoundException;
import com.hexad.model.Order;
import com.hexad.model.Pack;
import com.hexad.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class OrderService {

    /**
     * Process the list of orders using the minimum amount of packs per product.
     * Each order should contain the amount of the product and the product code, separated by a space.
     * Set used packs into order's pack list.
     *
     * @param orderStringList string list of orders
     * @return the order list with used packs
     * @throws ProductNotFoundException when the product code is not found
     */
    List<Order> processOrder(List<String> orderStringList) throws ProductNotFoundException, OrderNotFilledException {
        List<Order> orderList = convertStringListToOrderList(orderStringList);
        Map<String, Product> productMap = new ProductService().getAllProductMap();

        checkIfAllProductsExist(orderList, productMap);

        List<Order> errorOrderList = new ArrayList<>();
        int remainingProductQuantity;
        for (Order order : orderList) {
            remainingProductQuantity = calculateMinimumAmountOfPacks(order.getProductQuantity(),
                    order,
                    productMap.get(order.getProductCode()).getPackList());
            if (remainingProductQuantity > 0) {
                errorOrderList.add(order);
            }
        }

        if (!errorOrderList.isEmpty()) {
            throw new OrderNotFilledException(getErrorMessage(errorOrderList));
        }

        return orderList;
    }

    /**
     * Create a message with the orders that couldn't be filled.
     * @param errorOrderList lists of orders that couldn't be filled
     * @return error message
     */
    private String getErrorMessage(List<Order> errorOrderList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Order order: errorOrderList) {
            stringBuilder
                    .append("Product: ")
                    .append(order.getProductCode())
                    .append("Quantity: ")
                    .append(order.getProductQuantity());
        }
        return stringBuilder.toString();
    }

    /**
     * Recursive method that loops through the list of available packs of the product to find the minimum amount of packs to fill the order.<br>
     * If the right amount was found, it will add all used packs to the order and will return 0.<br>
     * If it wasn't able to fill the order, it will return the remaining amount of products.
     *
     * @param orderProductQuantity quantity of products that were ordered
     * @param order                register of the order
     * @param packList             list of available packs for a product
     * @return 0 or the remaining amount
     */
    int calculateMinimumAmountOfPacks(int orderProductQuantity, Order order, List<Pack> packList) {
        //no more packs available, return the remaining amount
        if (packList.size() == 0) {
            return orderProductQuantity;
        }

        //check if pack can be used
        Pack pack = packList.get(0);
        if (pack.getProductQuantity() <= orderProductQuantity) {

            //get maximum number of packs that can be used
            int packQuantity = orderProductQuantity / pack.getProductQuantity();
            int remainingAmount = orderProductQuantity - packQuantity * pack.getProductQuantity();

            //if there are remaining amount of products in the order, loop through the other packs till reach 0
            //if we still can't reach to 0 decrease the pack quantity used till reach 0
            while (remainingAmount > 0 && packQuantity > 0) {
                remainingAmount = calculateMinimumAmountOfPacks(remainingAmount, order, packList.subList(1, packList.size()));

                if (remainingAmount > 0) {
                    packQuantity--;
                    remainingAmount = orderProductQuantity - packQuantity * pack.getProductQuantity();
                }
            }

            if (remainingAmount == 0) {
                //reached the right amount of packs
                //add used pack to order's pack list
                order.getPackList().add(new Pack(packQuantity, pack.getProductQuantity(), pack.getUnitPackPrice()));
                order.setTotalPrice(
                        order.getTotalPrice().add(
                                pack.getUnitPackPrice().multiply(
                                        BigDecimal.valueOf(packQuantity)
                                )
                        )
                );
                return remainingAmount;
            }

        }

        //jump to the next pack
        return calculateMinimumAmountOfPacks(orderProductQuantity, order, packList.subList(1, packList.size()));
    }

    /**
     * Receives a list of orders and a map with all available products.
     * Check if the product of each order exists.
     * Throw an exception with all products that were not found.
     *
     * @param orderList  list with all orders
     * @param productMap map with all available products
     * @throws ProductNotFoundException when a product code is not found
     */
    private void checkIfAllProductsExist(List<Order> orderList, Map<String, Product> productMap) throws ProductNotFoundException {
        String productNotFound = "";
        for (Order order : orderList) {
            if (!productMap.containsKey(order.getProductCode())) {
                productNotFound += order.getProductCode() + " ";
            }
        }
        if (productNotFound.length() > 0) {
            throw new ProductNotFoundException(productNotFound);
        }
    }

    /**
     * Convert the string list of orders into a list of order model.
     *
     * @param orderStringList string list of orders
     * @return list of orders
     */
    private List<Order> convertStringListToOrderList(List<String> orderStringList) {
        List<Order> orderList = new ArrayList<>();
        for (String s : orderStringList) {
            String[] strings = s.split(" ");
            if (strings.length != 2) {
                throw new IllegalArgumentException("Invalid argument for an order: [" + s + "]");
            }
            orderList.add(new Order(Integer.parseInt(strings[0]), strings[1]));
        }
        return orderList;
    }

}
