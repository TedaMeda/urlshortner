package com.gj4.urlshortner.exception;

public class ExpiredLinkException extends RuntimeException {
    public ExpiredLinkException(String message) {
        super(message);
    }
}
