package com.hexad.service;

import com.hexad.common.ConverterToBigDecimal;
import com.hexad.exception.OrderNotFilledException;
import com.hexad.exception.ProductNotFoundException;
import com.hexad.model.Order;
import com.hexad.model.Pack;
import com.hexad.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceTest implements ConverterToBigDecimal {

    private OrderService orderService;
    private Order seventeenCroissantOrder;
    private Order fourteenBlueberryMuffinOrder;
    private Product croissantProduct;
    private Product blueberryMuffinProduct;

    @Before
    public void setUp() {
        orderService = new OrderService();
        seventeenCroissantOrder = new Order(17, "CF");
        fourteenBlueberryMuffinOrder = new Order(14, "MB11");
        croissantProduct = new Product("CF", "Croissant", "9 16.99", "5 9.95", "3 5.95");
        blueberryMuffinProduct = new Product("MB11", "Blueberry Muffin", "8 24.95", "5 16.95", "2 9.95");
    }

    /**
     * Ordering a list of products:
     * 10 VS5
     * 14 MB11
     * 13 CF
     *
     * Should return:
     * 10 VS5 Total: $17.98 Packs: 2 x 5 $8.99
     * 14 MB11 Total: $54.8 Packs: 3 x 2 $9.95, 1 x 8 $24.95
     * 13 CF Total: $25.85 Packs: 1 x 3 $5.95, 2 x 5 $9.95
     */
    @Test
    public void shouldPassOutputSampleScenario() throws ProductNotFoundException, OrderNotFilledException {
        Order order;
        List<Order> orderList = orderService.processOrder(Arrays.asList("10 VS5", "14 MB11", "13 CF"));

        order = orderList.get(0);
        assertEquals(new Order(10, "VS5", toBigDecimal(17.98)), order);
        assertEquals(new Pack(2, 5, toBigDecimal(8.99)), order.getPackList().get(0));

        order = orderList.get(1);
        assertEquals(new Order(14, "MB11", toBigDecimal(54.80)), order);
        assertEquals(new Pack(3, 2, toBigDecimal(9.95)), order.getPackList().get(0));
        assertEquals(new Pack(1, 8, toBigDecimal(24.95)), order.getPackList().get(1));

        order = orderList.get(2);
        assertEquals(new Order(13, "CF", toBigDecimal(25.85)), order);
        assertEquals(new Pack(1, 3, toBigDecimal(5.95)), order.getPackList().get(0));
        assertEquals(new Pack(2, 5, toBigDecimal(9.95)), order.getPackList().get(1));
    }

    /**
     * Should throw a ProductNotFoundException when receive a product that doesn't exist
     */
    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowProductNotFoundException() throws ProductNotFoundException, OrderNotFilledException {
        orderService.processOrder(Arrays.asList("1 MB"));
    }

    /**
     * Should throw a IllegalArgumentException when receive the product quantity and code without a space between them
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() throws ProductNotFoundException, OrderNotFilledException {
       orderService.processOrder(Arrays.asList("1MB"));
    }

    /**
     * Should throw a OrderNotFilledException when an order couldn't be filled
     */
    @Test(expected = OrderNotFilledException.class)
    public void shouldThrowOrderNotFilledException() throws ProductNotFoundException, OrderNotFilledException {
        orderService.processOrder(Arrays.asList("1 MB11"));
    }

    /**
     * Ordering seventeen croissants should return:
     * 3 Packs
     * 1 Pack of 3 croissants
     * 1 Pack of 5 croissants
     * 1 Pack of 9 croissants
     * and cost 32.89
     */
    @Test
    public void shouldCalculateSeventeenCroissantOrder() {
        orderService.calculateMinimumAmountOfPacks(seventeenCroissantOrder.getProductQuantity(), seventeenCroissantOrder, croissantProduct.getPackList());
        assertEquals(3, seventeenCroissantOrder.getPackList().size());
        assertEquals(1, seventeenCroissantOrder.getPackList().get(0).getPackQuantity());
        assertEquals(3, seventeenCroissantOrder.getPackList().get(0).getProductQuantity());
        assertEquals(1, seventeenCroissantOrder.getPackList().get(1).getPackQuantity());
        assertEquals(5, seventeenCroissantOrder.getPackList().get(1).getProductQuantity());
        assertEquals(1, seventeenCroissantOrder.getPackList().get(2).getPackQuantity());
        assertEquals(9, seventeenCroissantOrder.getPackList().get(2).getProductQuantity());
        assertEquals(toBigDecimal(  32.89f), seventeenCroissantOrder.getTotalPrice());
    }

    /**
     * Ordering fourteen blueberry muffins should return:
     * 2 Packs
     * 3 Packs of 2 blueberry muffins
     * 1 Pack of 8 blueberry muffins
     * and cost 54.8
     */
    @Test
    public void shouldCalculateFourteenBlueberryMuffinOrder() {
        orderService.calculateMinimumAmountOfPacks(fourteenBlueberryMuffinOrder.getProductQuantity(), fourteenBlueberryMuffinOrder, blueberryMuffinProduct.getPackList());
        assertEquals(2, fourteenBlueberryMuffinOrder.getPackList().size());
        assertEquals(3, fourteenBlueberryMuffinOrder.getPackList().get(0).getPackQuantity());
        assertEquals(2, fourteenBlueberryMuffinOrder.getPackList().get(0).getProductQuantity());
        assertEquals(1, fourteenBlueberryMuffinOrder.getPackList().get(1).getPackQuantity());
        assertEquals(8, fourteenBlueberryMuffinOrder.getPackList().get(1).getProductQuantity());
        assertEquals(toBigDecimal(54.8), fourteenBlueberryMuffinOrder.getTotalPrice());
    }
}