package com.test.olx.login.exception;

public class DatabaseTransactionException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public DatabaseTransactionException(String message) {
		super(message);
	}
	

}
