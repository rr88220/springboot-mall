package com.example.mall.dao;

import com.example.mall.dto.CreateOrderRequest;
import com.example.mall.model.Order;
import com.example.mall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    void updateStock(Integer productId,Integer stock);

}
