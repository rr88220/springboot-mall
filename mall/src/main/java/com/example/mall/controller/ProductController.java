package com.example.mall.controller;

import com.example.mall.dto.ProductRequest;
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
    public ResponseEntity<Product> insertProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer id = productService.insertProduct(productRequest);
        Product product = productService.getById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity updateProduct(@PathVariable Integer productId,
                                        @RequestBody @Valid ProductRequest productRequest){
        Product product1 = productService.getById(productId);
        if(product1 != null){
            productService.updateProduct(productId,productRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
