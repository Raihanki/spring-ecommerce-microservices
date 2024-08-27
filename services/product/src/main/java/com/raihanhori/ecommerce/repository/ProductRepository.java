package com.raihanhori.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raihanhori.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Optional<Product> findFirstByName(String name);
	
}
