package com.example.mall.dao.impl;

import com.example.mall.dao.OrderDao;
import com.example.mall.dto.OrderQueryParams;
import com.example.mall.model.Order;
import com.example.mall.model.OrderItem;
import com.example.mall.rowmapper.OrderItemRowMapper;
import com.example.mall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id,user_id,total_amount,created_date,last_modified_date FROM `order` WHERE order_id = :orderId";
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        if(orderList != null){
            return orderList.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT order_item_id,order_id,product_id,quantity,amount,productname,image_url " +
                "FROM order_item LEFT JOIN product ON product_id = productid " +
                "WHERE order_id = :orderId";
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id,user_id,total_amount,created_date,last_modified_date FROM `order` " +
                "WHERE user_id = :userId ORDER BY last_modified_date LIMIT :limit OFFSET :offset";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",orderQueryParams.getUserId());
        map.put("limit",orderQueryParams.getLimit());
        map.put("offset",orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());

        return orderList;
    }

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT COUNT(*) FROM `order` WHERE user_id = :userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",orderQueryParams.getUserId());

        return namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id,total_amount,created_date,last_modified_date) VALUES (:userId,:totalAmount,:createdDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);
        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item (order_id,product_id,quantity,amount) VALUES (:orderId,:productId,:quantity,:amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
        for(int i = 0 ; i<orderItemList.size() ; i++){
            OrderItem orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId",orderId);
            parameterSources[i].addValue("productId",orderItem.getProductId());
            parameterSources[i].addValue("quantity",orderItem.getQuantity());
            parameterSources[i].addValue("amount",orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
    }


}
