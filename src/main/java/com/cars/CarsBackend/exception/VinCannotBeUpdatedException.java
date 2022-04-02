package com.cars.CarsBackend.exception;

public class VinCannotBeUpdatedException extends RuntimeException{
    public VinCannotBeUpdatedException(final String message) {
        super(String.format(message));
    }
}
