package com.example.mall.controller;

import com.example.mall.model.Product;
import com.example.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products/{productId}")
    public ResponseEntity<Product> getById(@PathVariable Integer productId){
        Product product = productService.getById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("products")
    public ResponseEntity<Product> insertProduct(@RequestBody @Valid Product product){
        Product insertProduct = productService.insertProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(insertProduct);
    }
}
