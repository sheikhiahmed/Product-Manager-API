package com.sheikh.productmanager.service;

import com.sheikh.productmanager.dao.ProductRepository;
import com.sheikh.productmanager.dto.ProductDTO;
import com.sheikh.productmanager.exception.ProductNotFoundException;
import com.sheikh.productmanager.model.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }
    // Received DTO but saved as Entity
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product newProduct = new Product(productDTO);
        Product savedProduct = productRepository.save(newProduct);

        // Save product to cache
        redisTemplate.opsForHash().put("products", savedProduct.getId().toString(), savedProduct);

        // Convert Model back to DTO
        return new ProductDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new ProductNotFoundException("No Product found");
        }
        // Fetch all products and map them to ProductDTO
        return products.stream().map(ProductDTO::new).toList();
    }

    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductById(Long id) {
        // Fetch product from cache
        Product productFromCache = (Product) redisTemplate.opsForHash().get("products", id.toString());

        if (productFromCache != null) {
            return new ProductDTO(productFromCache); // Return cached product as DTO
        }

        // Fetch product from DB if not found in cache
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            redisTemplate.opsForHash().put("products", id.toString(), product.get()); // Store product in cache
            return new ProductDTO(product.get());
        } else {
            throw new ProductNotFoundException("No Product Found with id " + id);
        }
    }
    public List<Product> showProducts(){
        return productRepository.findAll();
    }


    public List<ProductDTO> getProductByCategory(String category) {
        return productRepository.findByCategory(category).stream().map(ProductDTO::new).toList();
    }

    @CachePut(value = "products", key = "#id")
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(productDTO.getCategory());

        Product updatedProduct = productRepository.save(existingProduct);

        // Update cache with new product details
        redisTemplate.opsForHash().put("products", updatedProduct.getId().toString(), updatedProduct);

        return new ProductDTO(updatedProduct);
    }


    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);

        // Remove product from cache
        redisTemplate.opsForHash().delete("products", id.toString());
    }

}
