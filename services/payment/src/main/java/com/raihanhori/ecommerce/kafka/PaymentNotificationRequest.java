package com.raihanhori.ecommerce.kafka;

import java.math.BigDecimal;

import com.raihanhori.ecommerce.entity.PaymentMethod;

public record PaymentNotificationRequest(
		String order_reference,
		BigDecimal amount,
		PaymentMethod paymentMethod,
		String customerFirstName,
		String customerLastName,
		String customerEmail
) {

}
