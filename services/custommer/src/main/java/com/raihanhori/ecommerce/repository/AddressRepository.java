package com.raihanhori.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raihanhori.ecommerce.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	Optional<Address> findByCustomer_Id(Integer id);

}
