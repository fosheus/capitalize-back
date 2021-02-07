package com.albanj.capitalize.capitalizeback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class CapitalizeNotFoundException extends RuntimeException {

    public CapitalizeNotFoundException() {
    }

    public CapitalizeNotFoundException(String message) {
        super(message);
    }
}
