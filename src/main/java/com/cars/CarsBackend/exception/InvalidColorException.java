package com.cars.CarsBackend.exception;

public class InvalidColorException extends RuntimeException {
    public InvalidColorException(final String message, final String color) {
        super(String.format(message, color));
    }
}
