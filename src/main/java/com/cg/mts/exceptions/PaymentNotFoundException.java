package com.cg.mts.exceptions;

public class PaymentNotFoundException extends RuntimeException {
	
	public PaymentNotFoundException(String msg) {
		super(msg);
	}
}
