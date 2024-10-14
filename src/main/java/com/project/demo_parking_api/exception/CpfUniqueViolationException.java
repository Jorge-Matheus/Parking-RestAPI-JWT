package com.project.demo_parking_api.exception;

public class CpfUniqueViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	
	public CpfUniqueViolationException(String message) {
		super(message);
	}
}
