package com.sheikh.productmanager.scheduler;

import com.sheikh.productmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductCountScheduler {

    @Autowired
    private ProductRepository productRepository;

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void countProducts() {
        long productCount = productRepository.count();
        System.out.println("Total number of products: " + productCount);
    }
}

