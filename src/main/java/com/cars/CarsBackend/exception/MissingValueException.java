package com.cars.CarsBackend.exception;

public class MissingValueException extends RuntimeException{
    public MissingValueException(final String message) {
        super(String.format(message));
    }
}
