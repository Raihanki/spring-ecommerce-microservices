package com.raihanhori.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.raihanhori.ecommerce.helper.SuccessResponseHelper;
import com.raihanhori.ecommerce.request.OrderRequest;
import com.raihanhori.ecommerce.resource.OrderResource;
import com.raihanhori.ecommerce.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public SuccessResponseHelper<String> store(@RequestBody OrderRequest request) {
		orderService.createOrder(request);
		
		return SuccessResponseHelper.<String>builder()
				.status(201)
				.data("order successfully created")
				.build();
	}
	
	@GetMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public SuccessResponseHelper<OrderResource> show(@PathVariable Integer orderId) {
		OrderResource order = orderService.getById(orderId);
		
		return SuccessResponseHelper.<OrderResource>builder()
				.status(201)
				.data(order)
				.build();
	}
	
}
