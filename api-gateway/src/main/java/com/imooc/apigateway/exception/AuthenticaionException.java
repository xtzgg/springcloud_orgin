package com.imooc.apigateway.exception;

public class AuthenticaionException extends RuntimeException {
    public AuthenticaionException() {
    }
    public AuthenticaionException(String message) {
        super(message);
    }
}
