package com.cars.CarsBackend.response;

import com.cars.CarsBackend.model.Car;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDeleteResponse extends CarResponse {
    private Car car;
}
