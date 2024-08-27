package com.raihanhori.ecommerce.request;

import java.math.BigDecimal;

import com.raihanhori.ecommerce.entity.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PaymentRequest {
	
	@NotNull
	private BigDecimal amount;

	@NotNull
	private PaymentMethod paymentMethod;
	
	@NotNull
	private Integer orderId;
	
	@NotBlank
	private String orderReference;
	
	private CustomerRequest Customer;
	
}
