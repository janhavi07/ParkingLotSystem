package com.parkinglotsystem;

import java.util.List;

public class HandicapDriver implements ParkingStrategy{
    @Override
    public ParkingLot getLot() throws ParkingLotException {
        ParkingLot parkingLot = ParkingLotSystem.parkingLots.stream()
                .filter(lot -> lot.getAvailableSlots().size() > 0)
                .findFirst()
                .orElseThrow(() ->new ParkingLotException("LOTS ARE FULL", ParkingLotException.ExceptionType.LOTS_FULL));
        return parkingLot;
    }
}
