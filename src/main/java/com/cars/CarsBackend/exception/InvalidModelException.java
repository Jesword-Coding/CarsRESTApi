package com.cars.CarsBackend.exception;

public class InvalidModelException extends RuntimeException {
    public InvalidModelException(final String message, final String model) {
        super(String.format(message, model));
    }
}
