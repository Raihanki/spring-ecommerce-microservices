package com.raihanhori.ecommerce.kafka.payment;

import java.math.BigDecimal;

import com.raihanhori.ecommerce.kafka.order.PaymentMethod;

public record PaymentConfirmation(
		
		String order_reference,
		
		BigDecimal amount,
		
		PaymentMethod paymentMethod,
		
		String customerFirstname,
		
		String customerLastname,
		
		String customerEmail
		
) {}
