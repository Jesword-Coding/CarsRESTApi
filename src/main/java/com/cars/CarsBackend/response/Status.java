package com.cars.CarsBackend.response;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class Status implements Serializable {
    private int statusCode;
    private boolean success;
    private String statusDescription;
}
