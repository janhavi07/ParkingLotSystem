package com.parkinglotsystem;

public class DriverTypeFactory {

    public ParkingStrategy getParkingStrategy(DriverType driverType) {
        if (driverType.equals(DriverType.NORMAL))
            return new NormalDriver();
        return new HandicapDriver();
    }
}
