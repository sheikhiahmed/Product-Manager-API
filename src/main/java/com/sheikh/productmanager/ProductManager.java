package com.sheikh.productmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProductManager {

	public static void main(String[] args) {
		SpringApplication.run(ProductManager.class, args);
	}

}
