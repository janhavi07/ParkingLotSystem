package com.parkinglotsystem;

public class ParkingLotManagementSystem {

    private Object vehicle;

    public void toPark(Object vehicle) {
        this.vehicle = vehicle;
    }

    public void toUnpark(Object vehicle) {
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
        }
    }

    public boolean checkIfParked() {
        if(this.vehicle != null)
            return true;
        return false;
    }

    public boolean checkIfUnParked() {
        if(this.vehicle ==  null)
            return true;
        return false;
    }
}
