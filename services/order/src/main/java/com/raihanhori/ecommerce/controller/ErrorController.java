package com.raihanhori.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.raihanhori.ecommerce.exception.BusinessException;
import com.raihanhori.ecommerce.exception.ErrorCreatePurchaseException;
import com.raihanhori.ecommerce.helper.ErrorResponseHelper;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponseHelper<Object>> constraintViolation(ConstraintViolationException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(
						ErrorResponseHelper.<Object>builder()
						.status(HttpStatus.BAD_REQUEST.value())
						.data(exception.getConstraintViolations())
						.build()
				);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorResponseHelper<String>> responseStatus(ResponseStatusException exception) {
		return ResponseEntity.status(exception.getStatusCode())
				.body(
						ErrorResponseHelper.<String>builder()
						.status(HttpStatus.BAD_REQUEST.value())
						.data(exception.getReason())
						.build()
				);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponseHelper<String>> businessException(BusinessException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(
						ErrorResponseHelper.<String>builder()
						.status(HttpStatus.BAD_REQUEST.value())
						.data(exception.getMessage())
						.build()
				);
	}
	
	@ExceptionHandler(ErrorCreatePurchaseException.class)
	public ResponseEntity<ErrorResponseHelper<String>> errorCreatePurchase(ErrorCreatePurchaseException exception) {
		return ResponseEntity.status(exception.getStatus())
				.body(
						ErrorResponseHelper.<String>builder()
						.status(exception.getStatus().value())
						.data(exception.getMessage())
						.build()
				);
	}
	
}