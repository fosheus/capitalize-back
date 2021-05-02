package com.albanj.capitalize.capitalizeback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CapitalizeInternalException extends RuntimeException {

	public CapitalizeInternalException() {
	}

	public CapitalizeInternalException(String message) {
		super(message);
	}
}
