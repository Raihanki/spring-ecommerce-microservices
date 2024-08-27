package com.raihanhori.ecommerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.raihanhori.ecommerce.request.PaymentRequest;

@FeignClient(
		name = "product-service",
		url = "${application.config.payment-url}"
)
public interface PaymentClient {
	
	@PostMapping
	String requestOrderPayment(@RequestBody PaymentRequest request);
	
}
