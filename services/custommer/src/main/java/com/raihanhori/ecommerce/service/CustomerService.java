package com.raihanhori.ecommerce.service;

import org.springframework.data.domain.Page;

import com.raihanhori.ecommerce.request.CreateCustomerRequest;
import com.raihanhori.ecommerce.request.GetCustomerRequest;
import com.raihanhori.ecommerce.request.UpdateCustomerRequest;
import com.raihanhori.ecommerce.resource.CustomerResource;

public interface CustomerService {
	
	void create(CreateCustomerRequest request);
	
	void update(UpdateCustomerRequest request);
	
	Page<CustomerResource> getAll(GetCustomerRequest request);
	
	boolean existsById(Integer id);
	
	CustomerResource getById(Integer id);
	
	void delete(Integer id);
	
}
