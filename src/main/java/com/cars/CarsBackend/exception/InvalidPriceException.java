package com.cars.CarsBackend.exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(final String message, final Double price) {
        super(String.format(message, price));
    }
}
