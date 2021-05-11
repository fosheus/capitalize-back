package com.albanj.capitalize.capitalizeback.enums;

public enum CapitalizeErrorEnum {

	INTERNAL_ERROR(1, "Erreur interne : veuillez vous rapprocher d'un administrateur"),
	NOT_FOUND(11, "La ressource demandée n'existe pas"),
	FORBIDDEN(7, "Vous n'avez pas les droits pour accéder à modifier la ressource"),

	POST_NOT_FOUND(2, "Le post d'id {0} n'existe pas"), FILE_NOT_FOUND(4, "Il n'existe pas de fichier avec l'id {0}"),
	SIGNUP_FORM_INVALID(3,
			"Le formulaire de souscription n'est pas valide, il existe déjà un utilisateur avec le même email ou login de connexion"),
	INVALID_ARGUMENT(5, "Le paramètre {0} ne peut pas avoir la valeur [{1}]"),
	FILE_TYPE_INVALID(6, "Le type de fichier {0} ne correspond pas au type attendu {1}"),
	FORMAT_ERROR_POST_FILE_PATH(8, "Le chemin ou nom de fichier [{0}] n'est pas au bon format"),
	EMPTY_UPLOADED_FILE(9, "Le fichier uploadé [{0}] est vide"),
	EXISTING_FILE_WITH_SAME_PATH(10, "Il existe déjà un fichier avec le même chemin {0}");

	public final Integer code;
	public final String text;

	private CapitalizeErrorEnum(Integer code, String text) {
		this.code = code;
		this.text = text;
	}

}
