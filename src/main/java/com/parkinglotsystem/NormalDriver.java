package com.parkinglotsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NormalDriver implements ParkingStrategy {
    @Override
    public ParkingLot getLot() {
        List<ParkingLot> tempLots = new ArrayList<>(ParkingLotSystem.parkingLots);
        tempLots.sort(Comparator.comparing(lot -> lot.getNumberOfVehiclesParked()));
        return tempLots.get(0);
    }
}
