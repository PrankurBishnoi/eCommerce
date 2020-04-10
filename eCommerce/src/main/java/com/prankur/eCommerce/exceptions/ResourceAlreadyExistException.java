package com.prankur.eCommerce.exceptions;

public class ResourceAlreadyExistException extends RuntimeException
{
    public ResourceAlreadyExistException(String message) {
        super(message);
    }

    public ResourceAlreadyExistException() {
    }
}
