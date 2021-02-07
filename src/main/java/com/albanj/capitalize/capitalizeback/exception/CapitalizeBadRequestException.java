package com.albanj.capitalize.capitalizeback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CapitalizeBadRequestException extends RuntimeException{

    public CapitalizeBadRequestException(){}

    public CapitalizeBadRequestException(String message) {
        super(message);
    }
}
