package com.example.mall.dao.impl;

import com.example.mall.model.Product;
import com.example.mall.dao.ProductDao;
import com.example.mall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getById(Integer productId) {
        String sql = "SELECT productid,productname,category,image_url,price,stock,description,created_date,last_modified_date FROM product WHERE productid = :id";

        Map<String,Object> map = new HashMap<>();
        map.put("id",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if(productList.size()>0){
            return productList.get(0);
        }else{
            return null;
        }
    }
}
