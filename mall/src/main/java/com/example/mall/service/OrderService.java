package com.example.mall.service;

import com.example.mall.dto.CreateOrderRequest;
import com.example.mall.dto.OrderQueryParams;
import com.example.mall.model.Order;

import java.util.List;

public interface OrderService {

    public Order getOrderById(Integer orderId);

    public List<Order> getOrders(OrderQueryParams orderQueryParams);

    public Integer countOrders(OrderQueryParams orderQueryParams);

    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
