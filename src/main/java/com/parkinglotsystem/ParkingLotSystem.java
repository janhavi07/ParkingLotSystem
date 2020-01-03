package com.parkinglotsystem;

import java.util.ArrayList;
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

    public void parkVehicles(DRIVER_TYPE driverType, Object vehicle) throws ParkingLotException {
        ParkingLot lot = driverType.getLot(parkingLots);
        lot.toPark(vehicle);
    }

    public ParkingLot findLotOfParkedVehicle(Object vehicle) {
        ParkingLot parkingLotOfParkedVehicle = this.parkingLots.stream()
                .filter(lot -> lot.checkIfParked(vehicle))
                .findFirst()
                .orElse(null);
        return parkingLotOfParkedVehicle;
    }
}
