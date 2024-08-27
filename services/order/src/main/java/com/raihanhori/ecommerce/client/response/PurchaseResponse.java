package com.raihanhori.ecommerce.client.response;

import java.math.BigDecimal;
import java.time.Instant;

public record PurchaseResponse(

		Integer id,

		String name,

		String description,

		Integer availableQuantity,

		BigDecimal price,

		Instant createdAt

) {
}
