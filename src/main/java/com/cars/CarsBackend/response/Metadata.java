package com.cars.CarsBackend.response;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class Metadata implements Serializable {
    private String description;
    private String timeStamp;
    private Status status = new Status();
}
