package com.example.mall.service;

import com.example.mall.model.Product;

public interface ProductService {
    public Product getById(Integer productId);
    public Product insertProduct(Product product);
    public void updateProduct(Integer productId,Product product);
}
