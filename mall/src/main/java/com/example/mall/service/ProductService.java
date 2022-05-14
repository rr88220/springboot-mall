package com.example.mall.service;

import com.example.mall.dto.ProductQueryParams;
import com.example.mall.dto.ProductRequest;
import com.example.mall.model.Product;

import java.util.List;

public interface ProductService {
    public Product getById(Integer productId);
    public List<Product> getProducts(ProductQueryParams productQueryParams);
    public Integer insertProduct(ProductRequest productRequest);
    public void updateProduct(Integer productId,ProductRequest productRequest);
    public void deleteProduct(Integer productId);
}
