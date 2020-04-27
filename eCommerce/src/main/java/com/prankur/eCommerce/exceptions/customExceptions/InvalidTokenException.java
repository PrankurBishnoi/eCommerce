package com.prankur.eCommerce.exceptions.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenException extends RuntimeException
{
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
    }
}
