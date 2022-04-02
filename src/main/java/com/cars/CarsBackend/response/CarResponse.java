package com.cars.CarsBackend.response;

public abstract class CarResponse {
    private final Metadata metadata = new Metadata();

    public Metadata getMetadata() {
        return metadata;
    }

}
