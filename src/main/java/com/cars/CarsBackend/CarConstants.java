package com.cars.CarsBackend;

public class CarConstants {
    public static final String INVALID_PRICE_EXCEPTION = "Invalid Price: [%s]. Price is too low or too high.";
    public static final String INVALID_YEAR_EXCEPTION = "Invalid Year: [%s]. Year is too low or too high.";
    public static final String INVALID_MILEAGE_EXCEPTION = "Invalid Mileage: [%s]. Mileage is too low or too high.";

    public static final String INVALID_MAKE_EXCEPTION = "Invalid Car Make: [%s]. Length too long";
    public static final String INVALID_MODEL_EXCEPTION = "Invalid Car Model: [%s]. Length too long";
    public static final String INVALID_STATE_EXCEPTION = "Invalid State: [%s]. Length too long";
    public static final String INVALID_COLOR_EXCEPTION = "Invalid Color: [%s]. Length too long";

    public static final String VIN_NOT_FOUND_EXCEPTION = "Vin [%s] not found.";
    public static final String MISSING_VALUE_EXCEPTION = "Missing input body values. Make, model and year cannot be null";
    public static final String VIN_CANNOT_BE_UPDATED_EXCEPTION = "Vin is not allowed to be updated";
}
