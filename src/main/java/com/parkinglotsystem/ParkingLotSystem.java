package com.parkinglotsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    public static ArrayList<ParkingLot> parkingLots;
    private int maximumParkingLots;
    private int maximumParkingSlots;
    private StrategyTypeFactory strategyTypeFactory;
    private ParkingLot parkingLot;
    private NormalDriver normalDriver;
    private ParkingStrategy strategy;

    public ParkingLotSystem(int slots, int lots) {
        this.maximumParkingSlots = slots;
        this.maximumParkingLots = lots;
        this.parkingLots = new ArrayList<>();
        this.strategyTypeFactory = new StrategyTypeFactory();
        IntStream.range(0, maximumParkingLots)
                .forEach(lotNumber -> parkingLots.add(new ParkingLot(maximumParkingSlots)));
    }

    public void parkVehicles(Enum type, Vehicle vehicle) throws ParkingLotException {
        strategy = strategyTypeFactory.getParkingStrategy(type);
        parkingLot = strategy.getLot();
        parkingLot.toPark(vehicle);
    }

    public ParkingLot findLotOfParkedVehicle(Vehicle vehicle) {
        ParkingLot parkingLotOfParkedVehicle = this.parkingLots.stream()
                .filter(lot -> lot.checkIfParked(vehicle))
                .findFirst()
                .orElse(null);
        return parkingLotOfParkedVehicle;
    }

    public List<List<Integer>> getLocationOfWhiteVehicle(String color) {
        List<List<Integer>> listOfLotsWithWhiteVehicles = this.parkingLots.stream()
                .map(lot -> lot.findListOfWhiteVehicles(color))
                .collect(Collectors.toList());
        System.out.println(listOfLotsWithWhiteVehicles.toString());
        return  listOfLotsWithWhiteVehicles;
    }

    public void setMockObject(ParkingLot parkingLot, StrategyTypeFactory strategyTypeFactory,
                              NormalDriver driver, ParkingStrategy strategy) {
        this.parkingLot = parkingLot;
        this.strategyTypeFactory = strategyTypeFactory;
        this.normalDriver = driver;
        this.strategy = strategy;
    }
}
