package com.cars.CarsBackend.controller;

import com.cars.CarsBackend.model.Car;
import com.cars.CarsBackend.response.CarResponse;
import org.springframework.http.ResponseEntity;

public interface CarController {
    ResponseEntity<CarResponse> getAllCars();
    ResponseEntity<CarResponse> addCar(Car car);
    ResponseEntity<CarResponse> updateCar(String vin, Car car);
    ResponseEntity<CarResponse> getCarByVin(String vin);
    ResponseEntity<CarResponse> deleteCar(String vin);
    ResponseEntity<CarResponse> getCarsByMake(String make);
    ResponseEntity<CarResponse> getCarsByMakeAndModel(String make, String model);
    ResponseEntity<CarResponse> getCarsByState(String state);
    ResponseEntity<CarResponse> getCarByColor(String color);
}
