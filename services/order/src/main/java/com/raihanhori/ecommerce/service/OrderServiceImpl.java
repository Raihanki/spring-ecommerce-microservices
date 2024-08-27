package com.raihanhori.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.raihanhori.ecommerce.client.CustomerClient;
import com.raihanhori.ecommerce.client.PaymentClient;
import com.raihanhori.ecommerce.client.ProductClient;
import com.raihanhori.ecommerce.client.response.CustomerResponse;
import com.raihanhori.ecommerce.client.response.PurchaseResponse;
import com.raihanhori.ecommerce.entity.Order;
import com.raihanhori.ecommerce.exception.BusinessException;
import com.raihanhori.ecommerce.exception.ErrorCreatePurchaseException;
import com.raihanhori.ecommerce.helper.ValidationHelper;
import com.raihanhori.ecommerce.kafka.OrderConfirmation;
import com.raihanhori.ecommerce.kafka.OrderProducer;
import com.raihanhori.ecommerce.repository.OrderRepository;
import com.raihanhori.ecommerce.request.OrderLineRequest;
import com.raihanhori.ecommerce.request.OrderRequest;
import com.raihanhori.ecommerce.request.PaymentRequest;
import com.raihanhori.ecommerce.request.PurchaseRequest;
import com.raihanhori.ecommerce.resource.OrderResource;

import feign.FeignException;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private CustomerClient customerClient;
	
	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderLineService orderLineService;
	
	@Autowired
	private ValidationHelper validationHelper;
	
	@Autowired
	private OrderProducer orderProducer;
	
	@Autowired
	private PaymentClient paymentClient;
	
	@Transactional
	@Override
	public void createOrder(OrderRequest request) {
		validationHelper.validate(request);
		
		CustomerResponse customer = customerClient.findCustomerById(request.getCustomerId())
				.orElseThrow(() -> new BusinessException("Cannot create order:: customer does not exists"));
	
		List<PurchaseResponse> products = null;
		try {
			products = productClient.createPurchase(request.getProducts());
		} catch (FeignException e) {
			HttpStatus status = HttpStatus.valueOf(e.status());
			throw new ErrorCreatePurchaseException("Error create purchase when calling product service: " + e.getMessage(), status);
		}
		
		Order order = new Order();
		order.setCustomerId(customer.id());
		order.setPaymentMethod(request.getPaymentMethod());
		order.setReference(request.getReference());
		order.setTotalAmount(request.getAmount());
		orderRepository.save(order);
		
		for (PurchaseRequest purchaseRequest : request.getProducts()) {
			orderLineService.create(
					OrderLineRequest.builder()
					.orderId(order.getId())
					.productId(purchaseRequest.getProductId())
					.quantity(purchaseRequest.getQuantity())
					.build()
			);
		}
		
		paymentClient.requestOrderPayment(
				PaymentRequest.builder()
				.amount(request.getAmount())
				.paymentMethod(request.getPaymentMethod())
				.orderId(order.getId())
				.orderReference(order.getReference())
				.Customer(customer)
				.build()
		);
		
		orderProducer.sendOrderConfirmation(
				new OrderConfirmation(
						order.getReference(), 
						order.getTotalAmount(), 
						order.getPaymentMethod(), 
						customer, 
						products
				)
		);
	}

	@Override
	public OrderResource getById(Integer orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));
		
		return toOrderResource(order);
	}
	
	private OrderResource toOrderResource(Order order) {
		return OrderResource.builder()
				.id(order.getId())
				.reference(order.getReference())
				.amount(order.getTotalAmount())
				.paymentMethod(order.getPaymentMethod())
				.customerId(order.getCustomerId())
				.build();
	}

}
