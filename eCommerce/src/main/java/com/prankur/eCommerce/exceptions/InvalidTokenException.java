package com.prankur.eCommerce.exceptions;

public class InvalidTokenException extends RuntimeException
{
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
    }
}
