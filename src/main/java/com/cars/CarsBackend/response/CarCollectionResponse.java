package com.cars.CarsBackend.response;

import com.cars.CarsBackend.model.Car;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CarCollectionResponse extends CarResponse{
   private Integer recordsFetched = 0;
   private List<Car> carsList;
}
