package com.example.mall.controller;

import com.example.mall.dto.CreateOrderRequest;
import com.example.mall.dto.OrderQueryParams;
import com.example.mall.model.Order;
import com.example.mall.service.OrderService;
import com.example.mall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                       @RequestParam(defaultValue = "10") @Max(100) @Min(0) Integer limit,
                                       @RequestParam(defaultValue = "0") @Min(0) Integer offset){
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        //取得orderList
        List<Order> orderList= orderService.getOrders(orderQueryParams);

        //取得總數
        Integer total = orderService.countOrders(orderQueryParams);

        //分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(orderList);

        return ResponseEntity.status(200).body(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        Integer orderId = orderService.createOrder(userId,createOrderRequest);
        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(201).body(order);
    }


}
