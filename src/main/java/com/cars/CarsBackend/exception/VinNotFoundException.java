package com.cars.CarsBackend.exception;

public class VinNotFoundException extends RuntimeException {
    public VinNotFoundException(final String message, final String vin) {
        super(String.format(message, vin));
    }
}
