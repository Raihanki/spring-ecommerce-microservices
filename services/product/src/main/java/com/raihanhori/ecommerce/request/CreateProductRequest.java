package com.raihanhori.ecommerce.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateProductRequest {
	
	@NotBlank
	@Size(max = 200)
	private String name;
	
	@NotBlank
	private String desctiption;
	
	@NotNull
	private Integer availableQuantity;
	
	@NotNull
	private BigDecimal price;
	
	@NotNull
	private Integer categoryId;
	
}
	