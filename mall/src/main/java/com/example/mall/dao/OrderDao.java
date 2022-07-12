package com.example.mall.dao;

import com.example.mall.dto.CreateOrderRequest;
import com.example.mall.model.Order;
import com.example.mall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    public Order getOrderById(Integer orderId);

    public List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    public Integer createOrder(Integer userId, Integer totalAmount);

    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

}
