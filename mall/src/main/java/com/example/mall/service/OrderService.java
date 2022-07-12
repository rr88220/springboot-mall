package com.example.mall.service;

import com.example.mall.dto.CreateOrderRequest;
import com.example.mall.model.Order;

public interface OrderService {

    public Order getOrderById(Integer orderId);

    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
