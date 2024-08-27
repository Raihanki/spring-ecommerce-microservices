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
public class SuccessResponseHelper<T> {
	
	private Integer status;
	
	private T data;
	
}