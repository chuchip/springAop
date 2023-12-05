package com.profesorp.springaop.exception;


public class LowMemoryException extends RuntimeException {

    private static final long serialVersionUID = 285565931479778831L;

    public LowMemoryException(String message) {
        super(message);
    }
}