package com.albanj.capitalize.capitalizeback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CapitalizeForbiddenException extends RuntimeException {

	public CapitalizeForbiddenException() {
	}

	public CapitalizeForbiddenException(String message) {
		super(message);
	}
}
