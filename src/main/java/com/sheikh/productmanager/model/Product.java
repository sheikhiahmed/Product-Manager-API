package com.sheikh.productmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sheikh.productmanager.dto.ProductDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Name is required.")
    private String name;

    private String description;

    private double price;

    @NotNull(message = "Category is required.")
    private String category;

    public Product(ProductDTO productDTO) {
        this.name = productDTO.getName();
        this.description = productDTO.getDescription();
        this.price = productDTO.getPrice();
        this.category = productDTO.getCategory();
    }


}