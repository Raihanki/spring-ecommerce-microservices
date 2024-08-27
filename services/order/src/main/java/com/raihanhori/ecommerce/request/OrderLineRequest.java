package com.raihanhori.ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineRequest {
	
	private Integer orderId;
	
	private Integer productId;
	
	private Integer quantity;
	
}
