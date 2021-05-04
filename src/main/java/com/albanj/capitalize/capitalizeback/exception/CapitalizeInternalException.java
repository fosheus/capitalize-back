package com.albanj.capitalize.capitalizeback.exception;

public class CapitalizeInternalException extends CapitalizeGenericException {

	public CapitalizeInternalException() {
		super();
	}

	public CapitalizeInternalException(String message) {
		super(message);
	}

	public CapitalizeInternalException(Integer code, String text) {
		super(code, text);
	}

	public CapitalizeInternalException(Integer code, String text, Throwable t) {
		super(code, text, t);
	}

	public CapitalizeInternalException(Integer code, String text, String message) {
		super(code, text, message);
	}

	public CapitalizeInternalException(Integer code, String text, String message, Throwable t) {
		super(code, text, message, t);
	}
}
