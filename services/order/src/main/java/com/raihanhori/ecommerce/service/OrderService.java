package com.raihanhori.ecommerce.service;

import com.raihanhori.ecommerce.request.OrderRequest;
import com.raihanhori.ecommerce.resource.OrderResource;

public interface OrderService {

	void createOrder(OrderRequest request);
	
	OrderResource getById(Integer orderId);
	
}
