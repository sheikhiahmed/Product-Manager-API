package com.sheikh.productmanager.controller;

import com.sheikh.productmanager.dto.ProductDTO;
import com.sheikh.productmanager.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    //Receive DTO and createProduct it as Entity
    @PostMapping("/createProduct")
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO productDTO){
        ProductDTO save = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);

    }

}
