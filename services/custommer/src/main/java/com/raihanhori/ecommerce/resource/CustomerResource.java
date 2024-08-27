package com.raihanhori.ecommerce.resource;

import com.raihanhori.ecommerce.entity.Address;

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
public class CustomerResource {
	
	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private Address address;
	
}
