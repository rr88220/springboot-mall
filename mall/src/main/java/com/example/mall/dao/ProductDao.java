package com.example.mall.dao;

import com.example.mall.model.Product;

public interface ProductDao {
    public Product getById(Integer productId);
    public Product insertProduct(Product product);
    public void updateProduct(Integer productId,Product product);
}
