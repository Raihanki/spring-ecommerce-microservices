package com.raihanhori.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raihanhori.ecommerce.entity.Payment;
import com.raihanhori.ecommerce.entity.PaymentStatus;
import com.raihanhori.ecommerce.kafka.NotificationProducer;
import com.raihanhori.ecommerce.kafka.PaymentNotificationRequest;
import com.raihanhori.ecommerce.repository.PaymentRepository;
import com.raihanhori.ecommerce.request.PaymentRequest;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private NotificationProducer notificationProducer;

	@Override
	public void createPayment(PaymentRequest request) {
		Payment payment = new Payment();
		payment.setAmount(request.getAmount());
		payment.setOrderId(request.getOrderId());
		payment.setPaymentMethod(request.getPaymentMethod());
		payment.setStatus(PaymentStatus.PENDING);
		paymentRepository.save(payment);
		
		notificationProducer.sendNotification(
				new PaymentNotificationRequest(
						request.getOrderReference(),
						request.getAmount(), 
						request.getPaymentMethod(), 
						request.getCustomer().getFirstName(), 
						request.getCustomer().getLastName(), 
						request.getCustomer().getEmail()
				)
		);
	}

}
