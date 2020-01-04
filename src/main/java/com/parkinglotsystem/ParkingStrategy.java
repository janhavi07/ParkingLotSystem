package com.parkinglotsystem;

import java.util.List;

public interface ParkingStrategy {
    public ParkingLot getLot() throws ParkingLotException;
}
