package com.example.mall.service;

import com.example.mall.dto.CreateOrderRequest;
import com.example.mall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
