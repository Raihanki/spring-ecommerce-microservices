package com.raihanhori.ecommerce.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequest {
	
	@NotNull
	private Integer productId;
	
	@NotNull
	@Min(value = 1)
	private Integer availableQuantity;
	
}
	