package com.example.mall.service.impl;

import com.example.mall.dao.ProductDao;
import com.example.mall.dto.ProductQueryParams;
import com.example.mall.dto.ProductRequest;
import com.example.mall.model.Product;
import com.example.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getById(Integer productId) {
        return productDao.getById(productId);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams){ return productDao.getProducts(productQueryParams); };

    @Override
    public Integer countProducts(ProductQueryParams productQueryParams){ return productDao.countProducts(productQueryParams); };
    @Override
    public Integer insertProduct(ProductRequest productRequest) {
        return productDao.insertProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
