package com.raihanhori.ecommerce.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.raihanhori.ecommerce.client.response.CustomerResponse;
import com.raihanhori.ecommerce.client.response.PurchaseResponse;
import com.raihanhori.ecommerce.entity.PaymentMethod;

public record OrderConfirmation(
		String reference,
		BigDecimal amount,
		PaymentMethod paymentMethod,
		CustomerResponse customer,
		List<PurchaseResponse> products
) {

}
