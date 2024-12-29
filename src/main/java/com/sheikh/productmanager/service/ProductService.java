package com.sheikh.productmanager.service;

import com.sheikh.productmanager.dao.ProductRepository;
import com.sheikh.productmanager.dto.ProductDTO;
import com.sheikh.productmanager.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    //Received DTO but saved as Entity
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setCategory(productDTO.getCategory());

        //save to database
        Product saveProduct = productRepository.save(newProduct);

        //convert Model back to DTO
        ProductDTO responseDTO = new ProductDTO();
        responseDTO.setName(saveProduct.getName());
        responseDTO.setDescription(saveProduct.getDescription());
        responseDTO.setPrice(saveProduct.getPrice());
        responseDTO.setCategory(saveProduct.getCategory());

        return responseDTO;


    }

    public List<ProductDTO> getAllProducts() {
        // Fetch all products and map them to ProductDTO
        return productRepository.findAll().stream()
                .map(product -> new ProductDTO(
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory()
                ))
                .collect(Collectors.toList());
    }
}
