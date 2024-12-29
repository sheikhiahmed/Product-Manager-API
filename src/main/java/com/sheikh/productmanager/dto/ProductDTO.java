package com.sheikh.productmanager.dto;

import com.sheikh.productmanager.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotNull(message = "Name is required.")
    private String name;

    private String description;

    private double price;

    @NotNull(message = "Category is required.")
    private String category;

}
