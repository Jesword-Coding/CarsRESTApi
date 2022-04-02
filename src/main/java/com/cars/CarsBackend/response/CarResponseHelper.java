package com.cars.CarsBackend.response;

public class CarResponseHelper {
    public Status createStatus(boolean s, int statusCode, String statusDescription) {
        Status status = new Status();
        status.setSuccess(s);
        status.setStatusCode(statusCode);
        status.setStatusDescription(statusDescription);
        return status;
    }
}
