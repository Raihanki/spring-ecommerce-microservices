package com.raihanhori.ecommerce.resource;

import java.math.BigDecimal;

import com.raihanhori.ecommerce.entity.PaymentMethod;

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
public class OrderResource {

	private Integer id;
	
	private String reference;
	
	private BigDecimal amount;
	
	private PaymentMethod paymentMethod;
	
	private Integer customerId;
	
}
