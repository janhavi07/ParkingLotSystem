package com.parkinglotsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LargeVehicle implements ParkingStrategy {
    @Override
    public ParkingLot getLot() throws ParkingLotException {
        List<ParkingLot> tempLots = new ArrayList<>(ParkingLotSystem.parkingLots);
        tempLots.sort(Comparator.comparing(lot -> lot.getAvailableSlots().size(),Comparator.reverseOrder()));
        return tempLots.get(0);
    }
}
