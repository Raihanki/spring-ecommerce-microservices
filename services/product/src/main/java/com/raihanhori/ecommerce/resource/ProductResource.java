package com.raihanhori.ecommerce.resource;

import java.math.BigDecimal;
import java.time.Instant;

import com.raihanhori.ecommerce.entity.Category;

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
public class ProductResource {
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Integer availableQuantity;
	
	private BigDecimal price;
	
	private Category category;
	
	private Instant createdAt;
	
}
