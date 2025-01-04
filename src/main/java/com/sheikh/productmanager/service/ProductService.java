package com.sheikh.productmanager.service;

import com.sheikh.productmanager.repository.ProductRepository;
import com.sheikh.productmanager.dto.ProductDTO;
import com.sheikh.productmanager.exception.ProductNotFoundException;
import com.sheikh.productmanager.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    RedisService redisService;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //maps a Product entity to a ProductDTO
    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        );
    }
    //Received DTO but saved as Entity
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setCategory(productDTO.getCategory());

        //save to database
        Product savedProduct = productRepository.save(newProduct);
        ProductDTO responseDTO = mapToDTO(savedProduct);

        redisService.save("product:" + savedProduct.getId(), responseDTO);
        return responseDTO;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new ProductNotFoundException("No Product found");
        }
        // Fetch all products and map them to ProductDTO
        return products.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        ProductDTO cachedProduct = redisService.get("product:" + id, ProductDTO.class);
        if (cachedProduct != null) {
            return cachedProduct;
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found with ID: " + id));

        ProductDTO responseDTO = mapToDTO(product);
        redisService.save("product:" + id, responseDTO);
        return responseDTO;
    }

    public List<Product> showProducts(){
        List<Product> p = productRepository.findAll();
        return p;
    }


    public List<ProductDTO> getProductByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory()
                ))
                .collect(Collectors.toList());
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found with ID: " + id));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(productDTO.getCategory());

        Product updatedProduct = productRepository.save(existingProduct);
        ProductDTO responseDTO = mapToDTO(updatedProduct);

        redisService.save("product:" + id, responseDTO);
        return responseDTO;
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found with ID: " + id));

        productRepository.delete(product);
        redisService.delete("product:" + id);
    }
}
