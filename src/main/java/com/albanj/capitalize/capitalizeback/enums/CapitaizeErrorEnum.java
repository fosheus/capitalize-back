package com.albanj.capitalize.capitalizeback.enums;

public enum CapitaizeErrorEnum {

	POST_NOT_FOUND(1, "Le post d'id {0} n'existe pas");

	public final Integer code;
	public final String text;

	private CapitaizeErrorEnum(Integer code, String text) {
		this.code = code;
		this.text = text;
	}

}
