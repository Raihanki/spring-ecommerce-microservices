package com.raihanhori.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class ErrorCreatePurchaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	
	public ErrorCreatePurchaseException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
