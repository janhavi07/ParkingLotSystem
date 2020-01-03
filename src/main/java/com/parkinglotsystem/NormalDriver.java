package com.parkinglotsystem;

import com.sun.org.apache.bcel.internal.generic.PUSH;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NormalDriver implements ParkingStrategy {
    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLots) {
        List<ParkingLot> tempLots = new ArrayList<>(parkingLots);
        tempLots.sort(Comparator.comparing(lot -> lot.getNumberOfVehiclesParked()));
        return tempLots.get(0);
    }
}
