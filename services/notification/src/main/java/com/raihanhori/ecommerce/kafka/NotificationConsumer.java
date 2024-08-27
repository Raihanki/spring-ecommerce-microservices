package com.raihanhori.ecommerce.kafka;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.raihanhori.ecommerce.email.EmailService;
import com.raihanhori.ecommerce.entity.Notification;
import com.raihanhori.ecommerce.entity.NotificationType;
import com.raihanhori.ecommerce.kafka.order.OrderConfirmation;
import com.raihanhori.ecommerce.kafka.payment.PaymentConfirmation;
import com.raihanhori.ecommerce.repository.NotificationRepository;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationConsumer {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private EmailService emailService;
	
	@KafkaListener(topics = "payment-topic")
	public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
		log.info(String.format("Consuming message from payment-topic:: %s", paymentConfirmation));
		
		Notification notification = new Notification();
		notification.setNotificationDate(Instant.now());
		notification.setType(NotificationType.PAYMENT_CONFIRMATION);
		
		notificationRepository.save(notification);
		
		//TODO: send email
		String customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
		emailService.sendPaymentSuccessEmail(
				paymentConfirmation.customerEmail(), 
				customerName, 
				paymentConfirmation.amount(), 
				paymentConfirmation.order_reference()
		);
	}
	
	@KafkaListener(topics = "order-topic")
	public void consumeOrderSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
		log.info(String.format("Consuming message from order-topic:: %s", orderConfirmation));
		
		Notification notification = new Notification();
		notification.setNotificationDate(Instant.now());
		notification.setType(NotificationType.ORDER_CONFIRMATION);
		
		notificationRepository.save(notification);
		
		//TODO: send email
		String customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
		emailService.sendPaymentSuccessEmail(
				orderConfirmation.customer().email(), 
				customerName, 
				orderConfirmation.totalAmount(), 
				orderConfirmation.orderReference()
		);
	}

}
