package com.cars.CarsBackend.controller;

import com.cars.CarsBackend.CarConstants;
import com.cars.CarsBackend.exception.*;
import com.cars.CarsBackend.model.Car;
import com.cars.CarsBackend.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CarControllerImpl.class)
public class CarControllerAdviceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private static final String DESCRIPTION_JSON_PATH = "$.metadata.description";
    private static final String STATUS_CODE_JSON_PATH = "$.metadata.status.statusCode";

    @Test
    public void findVinFailedTest() throws Exception {
        String vin = "j7gg37fk29jj27jf3";
        String description = "Car Vin Number Not Found";

        when(carService.getCarByVin(vin)).
                thenThrow(new VinNotFoundException(CarConstants.VIN_NOT_FOUND_EXCEPTION, vin));

        mockMvc.perform(get("/api/v1/cars/search/vin/" + vin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void makeFailureTest() throws Exception {
        String description = "Invalid Car Make. Length too long";
        String make = "make";

        when(carService.getCarsByMake(make)).
              thenThrow(new InvalidMakeException(CarConstants.INVALID_MAKE_EXCEPTION, make));

        mockMvc.perform(get("/api/v1/cars/search/" + make)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void modelFailureTest() throws Exception {
        String description = "Invalid Car Model. Length too long";
        String make = "make";
        String model = "model";

        when(carService.getCarsByMakeAndModel(make, model)).
                thenThrow(new InvalidModelException(CarConstants.INVALID_MAKE_EXCEPTION, model));

        mockMvc.perform(get("/api/v1/cars/search/" + make + "/" + model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void colorFailureTest() throws Exception {
        String description = "Invalid Car Color. Length too long";
        String color = "color";

        when(carService.getCarsByColor(color)).
                thenThrow(new InvalidColorException(CarConstants.INVALID_COLOR_EXCEPTION, color));

        mockMvc.perform(get("/api/v1/cars/search/color/" + color)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void stateFailureTest() throws Exception {
        String description = "Invalid State. Length too long";
        String state = "state";

        when(carService.getCarsByState(state)).
                thenThrow(new InvalidStateException(CarConstants.INVALID_STATE_EXCEPTION, state));

        mockMvc.perform(get("/api/v1/cars/search/state/" + state)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void vinUpdateFailureTest() throws Exception {
        String description = "Vin is not allowed to be updated";

        String carJsonString = "{" +
                "\"vin\":\"zjlw74bfhdyrh4y7z\"," +
                "\"make\":\"acura\"," +
                "\"price\":1000.0," +
                "\"model\":\"integra\"," +
                "\"year\":1999," +
                "\"mileage\":10000.0," +
                "\"color\":\"black\"," +
                "\"state\":\"california\"}";

        when(carService.patchUpdateCar(any(String.class), any(Car.class))).
                thenThrow(new VinCannotBeUpdatedException(CarConstants.VIN_CANNOT_BE_UPDATED_EXCEPTION));

        mockMvc.perform(patch("/api/v1/cars/updateCar/" + String.class)
                        .accept(MediaType.APPLICATION_JSON).content(carJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void priceFailureTest() throws Exception {
        String description = "Invalid Price. Price is too low or too high.";
        Double price = -1000.0;

        String carJsonString = "{" +
                "\"vin\":\"zjlw74bfhdyrh4y7z\"," +
                "\"make\":\"acura\"," +
                "\"price\":-1000.0," +
                "\"model\":\"integra\"," +
                "\"year\":1999," +
                "\"mileage\":10000.0," +
                "\"color\":\"black\"," +
                "\"state\":\"california\"}";

        when(carService.patchUpdateCar(any(String.class), any(Car.class))).
                thenThrow(new InvalidPriceException(CarConstants.INVALID_PRICE_EXCEPTION, price));

        mockMvc.perform(patch("/api/v1/cars/updateCar/" + String.class)
                        .accept(MediaType.APPLICATION_JSON).content(carJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void mileageFailureTest() throws Exception {
        String description = "Invalid Mileage. Mileage is too low or too high.";
        Double mileage = -10000.0;

        String carJsonString = "{" +
                "\"vin\":\"zjlw74bfhdyrh4y7z\"," +
                "\"make\":\"acura\"," +
                "\"price\":1000.0," +
                "\"model\":\"integra\"," +
                "\"year\":1999," +
                "\"mileage\":-10000.0," +
                "\"color\":\"black\"," +
                "\"state\":\"california\"}";

        when(carService.patchUpdateCar(any(String.class), any(Car.class))).
                thenThrow(new InvalidMileageException(CarConstants.INVALID_MILEAGE_EXCEPTION, mileage));

        mockMvc.perform(patch("/api/v1/cars/updateCar/" + String.class)
                        .accept(MediaType.APPLICATION_JSON).content(carJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void yearFailureTest() throws Exception {
        String description = "Invalid Year. Year is too low or too high.";
        Integer year = 5000;

        String carJsonString = "{" +
                "\"vin\":\"zjlw74bfhdyrh4y7z\"," +
                "\"make\":\"acura\"," +
                "\"price\":1000.0," +
                "\"model\":\"integra\"," +
                "\"year\":5000," +
                "\"mileage\":10000.0," +
                "\"color\":\"black\"," +
                "\"state\":\"california\"}";

        when(carService.patchUpdateCar(any(String.class), any(Car.class))).
                thenThrow(new InvalidYearException(CarConstants.INVALID_YEAR_EXCEPTION, year));

        mockMvc.perform(patch("/api/v1/cars/updateCar/" + String.class)
                        .accept(MediaType.APPLICATION_JSON).content(carJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void missingValuesFailureTest() throws Exception {
        String description = "Missing input body values. Make, model and year cannot be null.";

        String carJsonString = "{" +
                "\"color\":\"black\"," +
                "\"state\":\"california\"}";

        when(carService.addCar(any())).
                thenThrow(new MissingValueException(CarConstants.MISSING_VALUE_EXCEPTION));

        mockMvc.perform(post("/api/v1/cars/addCar")
                        .accept(MediaType.APPLICATION_JSON).content(carJsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(description))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.BAD_REQUEST.value()));
    }
}
