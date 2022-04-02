package com.cars.CarsBackend.service;

import com.cars.CarsBackend.model.Car;
import java.util.List;

public interface CarService {
    Car addCar(Car car);
    Car getCarByVin(String vin);
    Car patchUpdateCar(String vin, Car car);
    List<Car> getAllCars();
    List<Car> getCarsByMake(String make);
    List<Car> getCarsByMakeAndModel(String make, String model);
    List<Car> getCarsByColor(String color);
    List<Car> getCarsByState(String state);
    Car deleteByVin(String vin);
}
