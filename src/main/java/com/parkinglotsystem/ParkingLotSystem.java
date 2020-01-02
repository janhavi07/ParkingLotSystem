package com.parkinglotsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    public List<ParkingLotObserver> observerList;
    public List vehicleList;
    public ArrayList<Integer> unOccupiedSlotList;
    private int actualCapacity;


    public ParkingLotSystem(int capacityOfParkingLot) {
        this.actualCapacity = capacityOfParkingLot;
        this.observerList = new ArrayList<>();
        this.vehicleList = new ArrayList();
        this.unOccupiedSlotList = new ArrayList();
    }

    public void registerObserver(ParkingLotObserver observer) {
        observerList.add(observer);
    }

    public void setTotalSlots(int totalSlots) {
        this.actualCapacity = totalSlots;
    }

    public void initializeSlots() {
        IntStream.range(0, actualCapacity)
                .forEach(slot -> vehicleList.add(null));
    }

    public void toPark(Object vehicle) {
        this.vehicleList.add(vehicle);
    }

    public void toPark(int slot, Object vehicle) throws ParkingLotException {
        this.vehicleList.set(slot, vehicle);
        if (!this.vehicleList.contains(null)) {
            for (ParkingLotObserver observer : observerList)
                observer.slotsAreFull();
            throw new ParkingLotException("Could'nt park", ParkingLotException.ExceptionType.SLOTS_FULL);
        }
    }

    public void toUnpark(Object vehicle) {
        if (this.vehicleList.contains(vehicle)) {
            this.vehicleList.remove(vehicle);
        }
    }

    public boolean checkIfParked(Object vehicle) throws ParkingLotException {
        if (this.vehicleList.contains(vehicle))
            return true;
        if (!this.vehicleList.contains(null)) {
            for (ParkingLotObserver observer : observerList)
                observer.slotsAreFull();
            throw new ParkingLotException("Could'nt park", ParkingLotException.ExceptionType.SLOTS_FULL);
        }
        return false;
    }

    public boolean checkIfUnParked() throws ParkingLotException {
        if (this.vehicleList.size() < this.actualCapacity) {
            for (ParkingLotObserver observer : observerList)
                observer.slotsAreEmpty();
            return true;
        }
        throw new ParkingLotException("Could'nt unpark", ParkingLotException.ExceptionType.NOT_UNPARKED);
    }

    public ArrayList getAvailableSlots() {
        IntStream.range(0, actualCapacity)
                .filter(slot -> vehicleList.get(slot) == null)
                .forEach(slot -> unOccupiedSlotList.add(slot));
        return unOccupiedSlotList;
    }
}
