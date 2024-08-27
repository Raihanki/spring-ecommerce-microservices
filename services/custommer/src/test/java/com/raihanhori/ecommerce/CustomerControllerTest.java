package com.raihanhori.ecommerce;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raihanhori.ecommerce.entity.Address;
import com.raihanhori.ecommerce.entity.Customer;
import com.raihanhori.ecommerce.helper.SuccessResponseHelper;
import com.raihanhori.ecommerce.repository.AddressRepository;
import com.raihanhori.ecommerce.repository.CustomerRepository;
import com.raihanhori.ecommerce.request.CreateCustomerRequest;
import com.raihanhori.ecommerce.request.UpdateCustomerRequest;
import com.raihanhori.ecommerce.resource.CustomerResource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@AfterEach
	void tearDown() {
		addressRepository.deleteAll();
		customerRepository.deleteAll();
	}
	
	@BeforeEach
	void setUp() {
		addressRepository.deleteAll();
		customerRepository.deleteAll();
	}
	
	@Test
	void testCreateSuccess() throws Exception {
		CreateCustomerRequest request = new CreateCustomerRequest();
		request.setEmail("test@example.com");
		request.setFirstName("Shiina");
		request.setLastName("Mashiro");
		request.setHouseNumber("A12");
		request.setStreet("bandung");
		request.setZipCode("1234");
		
		mockMvc.perform(
				post("/api/v1/customers")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
		).andExpect(status().isCreated())
		.andDo(result -> {
			SuccessResponseHelper<String> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
			
			assertNotNull(response.getData());
			
			Customer createdCustomer = customerRepository.findByEmail(request.getEmail()).orElse(null);
			assertNotNull(createdCustomer);
			
			assertEquals("test@example.com", createdCustomer.getEmail());
			assertEquals("Shiina", createdCustomer.getFirstName());
			assertEquals("Mashiro", createdCustomer.getLastName());
			assertEquals("A12", createdCustomer.getAddress().getHouseNumber());
			assertEquals("bandung", createdCustomer.getAddress().getStreet());
			assertEquals("1234", createdCustomer.getAddress().getZipCode());
		});
	}
	
	@Test
	void testCustomerExists() throws Exception {
		Customer customer = new Customer();
		customer.setEmail("tes@test.com");
		customer.setFirstName("tes");
		customer.setLastName("test");
		customerRepository.save(customer);
		
		mockMvc.perform(
				get("/api/v1/customers/exists/" + customer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		.andDo(result -> {
			SuccessResponseHelper<Boolean> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
			
			assertTrue(response.getData());
		});
	}
	
	@Test
	void testCustomerNotExists() throws Exception {
		mockMvc.perform(
				get("/api/v1/customers/exists/99999")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		.andDo(result -> {
			SuccessResponseHelper<Boolean> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
			
			assertFalse(response.getData());
		});
	}
	
	@Test
	void testUpdateSuccess() throws Exception {
		Customer customer = new Customer();
		customer.setEmail("tes@test.com");
		customer.setFirstName("tes");
		customer.setLastName("test");
		customerRepository.save(customer);
		
		Address address = new Address();
		address.setHouseNumber("A12");
		address.setStreet("bandung");
		address.setZipCode("1234");
		address.setCustomer(customer);
		addressRepository.save(address);
		
		UpdateCustomerRequest request = new UpdateCustomerRequest();
		request.setEmail("updated@example.com");
		request.setFirstName("tes-updated");
		request.setLastName("test-updated");
		request.setHouseNumber("B12");
		request.setStreet("bandung-updated");
		request.setZipCode("12345");
		
		mockMvc.perform(
				put("/api/v1/customers/" + customer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
		).andExpect(status().isOk())
		.andDo(result -> {
			SuccessResponseHelper<String> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
			
			assertNotNull(response.getData());
			
			Customer oldCustomer = customerRepository.findByEmail(customer.getEmail()).orElse(null);
			assertNull(oldCustomer);
			
			Customer updatedCustomer = customerRepository.findByEmail(request.getEmail()).orElse(null);
			assertNotNull(updatedCustomer);
			
			assertEquals("updated@example.com", updatedCustomer.getEmail());
			assertEquals("tes-updated", updatedCustomer.getFirstName());
			assertEquals("test-updated", updatedCustomer.getLastName());
			assertEquals("B12", updatedCustomer.getAddress().getHouseNumber());
			assertEquals("bandung-updated", updatedCustomer.getAddress().getStreet());
			assertEquals("12345", updatedCustomer.getAddress().getZipCode());
		});
	}
	
	@Test
	void testGetCustomerSuccess() throws Exception {
		Customer customer = new Customer();
		customer.setEmail("tes@test.com");
		customer.setFirstName("tes");
		customer.setLastName("test");
		customerRepository.save(customer);
		
		Address address = new Address();
		address.setHouseNumber("A12");
		address.setStreet("bandung");
		address.setZipCode("1234");
		address.setCustomer(customer);
		addressRepository.save(address);
		
		mockMvc.perform(
				get("/api/v1/customers/" + customer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		.andDo(result -> {
			SuccessResponseHelper<CustomerResource> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
			
			assertNotNull(response.getData());
			
			assertEquals("tes@test.com", response.getData().getEmail());
			assertEquals("tes", response.getData().getFirstName());
			assertEquals("test", response.getData().getLastName());
			assertEquals("A12", response.getData().getAddress().getHouseNumber());
			assertEquals("bandung", response.getData().getAddress().getStreet());
			assertEquals("1234", response.getData().getAddress().getZipCode());
		});
	}
	
}
