package com.raihanhori.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.raihanhori.ecommerce.helper.PaginationData;
import com.raihanhori.ecommerce.helper.PaginationResponseHelper;
import com.raihanhori.ecommerce.helper.SuccessResponseHelper;
import com.raihanhori.ecommerce.request.CreateCustomerRequest;
import com.raihanhori.ecommerce.request.GetCustomerRequest;
import com.raihanhori.ecommerce.request.UpdateCustomerRequest;
import com.raihanhori.ecommerce.resource.CustomerResource;
import com.raihanhori.ecommerce.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public PaginationResponseHelper<List<CustomerResource>> getAll(
			@RequestParam(defaultValue = "10", required = false) Integer limit,
			@RequestParam(defaultValue = "1", required = false) Integer page
	) {
		GetCustomerRequest request = new GetCustomerRequest();
		request.setLimit(limit);
		request.setPage(page);
		
		Page<CustomerResource> customers = customerService.getAll(request);
		
		return PaginationResponseHelper.<List<CustomerResource>>builder()
				.status(200)
				.data(customers.getContent())
				.options(
						PaginationData.builder()
							.current_page(page)
							.total_data(customers.getNumberOfElements())
							.total_data_per_page(customers.getSize())
							.total_page(customers.getTotalPages())
							.build()
				).build();
	}
	
	@GetMapping(path = "/exists/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessResponseHelper<Boolean> checkExists(@PathVariable Integer customerId) {
		
		 boolean customer = customerService.existsById(customerId);
		
		return SuccessResponseHelper.<Boolean>builder()
				.status(200)
				.data(customer)
				.build();
	}
	
	@GetMapping(path = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessResponseHelper<CustomerResource> getOne(@PathVariable Integer customerId) {
		 CustomerResource customer = customerService.getById(customerId);
		
		return SuccessResponseHelper.<CustomerResource>builder()
				.status(200)
				.data(customer)
				.build();
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessResponseHelper<String> store(@RequestBody CreateCustomerRequest request) {
		customerService.create(request);
		
		return SuccessResponseHelper.<String>builder()
				.status(201)
				.data("custommer succcessfully created")
				.build();
	}
	
	@PutMapping(path = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessResponseHelper<String> update(@RequestBody UpdateCustomerRequest request, @PathVariable Integer customerId) {
		request.setId(customerId);
		customerService.update(request);
		
		return SuccessResponseHelper.<String>builder()
				.status(200)
				.data("custommer succcessfully updated")
				.build();
	}
}
