package com.raihanhori.ecommerce.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UpdateCustomerRequest {
	
	@JsonIgnore
	private Integer id;

	@NotBlank
	@Size(max = 200)
	private String firstName;
	
	@NotBlank
	@Size(max = 200)
	private String lastName;
	
	@Size(max = 200)
	@Email
	private String email;
	
	@NotBlank
	private String street;
	
	@NotBlank
	@Size(max = 100)
	private String houseNumber;
	
	@NotBlank
	@Size(max = 100)
	private String zipCode;
	
}
