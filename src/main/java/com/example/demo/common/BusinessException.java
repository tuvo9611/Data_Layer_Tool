package com.example.demo.common;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super(CommonExceptionConstants.MSG_INVALID_REQUEST);
	}

	public BusinessException(String msg) {
		super(msg);
	}
	
	public BusinessException(String msg, Throwable t) {
		super(msg, t);
	}
}