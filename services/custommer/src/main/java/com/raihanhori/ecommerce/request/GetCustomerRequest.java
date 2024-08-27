package com.raihanhori.ecommerce.request;

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
public class GetCustomerRequest {

	private Integer limit;
	
	private Integer page;
	
}
