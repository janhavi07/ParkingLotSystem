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

    public boolean checkIfParked() throws ParkingLotException {
        if(this.vehicle != null)
            return true;
        throw new ParkingLotException("Could'nt park",ParkingLotException.ExceptionType.NOT_PARKED);
    }

    public boolean checkIfUnParked() throws ParkingLotException {
        if(this.vehicle ==  null)
            return true;
        throw new ParkingLotException("Could'nt unpark",ParkingLotException.ExceptionType.NOT_UNPARKED);
    }
}
