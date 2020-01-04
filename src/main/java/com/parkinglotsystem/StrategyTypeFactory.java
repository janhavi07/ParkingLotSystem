package com.parkinglotsystem;

public class StrategyTypeFactory {

    public ParkingStrategy getParkingStrategy(Enum type) {
        if (type.equals(DriverType.NORMAL))
            return new NormalDriver();
        else if (type.equals(VehicleType.LARGE))
            return new LargeVehicle();
        return new HandicapDriver();
    }
}
