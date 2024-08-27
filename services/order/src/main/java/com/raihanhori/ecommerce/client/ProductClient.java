package com.raihanhori.ecommerce.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.raihanhori.ecommerce.client.response.PurchaseResponse;
import com.raihanhori.ecommerce.request.PurchaseRequest;

@FeignClient(
		name = "product-service",
		url = "${application.config.product-url}"
)
public interface ProductClient {
	
	@PostMapping(
			path = "/purchase", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	List<PurchaseResponse> createPurchase(@RequestBody List<PurchaseRequest> request);
	
}
