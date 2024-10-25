package com.project.demo_parking_api.exception;

public class CodigoUniqueViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	
	public CodigoUniqueViolationException(String msg) {
		super(msg);
	}
}
