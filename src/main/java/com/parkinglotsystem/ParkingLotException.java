package com.parkinglotsystem;

public class ParkingLotException extends Exception {
    ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    enum ExceptionType {
        NOT_PARKED,
        NOT_UNPARKED,
        SLOTS_FULL,
        VEHICLE_NOT_FOUND,
        LOTS_FULL;
    }
}
