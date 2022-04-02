package com.cars.CarsBackend.repository;

import com.cars.CarsBackend.model.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepositoryTest;

    @BeforeEach
    void setUp() {
        Car car1 = new Car("j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );

        Car car2 = new Car("kfjfy47fhry56fyrh",
                30000.0,
                "honda",
                "S2000",
                2009,
                39827.0,
                "silver",
                "texas"
        );

        Car car3 = new Car("9jlw74bfhdyrh4y7r",
                200000.0,
                "acura",
                "nsx",
                2021,
                0.0,
                "black",
                "california"
        );

        carRepositoryTest.save(car1);
        carRepositoryTest.save(car2);
        carRepositoryTest.save(car3);
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        Statement stmt = connection.createStatement();
        stmt.execute("DROP ALL OBJECTS");
        connection.commit();
        connection.close();
    }

    @Test
    void shouldReturnCarByVin() {
        String expectedVin = "j7gg37fk29jj27jf3";

        //When
        Car results = carRepositoryTest.findByVin(expectedVin);

        //Then
        assertThat(results.getVin()).isEqualTo(expectedVin);
    }

    @Test
    void shouldReturnAllCarsByMake() {
        //Given
        String make = "honda";
        int expectedSize = 2;

        //When
        List<Car> resultList = carRepositoryTest.findByMake(make);

        //Then
        assertThat(resultList.size()).isEqualTo(expectedSize);
    }

    @Test
    void shouldReturnAllCarsByMakeAndModel() {
        //Given
        String make = "honda";
        String model = "civic";
        int expectedSize = 1;

        //When
        List<Car> resultList = carRepositoryTest.findByMakeAndModel(make, model);

        //Then
        assertThat(resultList.size()).isEqualTo(expectedSize);
    }

    @Test
    void shouldReturnAllCarsByColor() {
        //Given
        String color = "black";
        int expectedSize = 2;

        //When
        List<Car> resultList = carRepositoryTest.findByColor(color);

        //Then
        assertThat(resultList.size()).isEqualTo(expectedSize);
    }

    @Test
    void shouldReturnAllCarsByState() {
        //Given
        String state = "california";
        int expectedSize = 2;

        //When
        List<Car> resultList = carRepositoryTest.findByState(state);

        //Then
        assertThat(resultList.size()).isEqualTo(expectedSize);
    }

    @Test
    void shouldDeleteCarByVin() {
        //Given
        String vin = "j7gg37fk29jj27jf3";
        int expectedSize = 2;

        //When
        carRepositoryTest.deleteByVin(vin);

        //Then
        List<Car> resultsList = carRepositoryTest.findAll();
        assertThat(resultsList.size()).isEqualTo(expectedSize);
    }
}
