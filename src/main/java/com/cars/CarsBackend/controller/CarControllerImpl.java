package com.cars.CarsBackend.controller;

import com.cars.CarsBackend.model.Car;
import com.cars.CarsBackend.response.*;
import com.cars.CarsBackend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/v1/cars")
public class CarControllerImpl implements CarController{
    private final CarService carService;
    private CarResponseHelper carResponseHelper;

    @Autowired
    public CarControllerImpl(final CarService carsService) {
        this.carService = carsService;
        carResponseHelper = new CarResponseHelper();
    }

    @Override
    @PostMapping("/addCar")
    public ResponseEntity<CarResponse> addCar(@RequestBody final Car car) {
        CarSuccessResponse response = new CarSuccessResponse();
        response.setCar(carService.addCar(car));
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Car has been added.");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }

    @Override
    @PatchMapping("/updateCar/{vin}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable("vin") final String vin, @RequestBody final Car car){
        CarSuccessResponse response = new CarSuccessResponse();
        response.setCar(carService.patchUpdateCar(vin, car));
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Car has been updated");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/deleteCar/{vin}")
    public ResponseEntity<CarResponse> deleteCar(@PathVariable("vin") final String vin) {
        CarDeleteResponse response = new CarDeleteResponse();
        response.setCar(carService.deleteByVin(vin));
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Car has been deleted");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<CarResponse> getAllCars() {
        CarCollectionResponse response = new CarCollectionResponse();
        response.setCarsList(carService.getAllCars());
        response.setRecordsFetched(response.getCarsList().size());
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("All Cars have been fetched");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("search/vin/{vin}")
    public ResponseEntity<CarResponse> getCarByVin(@PathVariable("vin") final String vin) {
        CarSuccessResponse response = new CarSuccessResponse();
        response.setCar(carService.getCarByVin(vin));
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Car with vin number " + vin + " has been fetched");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("search/{make}")
    public ResponseEntity<CarResponse> getCarsByMake(@PathVariable("make") final String make) {
        CarCollectionResponse response = new CarCollectionResponse();
        response.setCarsList(carService.getCarsByMake(make));
        response.setRecordsFetched(response.getCarsList().size());
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("All " + make + "s have been fetched");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("search/{make}/{model}")
    public ResponseEntity<CarResponse> getCarsByMakeAndModel(@PathVariable("make") final String make, @PathVariable("model") final String model) {
        CarCollectionResponse response = new CarCollectionResponse();
        response.setCarsList(carService.getCarsByMakeAndModel(make, model));
        response.setRecordsFetched(response.getCarsList().size());
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("All " + make + " " + model + "s have been fetched");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("search/state/{state}")
    public ResponseEntity<CarResponse> getCarsByState(@PathVariable("state") final String state) {
        CarCollectionResponse response = new CarCollectionResponse();
        response.setCarsList(carService.getCarsByState(state));
        response.setRecordsFetched(response.getCarsList().size());
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("All cars in " + state + " have been fetched");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("search/color/{color}")
    public ResponseEntity<CarResponse> getCarByColor(@PathVariable("color") final String color) {
        CarCollectionResponse response = new CarCollectionResponse();
        response.setCarsList(carService.getCarsByColor(color));
        response.setRecordsFetched(response.getCarsList().size());
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("All cars in the color " + color + " have been fetched");
        response.getMetadata().setStatus(carResponseHelper.createStatus(true, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        return ResponseEntity.ok(response);
    }
}
