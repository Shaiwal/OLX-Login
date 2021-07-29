package com.test.olx.login.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionController {
	
	@ExceptionHandler(value = UserAlreadyPresentException.class)
	public ResponseEntity<String> handleUserAlreadyPresentException(RuntimeException e, WebRequest request) {
		String respMsg = e.getMessage();
		return new ResponseEntity<String>(respMsg, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = DatabaseTransactionException.class)
	public ResponseEntity<String> handleDatabaseTransactionException(RuntimeException e, WebRequest request) {
		String respMsg = e.getMessage();
		return new ResponseEntity<String>(respMsg, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<String> handleBadCredentialsException(RuntimeException e, WebRequest request) {
		String respMsg = e.getMessage();
		return new ResponseEntity<String>(respMsg, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

}
