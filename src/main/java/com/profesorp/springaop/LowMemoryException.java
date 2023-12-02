package com.profesorp.springaop;


public class LowMemoryException extends RuntimeException {

    private static final long serialVersionUID = 285565931479778831L;

    public LowMemoryException(String message) {
        super(message);
    }
}