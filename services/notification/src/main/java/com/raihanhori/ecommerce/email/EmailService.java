package com.raihanhori.ecommerce.email;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.raihanhori.ecommerce.kafka.order.Product;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Async
	public void sendPaymentSuccessEmail(
			String targetMail,
			String customerName,
			BigDecimal amount,
			String orderReference
	) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(
					mimeMessage, 
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, 
					StandardCharsets.UTF_8.name()
		);
		
		messageHelper.setFrom("support.ecom-raihanhori@gmail.com");
		
		final String templateName = EmailTemplate.PAYMENT_CONFIRMATION.getTemplate();
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("customerName", customerName);
		variables.put("amount", amount);
		variables.put("orderReference", orderReference);
		
		Context context = new Context();
		context.setVariables(variables);
		
		messageHelper.setSubject(EmailTemplate.PAYMENT_CONFIRMATION.getSubject());
		
		try {
			String htmlTemplate = templateEngine.process(templateName, context);
			messageHelper.setText(htmlTemplate, true);
			messageHelper.setTo(targetMail);
			mailSender.send(mimeMessage);
			
			log.info(String.format("INFO - Email successfully send to %s", targetMail));
		} catch (MessagingException e) {
			log.warn(String.format("WARNING - Cannot send email to {}", targetMail));
		}
	}
	
	@Async
	public void sendOrderConfirmationEmail(
			String targetMail,
			String customerName,
			BigDecimal amount,
			String orderReference,
			List<Product> products
	) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(
					mimeMessage, 
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, 
					StandardCharsets.UTF_8.name()
		);
		
		messageHelper.setFrom("support.ecom-raihanhori@gmail.com");
		
		final String templateName = EmailTemplate.ORDER_CONFIRMATION.getTemplate();
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("customerName", customerName);
		variables.put("totalAmount", amount);
		variables.put("orderReference", orderReference);
		variables.put("products", products);
		
		Context context = new Context();
		context.setVariables(variables);
		
		messageHelper.setSubject(EmailTemplate.ORDER_CONFIRMATION.getSubject());
		
		try {
			String htmlTemplate = templateEngine.process(templateName, context);
			messageHelper.setText(htmlTemplate, true);
			messageHelper.setTo(targetMail);
			mailSender.send(mimeMessage);
			
			log.info(String.format("INFO - Email successfully send to %s", targetMail));
		} catch (MessagingException e) {
			log.warn(String.format("WARNING - Cannot send email to {}", targetMail));
		}
	}
	
}
