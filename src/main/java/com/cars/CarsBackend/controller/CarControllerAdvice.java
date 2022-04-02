package com.cars.CarsBackend.controller;

import com.cars.CarsBackend.exception.*;
import com.cars.CarsBackend.response.CarErrorResponse;
import com.cars.CarsBackend.response.CarResponse;
import com.cars.CarsBackend.response.CarResponseHelper;
import com.cars.CarsBackend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class CarControllerAdvice {

    private final CarService carService;
    private CarResponseHelper carResponseHelper;

    @Autowired
    public CarControllerAdvice(final CarService carsService) {
        this.carService = carsService;
        carResponseHelper = new CarResponseHelper();
    }

    @ExceptionHandler({VinNotFoundException.class})
    private ResponseEntity<CarResponse> findVinFailed(RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Car Vin Number Not Found");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidPriceException.class})
    private ResponseEntity<CarResponse> priceFailure (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Invalid Price. Price is too low or too high.");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidMakeException.class})
    private ResponseEntity<CarResponse> makeFailure (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Invalid Car Make. Length too long");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidModelException.class})
    private ResponseEntity<CarResponse> modelFailure (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Invalid Car Model. Length too long");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidColorException.class})
    private ResponseEntity<CarResponse> colorFailure (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Invalid Car Color. Length too long");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidStateException.class})
    private ResponseEntity<CarResponse> stateFailure (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Invalid State. Length too long");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidYearException.class})
    private ResponseEntity<CarResponse> yearFailure (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Invalid Year. Year is too low or too high.");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidMileageException.class})
    private ResponseEntity<CarResponse> mileageFailure (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Invalid Mileage. Mileage is too low or too high.");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingValueException.class})
    private ResponseEntity<CarResponse> missingValuesFailure (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Missing input body values. Make, model and year cannot be null.");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({VinCannotBeUpdatedException.class})
    private ResponseEntity<CarResponse> vinUpdateError (RuntimeException e) {
        CarErrorResponse response = new CarErrorResponse();
        response.getMetadata().setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        response.getMetadata().setDescription("Vin is not allowed to be updated");
        response.getMetadata().setStatus(carResponseHelper.createStatus(false, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
