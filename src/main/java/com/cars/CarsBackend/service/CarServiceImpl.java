package com.cars.CarsBackend.service;

import com.cars.CarsBackend.CarConstants;
import com.cars.CarsBackend.model.Car;
import com.cars.CarsBackend.exception.*;
import com.cars.CarsBackend.repository.CarRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car addCar(Car car) {
        if(isRequiredFieldsPresent(car)) {
            validatePrice(car.getPrice());
            validateMake(car.getMake());
            validateModel(car.getModel());
            validateYear(car.getYear());
            validateMileage(car.getMileage());
            validateColor(car.getColor());
            validateState(car.getState());
            car.setVin(getGeneratedVin());
            return carRepository.save(car);
        }
        else
            throw new MissingValueException(CarConstants.MISSING_VALUE_EXCEPTION);
    }

    @Override
    public Car getCarByVin(final String vin) {
        doesVinExist(vin);
        return carRepository.findByVin(vin);
    }

    @Override
    public Car patchUpdateCar(final String vin, final Car car) {
        doesVinExist(vin);
        Car carToUpdate = carRepository.findByVin(vin);
        patchUpdateHelper(carToUpdate, car);
        return carRepository.save(carToUpdate);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> getCarsByMake(final String make) {
        validateMake(make);
        return carRepository.findByMake(make);
    }

    @Override
    public List<Car> getCarsByMakeAndModel(final String make, final String model) {
        validateMake(make);
        validateModel(model);
        return carRepository.findByMakeAndModel(make, model);
    }

    @Override
    public List<Car> getCarsByColor(final String color) {
        validateColor(color);
        return carRepository.findByColor(color);
    }

    @Override
    public List<Car> getCarsByState(final String state) {
        validateState(state);
        return carRepository.findByState(state);
    }

    @Override
    public Car deleteByVin(final String vin) {
        doesVinExist(vin);
        Car carToBeDelete = carRepository.findByVin(vin);
        carRepository.deleteByVin(vin);
        return carToBeDelete;
    }

    // ------------- Generate Random Vin --------------------------
    private String getGeneratedVin() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < 17) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            sb.append(SALTCHARS.charAt(index));
        }
        return sb.toString();
    }
    // ----------------------------------------------------------------

    // -------------- Update helper method --------------------
    private void patchUpdateHelper(Car carToUpdate, final Car car) {
        if(StringUtils.isNotBlank(car.getVin())) {
            throw new VinCannotBeUpdatedException(CarConstants.VIN_CANNOT_BE_UPDATED_EXCEPTION);
        }
        if(car.getPrice() != null) {
            validatePrice(car.getPrice());
            carToUpdate.setPrice(car.getPrice());
        }

        if(car.getYear() != null) {
            validateYear(car.getYear());
            carToUpdate.setYear(car.getYear());
        }

        if(car.getMileage() != null) {
            validateMileage(car.getMileage());
            carToUpdate.setMileage(car.getMileage());
        }

        if(StringUtils.isNotBlank(car.getMake())) {
            validateMake(car.getMake());
            carToUpdate.setMake(car.getMake());
        }

        if(StringUtils.isNotBlank(car.getModel())) {
            validateModel(car.getModel());
            carToUpdate.setModel(car.getModel());
        }

        if(StringUtils.isNotBlank(car.getColor())) {
            validateColor(car.getColor());
            carToUpdate.setColor(car.getColor());
        }

        if(StringUtils.isNotBlank(car.getState())) {
            validateState(car.getState());
            carToUpdate.setState(car.getState());
        }

    }
    // -------------------------------------------------------

    // ------------------ Validations -------------------------------
    private void doesVinExist(String vin) {
        Car car = carRepository.findByVin(vin);
        if(car == null)
            throw new VinNotFoundException(CarConstants.VIN_NOT_FOUND_EXCEPTION, vin);
    }

    private Boolean isRequiredFieldsPresent(Car car) {
        if(StringUtils.isBlank(car.getMake()) || StringUtils.isBlank(car.getModel()) || car.getYear() == null)
            return false;
        else
            return true;
    }

    private void validatePrice(final Double price) {
        if (price < 0 || price > 9999999999.0)
            throw new InvalidPriceException(CarConstants.INVALID_PRICE_EXCEPTION, price);
    }

    private void validateMake(final String make) {
        if (make.length() > 20)
            throw new InvalidMakeException(CarConstants.INVALID_MAKE_EXCEPTION, make);
    }

    private void validateModel(final String model) {
        if (model.length() > 20)
            throw new InvalidModelException(CarConstants.INVALID_MODEL_EXCEPTION, model);
    }

    private void validateYear(final Integer year) {
        if (year < 1500 || year > 3000)
            throw new InvalidYearException(CarConstants.INVALID_YEAR_EXCEPTION, year);
    }

    private void validateMileage(final Double mileage) {
        if (mileage > 1000000 || mileage < 0)
            throw new InvalidMileageException(CarConstants.INVALID_MILEAGE_EXCEPTION, mileage);
    }

    private void validateColor(final String color) {
        if (color.length() > 10)
            throw new InvalidColorException(CarConstants.INVALID_COLOR_EXCEPTION, color);
    }

    private void validateState(final String state) {
        if (state.length() > 20)
            throw new InvalidStateException(CarConstants.INVALID_STATE_EXCEPTION, state);
    }
}
