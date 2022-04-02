package com.cars.CarsBackend.exception;

public class InvalidYearException extends RuntimeException {
    public InvalidYearException(final String message, final Integer year) {
        super(String.format(message, year));
    }
}
