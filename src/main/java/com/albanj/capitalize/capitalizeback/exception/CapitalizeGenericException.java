package com.albanj.capitalize.capitalizeback.exception;

import com.albanj.capitalize.capitalizeback.enums.CapitalizeErrorEnum;

import lombok.Getter;

@Getter
public class CapitalizeGenericException extends Exception {

	private Integer code;
	private String text;

	public CapitalizeGenericException() {
		super();
		this.code = CapitalizeErrorEnum.INTERNAL_ERROR.code;
		this.text = CapitalizeErrorEnum.INTERNAL_ERROR.text;
	}

	public CapitalizeGenericException(String message) {
		super(message);
		this.code = CapitalizeErrorEnum.INTERNAL_ERROR.code;
		this.text = CapitalizeErrorEnum.INTERNAL_ERROR.text;
	}

	public CapitalizeGenericException(Throwable throwable) {
		super(throwable);
		this.code = CapitalizeErrorEnum.INTERNAL_ERROR.code;
		this.text = CapitalizeErrorEnum.INTERNAL_ERROR.text;
	}

	public CapitalizeGenericException(String message, Throwable throwable) {
		super(message, throwable);
		this.code = CapitalizeErrorEnum.INTERNAL_ERROR.code;
		this.text = CapitalizeErrorEnum.INTERNAL_ERROR.text;
	}

	public CapitalizeGenericException(Integer code, String text) {
		super();
		this.code = code;
		this.text = text;
	}

	public CapitalizeGenericException(Integer code, String text, Throwable t) {
		super(t);
		this.code = code;
		this.text = text;
	}

	public CapitalizeGenericException(Integer code, String text, String message) {
		super(message);
		this.code = code;
		this.text = text;
	}

	public CapitalizeGenericException(Integer code, String text, String message, Throwable t) {
		super(message, t);
		this.code = code;
		this.text = text;
	}
}
