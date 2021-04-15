package com.cg.mts.exceptions;

public class CourierIdExistsException extends RuntimeException {
	
	private String operation;
	public CourierIdExistsException(String operation,String message) {
		super(message);
		this.operation=operation;
	}
	public String getOperation() {
		return operation;
	}
}
