package com.raihanhori.ecommerce;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raihanhori.ecommerce.entity.Category;
import com.raihanhori.ecommerce.entity.Product;
import com.raihanhori.ecommerce.helper.SuccessResponseHelper;
import com.raihanhori.ecommerce.repository.CategoryRepository;
import com.raihanhori.ecommerce.repository.ProductRepository;
import com.raihanhori.ecommerce.request.CreateProductRequest;
import com.raihanhori.ecommerce.request.PurchaseRequest;
import com.raihanhori.ecommerce.resource.ProductResource;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Category category;
	
	@BeforeEach
	void setUp() {
		category = categoryRepository.findById(1).orElse(null);
		
		productRepository.deleteAll();
	}
	
	@AfterEach
	void tearDown() {
		productRepository.deleteAll();
	}
	
	@Test
	void testCreateSuccess() throws Exception {
		CreateProductRequest request = new CreateProductRequest();
		request.setName("Test Product");
		request.setDesctiption("Test Description");
		request.setCategoryId(category.getId());
		request.setAvailableQuantity(100);
		request.setPrice(new BigDecimal("100.00"));
		
		mockMvc.perform(
				post("/api/v1/products")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
		).andExpect(status().isCreated())
		.andDo(result -> {
			SuccessResponseHelper<String> response =
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
		
			assertNotNull(response.getData());
			
			Product createdProduct = productRepository.findFirstByName(request.getName()).orElse(null);
			assertNotNull(createdProduct);
			assertEquals("Test Product", createdProduct.getName());
			assertEquals("Test Description", createdProduct.getDesctiption());
			assertEquals(createdProduct.getCategory().getId(), category.getId());
			assertEquals(new BigDecimal("100.00"), createdProduct.getPrice());
			assertEquals(100, createdProduct.getAvailableQuantity());
		});
	}
	
	@Test
	void testGetByIdSuccess() throws Exception {
		Product product = new Product();
		product.setName("Test Product");
		product.setDesctiption("Test Description");
		product.setCategory(category);
		product.setAvailableQuantity(100);
		product.setPrice(new BigDecimal("100.00"));
		productRepository.save(product);
		
		mockMvc.perform(
				get("/api/v1/products/" + product.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		.andDo(result -> {
			SuccessResponseHelper<ProductResource> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
			
			assertNotNull(response.getData());
			assertEquals("Test Product", response.getData().getName());
			assertEquals("Test Description", response.getData().getDescription());
			assertEquals(response.getData().getCategory().getId(), category.getId());
			assertEquals(new BigDecimal("100.00"), response.getData().getPrice());
			assertEquals(100, response.getData().getAvailableQuantity());
		});
	}
	
	@Test
	void testPurchaseSuccess() throws Exception {
		Product product = new Product();
		product.setName("Test Product");
		product.setDesctiption("Test Description");
		product.setCategory(category);
		product.setAvailableQuantity(100);
		product.setPrice(new BigDecimal("100.00"));
		productRepository.save(product);
		
		List<PurchaseRequest> request = new ArrayList<PurchaseRequest>();
		
		PurchaseRequest eachRequest = new PurchaseRequest();
		eachRequest.setProductId(product.getId());
		eachRequest.setAvailableQuantity(2);
		
		request.add(eachRequest);
		
		mockMvc.perform(
				post("/api/v1/products/purchase")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
		).andExpect(status().isOk())
		.andDo(result -> {
			SuccessResponseHelper<List<ProductResource>> response =
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
		
			assertNotNull(response.getData());
			assertEquals(1, response.getData().size());
		});
	}
	
}
