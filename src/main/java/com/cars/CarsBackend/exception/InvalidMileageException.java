package com.cars.CarsBackend.exception;

public class InvalidMileageException extends RuntimeException{
    public InvalidMileageException(final String message, final Double mileage) {
        super(String.format(message, mileage));
    }
}
