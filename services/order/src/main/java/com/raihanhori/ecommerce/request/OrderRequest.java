package com.raihanhori.ecommerce.request;

import java.math.BigDecimal;
import java.util.List;

import com.raihanhori.ecommerce.entity.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class OrderRequest {

	@NotBlank
	private String reference;
	
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	private PaymentMethod paymentMethod;
	
	@NotNull
	private Integer customerId;
	
	@NotEmpty
	private List<PurchaseRequest> products;
	
}
