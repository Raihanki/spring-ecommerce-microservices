package com.raihanhori.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raihanhori.ecommerce.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
}
