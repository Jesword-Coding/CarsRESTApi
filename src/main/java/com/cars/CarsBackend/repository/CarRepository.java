package com.cars.CarsBackend.repository;

import com.cars.CarsBackend.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    Car findByVin(String vin);
    List<Car> findByMake(String make);
    List<Car> findByMakeAndModel(String make, String model);
    List<Car> findByColor(String color);
    List<Car> findByState(String State);
    void deleteByVin(String vin);
}
