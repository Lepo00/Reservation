package it.anoki.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javassist.NotFoundException;

@ControllerAdvice
public class GenericErrorController {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Exception> handleException(Exception ex) {
		return ResponseEntity.status(500).build();
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Exception> handleAccessDeniedException(Exception ex) {
		return ResponseEntity.status(403).build();
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Exception> handleNotFoundException(Exception ex) {
		return ResponseEntity.status(404).build();
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Exception> handleBadCredentialException(Exception ex) {
		return ResponseEntity.status(400).build();
	}

}
