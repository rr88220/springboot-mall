package com.example.mall.dao.impl;

import com.example.mall.dto.ProductRequest;
import com.example.mall.model.Product;
import com.example.mall.dao.ProductDao;
import com.example.mall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;
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

    @Override
    public Integer insertProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(productname,category,image_url,price,stock,description,created_date,last_modified_date) VALUES (:productName,:category,:image_url,:price,:stock,:description,:created_date,:last_modified_date)";

        Map<String,Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().name());
        map.put("image_url",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("created_date",now);
        map.put("last_modified_date",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int id = keyHolder.getKey().intValue();
        return id;
    }
    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET productname=:productName,category=:category,image_url=:image_url,price=:price,stock=:stock,description=:description,last_modified_date=:last_modified_date WHERE productid = :id";

        Map<String,Object> map = new HashMap<>();
        map.put("id",productId);
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().name());
        map.put("image_url",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        map.put("last_modified_date",new Date());

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE productid = :id";

        Map<String,Object> map = new HashMap<>();
        map.put("id",productId);

        namedParameterJdbcTemplate.update(sql,map);
    }
}
