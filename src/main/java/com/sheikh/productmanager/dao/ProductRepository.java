package com.sheikh.productmanager.dao;

import com.sheikh.productmanager.dto.ProductDTO;
import com.sheikh.productmanager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductRepository extends JpaRepository<Product,Long> {
    // Repository method
    List<Product> findByCategory(String category);

}
