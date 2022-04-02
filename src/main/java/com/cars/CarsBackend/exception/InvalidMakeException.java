package com.cars.CarsBackend.exception;

public class InvalidMakeException extends RuntimeException {
    public InvalidMakeException(final String message, final String make) {
        super(String.format(message, make));
    }
}
