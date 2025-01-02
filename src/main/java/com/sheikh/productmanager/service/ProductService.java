package com.sheikh.productmanager.service;

import com.sheikh.productmanager.dao.ProductRepository;
import com.sheikh.productmanager.dto.ProductDTO;
import com.sheikh.productmanager.exception.ProductNotFoundException;
import com.sheikh.productmanager.model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new ProductNotFoundException("No Product found");
        }
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

    public Product getProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
       if(product.isPresent()){
           return product.get();
    } else {
           throw new ProductNotFoundException("No Product Found with id" + id);
       }
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

    public ProductDTO updateProduct(Long id, @Valid ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("product not found Exception with ID: "+id));
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(productDTO.getCategory());
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductDTO.builder()
                .name(existingProduct.getName())
                .description(existingProduct.getDescription())
                .price(existingProduct.getPrice())
                .category(existingProduct.getCategory()).build();
    }
}
