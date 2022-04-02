package com.cars.CarsBackend.controller;

import com.cars.CarsBackend.model.Car;
import com.cars.CarsBackend.service.CarService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CarControllerImpl.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private Car car1;
    private Car car2;
    private Car car3;

    private static final String STATUS_CODE_JSON_PATH = "$.metadata.status.statusCode";
    private static final String CAR_VIN_JSON_PATH = "$.car.vin";
    private static final String RECORDS_FETCHED_JSON_PATH = "$.recordsFetched";
    private static final String CAR_MAKE_PATH = "$.carsList.[0].make";
    private static final String CAR_MODEL_PATH = "$.carsList.[0].model";
    private static final String CAR_COLOR_PATH = "$.carsList.[0].color";
    private static final String CAR_STATE_PATH = "$.carsList.[0].state";

    @BeforeEach
    public void setUp() {
        car1 = new Car("j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );

        car2 = new Car("kfjfy47fhry56fyrh",
                30000.0,
                "honda",
                "S2000",
                2009,
                39827.0,
                "silver",
                "texas"
        );

        car3 = new Car("9jlw74bfhdyrh4y7r",
                200000.0,
                "acura",
                "nsx",
                2021,
                0.0,
                "black",
                "california"
        );
    }

    @Test
    public void addCar() throws Exception {
        String vin = "zjlw74bfhdyrh4y7z";

        Car carToBeAdded = new Car(
                vin,
                1000.0,
                "acura",
                "integra",
                1999,
                100000.0,
                "black",
                "california"
        );

        when(carService.addCar(any(Car.class))).thenReturn(carToBeAdded);

        String carToBeAddedJsonString = "{" +
                "\"vin\":\"zjlw74bfhdyrh4y7z\"," +
                "\"make\":\"acura\"," +
                "\"price\":1000.0," +
                "\"model\":\"integra\"," +
                "\"year\":1999," +
                "\"mileage\":10000.0," +
                "\"color\":\"black\"," +
                "\"state\":\"california\"}";

        RequestBuilder requestBuilder = post("/api/v1/cars/addCar")
                .accept(MediaType.APPLICATION_JSON).content(carToBeAddedJsonString)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(CAR_VIN_JSON_PATH).value(vin))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.CREATED.value()));

        // ** If need the response results **
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = result.getResponse();
//        String jsonString = response.getContentAsString();
    }

    @Test
    public void updateCar() throws Exception {
        String vin = "zjlw74bfhdyrh4y7z";
        Car carToBeUpdated = new Car(
                vin,
                1000.0,
                "acura",
                "integra",
                1999,
                100000.0,
                "black",
                "california"
        );

        when(carService.patchUpdateCar(eq(vin),any(Car.class))).thenReturn(carToBeUpdated);

        String carToBeUpdatedJsonString = "{" +
                "\"vin\":\"zjlw74bfhdyrh4y7z\"," +
                "\"make\":\"acura\"," +
                "\"price\":1000.0," +
                "\"model\":\"integra\"," +
                "\"year\":1999," +
                "\"mileage\":10000.0," +
                "\"color\":\"black\"," +
                "\"state\":\"california\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/api/v1/cars/updateCar/" + vin)
                .accept(MediaType.APPLICATION_JSON).content(carToBeUpdatedJsonString)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(CAR_VIN_JSON_PATH).value(vin))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.OK.value()));
    }

    @Test
    public void deleteCar() throws Exception {
        String vin = "zjlw74bfhdyrh4y7z";
        Car carToBeDeleted = new Car(
                vin,
                1000.0,
                "acura",
                "integra",
                1999,
                100000.0,
                "black",
                "california"
        );

        when(carService.deleteByVin(eq(vin))).thenReturn(carToBeDeleted);

        String carToBeDeletedJsonString = "{" +
                "\"vin\":\"zjlw74bfhdyrh4y7z\"," +
                "\"make\":\"acura\"," +
                "\"price\":1000.0," +
                "\"model\":\"integra\"," +
                "\"year\":1999," +
                "\"mileage\":10000.0," +
                "\"color\":\"black\"," +
                "\"state\":\"california\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/cars/deleteCar/" + vin)
                .accept(MediaType.APPLICATION_JSON).content(carToBeDeletedJsonString)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(CAR_VIN_JSON_PATH).value(vin))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.OK.value()));

    }


    @Test
    public void getAllCars() throws Exception {
        int expectedRecords = 3;
        when(carService.getAllCars()).thenReturn(Arrays.asList(car1,car2,car3));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/cars/search")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(RECORDS_FETCHED_JSON_PATH).value(expectedRecords))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.OK.value()));
    }

    @Test
    public void getCarByVin() throws Exception {
        String vin = "j7gg37fk29jj27jf3";

        when(carService.getCarByVin(vin)).thenReturn(car1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/cars/search/vin/" + vin)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(CAR_VIN_JSON_PATH).value(vin))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.OK.value()));
    }

    @Test
    public void getCarsByMake() throws Exception {
        int expectedRecords = 2;
        String make = "honda";
        when(carService.getCarsByMake(make)).thenReturn(Arrays.asList(car1,car2));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/cars/search/" + make)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(CAR_MAKE_PATH).value(make))
                .andExpect(jsonPath(RECORDS_FETCHED_JSON_PATH).value(expectedRecords))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.OK.value()));
    }

    @Test
    public void getCarsByMakeAndModel() throws Exception {
        int expectedRecords = 1;
        String make = "honda";
        String model = "civic";
        when(carService.getCarsByMakeAndModel(make,model)).thenReturn(Arrays.asList(car1));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/cars/search/" + make + "/" + model)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(CAR_MAKE_PATH).value(make))
                .andExpect(jsonPath(CAR_MODEL_PATH).value(model))
                .andExpect(jsonPath(RECORDS_FETCHED_JSON_PATH).value(expectedRecords))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.OK.value()));
    }

    @Test
    public void getCarsByState() throws Exception {
        int expectedRecords = 2;
        String state = "california";
        when(carService.getCarsByState(state)).thenReturn(Arrays.asList(car1,car3));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/cars/search/state/" + state)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(CAR_STATE_PATH).value(state))
                .andExpect(jsonPath(RECORDS_FETCHED_JSON_PATH).value(expectedRecords))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.OK.value()));
    }

    @Test
    public void getCarsByColor() throws Exception {
        int expectedRecords = 2;
        String color = "black";
        when(carService.getCarsByColor(color)).thenReturn(Arrays.asList(car1,car3));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/cars/search/color/" + color)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath(CAR_COLOR_PATH).value(color))
                .andExpect(jsonPath(RECORDS_FETCHED_JSON_PATH).value(expectedRecords))
                .andExpect(jsonPath(STATUS_CODE_JSON_PATH).value(HttpStatus.OK.value()));
    }
}
