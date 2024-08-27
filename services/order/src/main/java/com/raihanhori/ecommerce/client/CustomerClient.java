package com.raihanhori.ecommerce.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.raihanhori.ecommerce.client.response.CustomerResponse;

@FeignClient(
		name = "customer-service", 
		url = "${application.config.customer-url}"
)
public interface CustomerClient {
	
	@GetMapping(path = "/{customerId}")
	Optional<CustomerResponse> findCustomerById(@PathVariable Integer customerId);
	
}
