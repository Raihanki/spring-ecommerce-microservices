package com.raihanhori.ecommerce.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequest {

	@NotNull
	private Integer productId;
	
	@NotNull
	private Integer quantity;
	
}
