package com.raihanhori.ecommerce.service;

import java.util.List;

import com.raihanhori.ecommerce.request.OrderLineRequest;
import com.raihanhori.ecommerce.resource.OrderLineResource;

public interface OrderLineService {
	
	void create(OrderLineRequest request);
	
	List<OrderLineResource> findAllByOrder(Integer orderId);
	
}
