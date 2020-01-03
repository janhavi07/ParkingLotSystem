package com.parkinglotsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum DRIVER_TYPE {
    NORMAL() {
        @Override
        public ParkingLot getLot(List<ParkingLot> parkingLots) {
            List<ParkingLot> tempLots = new ArrayList<>(parkingLots);
            tempLots.sort(Comparator.comparing(lot -> lot.getNumberOfVehiclesParked()));
            return tempLots.get(0);
        }
    },
    HANDICAP() {
        @Override
        public ParkingLot getLot(List<ParkingLot> parkingLots) {
            ParkingLot parkingLot = parkingLots.stream()
                    .filter(lot -> lot.getAvailableSlots().size() > 0)
                    .findFirst()
                    .orElse(null);
            return parkingLot;
        }
    };

    public abstract ParkingLot getLot(List<ParkingLot> parkingLots);
}
