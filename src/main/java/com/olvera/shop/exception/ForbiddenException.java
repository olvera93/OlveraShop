package com.olvera.shop.exception;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException() {
        super("message");
    }
}
