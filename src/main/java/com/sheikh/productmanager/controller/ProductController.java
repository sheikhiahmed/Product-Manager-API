package com.sheikh.productmanager.controller;

import com.sheikh.productmanager.dto.ProductDTO;
import com.sheikh.productmanager.exception.ProductNotFoundException;
import com.sheikh.productmanager.model.Product;
import com.sheikh.productmanager.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    //Receive DTO and createProduct it as Entity
    @PostMapping()
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO productDTO){
        ProductDTO save = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);

    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }

    //Admin use only
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        return ResponseEntity.ok().body(product);
    }

    //Admin use only
    @GetMapping("/show")
    public ResponseEntity<List<Product>> showProducts(){
        List<Product> p = productService.showProducts();
        return ResponseEntity.ok().body(p);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable ("category") String category){
        List<ProductDTO> products = productService.getProductByCategory(category);
        return ResponseEntity.ok().body(products);

    }
    //Admin and users
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDTO){
        ProductDTO updateProduct = productService.updateProduct(id,productDTO);
        return ResponseEntity.ok(updateProduct);
    }
    //Admin muzahid.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable ("id") Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
