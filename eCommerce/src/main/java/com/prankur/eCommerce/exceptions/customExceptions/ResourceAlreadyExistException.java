package com.prankur.eCommerce.exceptions.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ResourceAlreadyExistException extends RuntimeException
{
    public ResourceAlreadyExistException(String message) {
        super(message);
    }

    public ResourceAlreadyExistException() {
    }
}
