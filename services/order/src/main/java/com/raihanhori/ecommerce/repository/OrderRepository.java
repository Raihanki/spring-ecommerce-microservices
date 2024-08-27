package com.raihanhori.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raihanhori.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
