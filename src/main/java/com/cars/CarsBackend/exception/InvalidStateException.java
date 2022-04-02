package com.cars.CarsBackend.exception;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(final String message, final String state) {
        super(String.format(message, state));
    }
}
