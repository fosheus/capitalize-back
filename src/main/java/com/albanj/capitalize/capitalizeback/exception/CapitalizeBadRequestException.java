package com.albanj.capitalize.capitalizeback.exception;

public class CapitalizeBadRequestException extends CapitalizeGenericException {

    public CapitalizeBadRequestException() {
        super();
    }

    public CapitalizeBadRequestException(Integer code, String text) {
        super(code, text);
    }

    public CapitalizeBadRequestException(Integer code, String text, Throwable t) {
        super(code, text, t);
    }

    public CapitalizeBadRequestException(Integer code, String text, String message) {
        super(code, text, message);
    }

    public CapitalizeBadRequestException(Integer code, String text, String message, Throwable t) {
        super(code, text, message, t);
    }

}
