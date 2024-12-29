package com.sheikh.productmanager;

import com.sheikh.productmanager.dto.ProductDTO;
import com.sheikh.productmanager.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductManagerTests {

	@Autowired
	private ProductService productService;

	@Test
	void contextLoads() {
		assertThat(productService).isNotNull();
	}
	@Test
	void createProductTest() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Test Product");
		productDTO.setDescription("A sample product description");
		productDTO.setPrice(10.99);
		productDTO.setCategory("Test Category");

		ProductDTO createdProduct = productService.createProduct(productDTO);

		assertThat(createdProduct).isNotNull();
		assertThat(createdProduct.getName()).isEqualTo("Test Product");
	}

}
