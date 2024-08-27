package com.raihanhori.ecommerce.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "first_name", length = 200, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 200, nullable = true)
	private String lastName;
	
	@Column(length = 200, unique = true, nullable = false)
	private String email;
	
	@OneToOne(mappedBy = "customer", cascade = CascadeType.REMOVE)
	private Address address;
	
	@Column(name = "created_at")
	@CreatedDate
	private Instant createdAt;
	
	@Column(name = "updated_at")
	@LastModifiedDate
	private Instant updatedAt;
	
}
