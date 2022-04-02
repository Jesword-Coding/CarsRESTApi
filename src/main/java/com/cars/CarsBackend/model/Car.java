package com.cars.CarsBackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "CARS")
public class Car implements Serializable {

    @Id
    @Column(name = "VIN", nullable = false, length = 20, updatable = false)
    private String vin;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "MAKE", nullable = false, length = 20)
    private String make;

    @Column(name = "MODEL", nullable = false, length = 20)
    private String model;

    @Column(name = "YEAR", nullable = false, length = 4)
    private Integer year;

    @Column(name = "MILEAGE")
    private Double mileage;

    @Column(name = "COLOR", length = 15)
    private String color;

    @Column(name = "STATE", length = 15)
    private String state;

    public Car(){}

    public Car(String vin, Double price,
               String make, String model,
               Integer year, Double mileage,
               String color, String state) {
        this.vin = vin;
        this.price = price;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.state = state;
    }
}
