package com.example.mall.service.impl;

import com.example.mall.dao.ProductDao;
import com.example.mall.model.Product;
import com.example.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getById(Integer productId) {
        return productDao.getById(productId);
    }

    @Override
    public Product insertProduct(Product product) {
        return productDao.insertProduct(product);
    }

    @Override
    public void updateProduct(Integer productId, Product product) {
        productDao.updateProduct(productId,product);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
