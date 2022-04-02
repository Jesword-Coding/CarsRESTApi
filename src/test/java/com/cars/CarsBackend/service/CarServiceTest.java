package com.cars.CarsBackend.service;

import com.cars.CarsBackend.exception.*;
import com.cars.CarsBackend.model.Car;
import com.cars.CarsBackend.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class) // uses autoclosable to close out database after testing
public class CarServiceTest {

    @Mock
    private CarRepository carRepositoryMock;

    private CarServiceImpl carServiceMock;

    @Autowired
    private CarRepository carRepositoryTest;

    private CarServiceImpl carServiceTest;



    @BeforeEach
    void setUp() {
        carServiceMock = new CarServiceImpl(carRepositoryMock);
        carServiceTest = new CarServiceImpl(carRepositoryTest);
    }

    @Test
    void canGetAllCars() {
        //when
        carServiceMock.getAllCars();

        //then
        verify(carRepositoryMock).findAll();
    }

    @Test
    void canGetAllCarsByMake() {
        //Given
        String make = "honda";

        //When
        carServiceMock.getCarsByMake(make);

        //Then
        ArgumentCaptor<String> makeArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(carRepositoryMock).findByMake(makeArgumentCaptor.capture());

        String capturedMake = makeArgumentCaptor.getValue();

        assertThat(capturedMake).isEqualTo(make);
    }

    @Test
    void canGetAllCarsByMakeAndModel() {
        //Given
        String make = "honda";
        String model = "civic";

        //When
        carServiceMock.getCarsByMakeAndModel(make, model);

        //Then
        ArgumentCaptor<String> makeArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> modelArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(carRepositoryMock).findByMakeAndModel(makeArgumentCaptor.capture(), modelArgumentCaptor.capture());

        String capturedMake = makeArgumentCaptor.getValue();
        String capturedModel = modelArgumentCaptor.getValue();

        assertThat(capturedMake).isEqualTo(make);
        assertThat(capturedModel).isEqualTo(model);
    }

    @Test
    void canGetAllCarsByColor() {
        //Given
        String color = "black";

        //When
        carServiceMock.getCarsByColor(color);

        //Then
        ArgumentCaptor<String> colorArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(carRepositoryMock).findByColor(colorArgumentCaptor.capture());

        String capturedColor = colorArgumentCaptor.getValue();

        assertThat(capturedColor).isEqualTo(color);
    }

    @Test
    void canGetAllCarsByState() {
        //Given
        String state = "california";

        //When
        carServiceMock.getCarsByState(state);

        //Then
        ArgumentCaptor<String> stateArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(carRepositoryMock).findByState(stateArgumentCaptor.capture());

        String capturedState = stateArgumentCaptor.getValue();

        assertThat(capturedState).isEqualTo(state);
    }

    @Test
    void canAddCar() {
        //given
        Car car = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );

        //when
        carServiceMock.addCar(car);

