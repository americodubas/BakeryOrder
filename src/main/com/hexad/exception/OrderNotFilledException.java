package com.hexad.exception;

import com.hexad.model.Order;

import java.util.List;

public class OrderNotFilledException extends Exception {

    public OrderNotFilledException(String s) {
        super("Order not filled! " + s);
    }
}
