package com.raihanhori.ecommerce.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponseHelper<T> {
	
	private Integer status;
	
	private T data;
	
	private PaginationData options;
	
}
