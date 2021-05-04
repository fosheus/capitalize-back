package com.albanj.capitalize.capitalizeback.exception;

public class CapitalizeNotFoundException extends CapitalizeGenericException {

    public CapitalizeNotFoundException() {
        super();
    }

    public CapitalizeNotFoundException(Integer code, String text) {
        super(code, text);
    }

    public CapitalizeNotFoundException(Integer code, String text, Throwable t) {
        super(code, text, t);
    }

    public CapitalizeNotFoundException(Integer code, String text, String message) {
        super(code, text, message);
    }

    public CapitalizeNotFoundException(Integer code, String text, String message, Throwable t) {
        super(code, text, message, t);
    }

}