        //then
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);

        verify(carRepositoryMock).save(carArgumentCaptor.capture());

        Car capturedCar = carArgumentCaptor.getValue();

        assertThat(capturedCar).isEqualTo(car);
    }

    @Test
    void canGetCarByVin() {
        //Given
        String vin = "j7gg37fk29jj27jf3";
        Car car = new Car(
                vin,
                20000.0,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );
        carRepositoryTest.save(car);

        //When
        Car carReturned = carServiceTest.getCarByVin(vin);

        //Then
        assertThat(carReturned.getVin()).isEqualTo(vin);
    }

    @Test
    void willThrowVinNotFoundException() {
        // When not given valid vin

        //Then
        assertThatThrownBy(() -> carServiceMock.getCarByVin(anyString())).isInstanceOf(VinNotFoundException.class);
        assertThatThrownBy(() -> carServiceMock.deleteByVin(anyString())).isInstanceOf(VinNotFoundException.class);
        assertThatThrownBy(() -> carServiceMock.patchUpdateCar("vin", any(Car.class))).isInstanceOf(VinNotFoundException.class);

    }

    @Test
    void willThrowInvalidPriceException() {

        // Given
        Double negativePrice = -1.0;
        Double bigPrice = 10000000000000.0;

        Car carWithNegativePrice = new Car(
                "j7gg37fk29jj27jf3",
                negativePrice,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );

        Car carWithBigPrice = new Car(
                "j7gg37fk29jj27jf3",
                bigPrice,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );

        //Then
        assertThatThrownBy(() -> carServiceMock.addCar(carWithNegativePrice)).isInstanceOf(InvalidPriceException.class);
        assertThatThrownBy(() -> carServiceMock.addCar(carWithBigPrice)).isInstanceOf(InvalidPriceException.class);

        //Verify
        verify(carRepositoryMock, never()).save(any());
    }

    @Test
    public void willThrowMissingValueException() {
        //Given
        Car carWithMissingMake = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                null,
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );

        Car carWithMissingModel = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                null,
                2020,
                10.0,
                "black",
                "california"
        );

        Car carWithMissingYear = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                null,
                10.0,
                "black",
                "california"
        );

        //Then
        assertThatThrownBy(() -> carServiceMock.addCar(carWithMissingMake)).isInstanceOf(MissingValueException.class);
        assertThatThrownBy(() -> carServiceMock.addCar(carWithMissingModel)).isInstanceOf(MissingValueException.class);
        assertThatThrownBy(() -> carServiceMock.addCar(carWithMissingYear)).isInstanceOf(MissingValueException.class);

        //verify
        verify(carRepositoryMock, never()).save(any());
    }

    @Test
    public void willThrowInvalidMakeException() {
        //Given
        Car carWithTooLongMakeName = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "ef0928j0239jf239fj029fjfj209efjsdo;ifj0392jf0aiwjepf9a;f3",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );

        //Then
        assertThatThrownBy(() -> carServiceMock.addCar(carWithTooLongMakeName)).isInstanceOf(InvalidMakeException.class);

        //verify
        verify(carRepositoryMock, never()).save(any());
        verify(carRepositoryMock, never()).findByMake(any());
        verify(carRepositoryMock, never()).findByMakeAndModel(any(), any());
    }

    @Test
    public void willThrowInvalidModelException() {
        //Given
        Car carWithTooLongModelName = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "ef0928j0239jf239fj029fjfj209efjsdo;ifj0392jf0aiwjepf9a;f3",
                2020,
                10.0,
                "black",
                "california"
        );

        //Then
        assertThatThrownBy(() -> carServiceMock.addCar(carWithTooLongModelName)).isInstanceOf(InvalidModelException.class);

        //verify
        verify(carRepositoryMock, never()).save(any());
        verify(carRepositoryMock, never()).findByMakeAndModel(any(), any());
    }

    @Test
    public void willThrowInvalidYearException() {
        //Given
        Car carWithTooLowOfYear = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                1000,
                10.0,
                "black",
                "california"
        );

        Car carWithTooHighOfYear = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                3001,
                10.0,
                "black",
                "california"
        );

        //Then
        assertThatThrownBy(() -> carServiceMock.addCar(carWithTooLowOfYear)).isInstanceOf(InvalidYearException.class);
        assertThatThrownBy(() -> carServiceMock.addCar(carWithTooHighOfYear)).isInstanceOf(InvalidYearException.class);

        //verify
        verify(carRepositoryMock, never()).save(any());
    }

    @Test
    public void willThrowInvalidMileageException() {
        //Given
        Car carWithNegativeMileage = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                2020,
                -10.0,
                "black",
                "california"
        );

        Car carWithTooHighOfMileage = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                2020,
                99999999990.0,
                "black",
                "california"
        );

        //Then
        assertThatThrownBy(() -> carServiceMock.addCar(carWithNegativeMileage)).isInstanceOf(InvalidMileageException.class);
        assertThatThrownBy(() -> carServiceMock.addCar(carWithTooHighOfMileage)).isInstanceOf(InvalidMileageException.class);

        //verify
        verify(carRepositoryMock, never()).save(any());
    }

    @Test
    public void willThrowInvalidColorException() {
        //Given
        Car carWithTooLongOfColorName = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                2020,
                10.0,
                "blackkklkkkkkkkkkkkkkk",
                "california"
        );

        //Then
        assertThatThrownBy(() -> carServiceMock.addCar(carWithTooLongOfColorName)).isInstanceOf(InvalidColorException.class);

        //verify
        verify(carRepositoryMock, never()).save(any());
        verify(carRepositoryMock, never()).findByColor(any());
    }

    @Test
    public void willThrowInvalidStateException() {
        //Given
        Car carWithTooLongOfStateName = new Car(
                "j7gg37fk29jj27jf3",
                20000.0,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "californiaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        );

        //Then
        assertThatThrownBy(() -> carServiceMock.addCar(carWithTooLongOfStateName)).isInstanceOf(InvalidStateException.class);

        //verify
        verify(carRepositoryMock, never()).save(any());
        verify(carRepositoryMock, never()).findByState(any());
    }

    @Test
    public void canDeleteCar() {
        String vin = "j7gg37fk29jj27jf3";
        Car car = new Car(
                vin,
                20000.0,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );
        carRepositoryTest.save(car);

        //When
        carServiceTest.deleteByVin(vin);
        Car carReturned = carRepositoryTest.findByVin(vin);

        //Then
        assertNull(carReturned);
    }

    @Test
    public void canPatchUpdateCar() {
        //given
        String vin = "j7gg37fk29jj27jf3";
        Car carToUpdate = new Car(
                vin,
                20000.0,
                "toyota",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );

        Car carWithUpdatedValues = new Car(
                null,
                30000.00,
                "honda",
                "S2000",
                2009,
                50000.00,
                "silver",
                "texas"
        );

        Car carAfterUpdates = new Car (
                vin,
                30000.00,
                "honda",
                "S2000",
                2009,
                50000.00,
                "silver",
                "texas"
        );
        carRepositoryTest.save(carToUpdate);

        //When
        Car resultCar = carServiceTest.patchUpdateCar(vin, carWithUpdatedValues);

        //Then
        assertAll("The car saved into the database should have the updated values along with old values that were not changed",
                () -> assertEquals(carAfterUpdates.getVin(), resultCar.getVin()),
                () -> assertEquals(carAfterUpdates.getPrice(), resultCar.getPrice()),
                () -> assertEquals(carAfterUpdates.getMake(), resultCar.getMake()),
                () -> assertEquals(carAfterUpdates.getModel(), resultCar.getModel()),
                () -> assertEquals(carAfterUpdates.getYear(), resultCar.getYear()),
                () -> assertEquals(carAfterUpdates.getMileage(), resultCar.getMileage()),
                () -> assertEquals(carAfterUpdates.getColor(), resultCar.getColor()),
                () -> assertEquals(carAfterUpdates.getState(), resultCar.getState())
        );
    }

    @Test
    public void willThrowCannotUpdateVinException() {
        //Given
        String vin = "j7gg37fk29jj27jf3";
        Car car = new Car(
                vin,
                20000.0,
                "honda",
                "civic",
                2020,
                10.0,
                "black",
                "california"
        );
        carRepositoryTest.save(car);

        //Then
        assertThatThrownBy(() -> carServiceTest.patchUpdateCar(vin, car)).isInstanceOf(VinCannotBeUpdatedException.class);
    }

}
