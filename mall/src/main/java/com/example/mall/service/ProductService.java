package com.example.mall.service;

import com.example.mall.dto.ProductRequest;
import com.example.mall.model.Product;

public interface ProductService {
    public Product getById(Integer productId);
    public Integer insertProduct(ProductRequest productRequest);
    public void updateProduct(Integer productId,ProductRequest productRequest);
    public void deleteProduct(Integer productId);
}
