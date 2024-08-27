package com.raihanhori.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raihanhori.ecommerce.entity.Order;
import com.raihanhori.ecommerce.entity.OrderLine;
import com.raihanhori.ecommerce.repository.OrderLineRepository;
import com.raihanhori.ecommerce.request.OrderLineRequest;
import com.raihanhori.ecommerce.resource.OrderLineResource;

@Service
public class OrderLineServiceImpl implements OrderLineService {
	
	@Autowired
	private OrderLineRepository orderLineRepository;

	@Override
	public void create(OrderLineRequest request) {
		OrderLine orderLine = new OrderLine();
		orderLine.setOrder(
				Order.builder().id(request.getOrderId()).build()
		);
		orderLine.setQuantity(request.getQuantity());
		orderLine.setProductId(request.getProductId());
		orderLineRepository.save(orderLine);
	}

	@Override
	public List<OrderLineResource> findAllByOrder(Integer orderId) {
		List<OrderLineResource> orderLines = orderLineRepository.findAllByOrder_Id(orderId)
				.stream().map(orderLine -> 
					OrderLineResource.builder()
						.id(orderLine.getId())
						.quantity(orderLine.getQuantity())
						.build()
				).toList();
		
		return orderLines;
	}

}
