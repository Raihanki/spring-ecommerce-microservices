package com.raihanhori.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raihanhori.ecommerce.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
