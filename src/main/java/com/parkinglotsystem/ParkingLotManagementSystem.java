package com.parkinglotsystem;

public class ParkingLotManagementSystem {

    private Object vehicle;

    public boolean toPark(Object vehicle) {
        this.vehicle = vehicle;
        return true;
    }

    public boolean toUnpark(Object vehicle) {
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }
}
