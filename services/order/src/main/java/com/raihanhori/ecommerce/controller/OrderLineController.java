package com.raihanhori.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raihanhori.ecommerce.helper.SuccessResponseHelper;
import com.raihanhori.ecommerce.resource.OrderLineResource;
import com.raihanhori.ecommerce.service.OrderLineService;

@RestController
@RequestMapping("/api/v1/order-lines")
public class OrderLineController {	
	
	@Autowired
	private OrderLineService orderLineService;
	
	@GetMapping(path = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessResponseHelper<List<OrderLineResource>> findAllByOrderId(@PathVariable Integer orderId) {
		List<OrderLineResource> orderLines = orderLineService.findAllByOrder(orderId);
		
		return SuccessResponseHelper.<List<OrderLineResource>>builder()
				.status(200)
				.data(orderLines)
				.build();
	}

}
