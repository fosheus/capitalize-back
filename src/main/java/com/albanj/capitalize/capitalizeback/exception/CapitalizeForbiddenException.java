package com.albanj.capitalize.capitalizeback.exception;

public class CapitalizeForbiddenException extends CapitalizeGenericException {

	public CapitalizeForbiddenException() {
		super();
	}

	public CapitalizeForbiddenException(Integer code, String text) {
		super(code, text);
	}

	public CapitalizeForbiddenException(Integer code, String text, Throwable t) {
		super(code, text, t);
	}

	public CapitalizeForbiddenException(Integer code, String text, String message) {
		super(code, text, message);
	}

	public CapitalizeForbiddenException(Integer code, String text, String message, Throwable t) {
		super(code, text, message, t);
	}

}
