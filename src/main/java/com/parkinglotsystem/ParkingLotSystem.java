package com.parkinglotsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private final int maximumParkingLots;
    private final int maximumParkingSlots;
    public ArrayList<ParkingLot> parkingLots;

    public ParkingLotSystem(int slots, int lots) {
        this.maximumParkingSlots = slots;
        this.maximumParkingLots = lots;
        this.parkingLots = new ArrayList<>();
        IntStream.range(0, maximumParkingLots)
                .forEach(lotNumber -> parkingLots.add(new ParkingLot(2)));
    }

    public void parkVehicles(Object vehicle) throws ParkingLotException {
        ParkingLot lot = getLot();
        lot.toPark(vehicle);
    }

    private ParkingLot getLot() {
        List<ParkingLot> tempLots = new ArrayList<>(this.parkingLots);
        tempLots.sort(Comparator.comparing(lot -> lot.getNumberOfVehiclesParked()));
        return tempLots.get(0);
    }

    public ParkingLot findLotOfParkedVehicle(Object vehicle) {
        ParkingLot parkingLotOfParkedVehicle = this.parkingLots.stream()
                .filter(lot -> lot.checkIfParked(vehicle))
                .findFirst()
                .orElse(null);
        return parkingLotOfParkedVehicle;
    }
}
