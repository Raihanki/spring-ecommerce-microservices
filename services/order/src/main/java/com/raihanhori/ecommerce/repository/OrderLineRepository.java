package com.raihanhori.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raihanhori.ecommerce.entity.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> { 
	
	List<OrderLine> findAllByOrder_Id(Integer order_id);
	
}
