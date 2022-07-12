package com.example.mall.service.impl;

import com.example.mall.dao.OrderDao;
import com.example.mall.dao.ProductDao;
import com.example.mall.dao.UserDao;
import com.example.mall.dto.BuyItem;
import com.example.mall.dto.CreateOrderRequest;
import com.example.mall.dto.OrderQueryParams;
import com.example.mall.model.Order;
import com.example.mall.model.OrderItem;
import com.example.mall.model.Product;
import com.example.mall.model.User;
import com.example.mall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    UserDao userDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);
        for(Order order:orderList){
            order.setOrderItemList(orderDao.getOrderItemsByOrderId(order.getOrderId()));
        }
        return orderList;
    }

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {
        return orderDao.countOrders(orderQueryParams);
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //檢查userId存在
        User user = userDao.getUserById(userId);
        if(user == null){
            log.warn("該userId {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<OrderItem> orderItemList = new ArrayList<>();
        int totalAmount = 0;
        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getById(buyItem.getProductId());

            //檢查商品是否存在
            if(product == null){
                log.warn("該productId {} 不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //檢查庫存是否足夠
            if(product.getStock()<buyItem.getQuantity()){
                log.warn("庫存不足，商品 {} 僅剩 {} 個",product.getProductName(),product.getStock());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());

            //計算總金額
            totalAmount += product.getPrice()*buyItem.getQuantity();

            //轉換成list
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(product.getPrice()*buyItem.getQuantity());
            orderItemList.add(orderItem);
        }

        //創建訂單
        int orderId = orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;
    }
}
