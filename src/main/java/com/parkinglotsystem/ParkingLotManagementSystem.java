package com.parkinglotsystem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotManagementSystem {

    private int actualCapacity;
    public List<ParkingLotObserver> observerList;
    private int currentCapacity;
    private Object vehicle;
    private ParkingLotOwner owner;
    private SecurityPerson security;
    private ParkingLotObserver observer;
    public List vehicleList;


    public ParkingLotManagementSystem(int capacityOfParkingLot) {
        this.actualCapacity = capacityOfParkingLot;
        this.currentCapacity = 0;
        this.observerList = new ArrayList<>();
        this.vehicleList=new ArrayList();
    }

    public void toPark(Object vehicle) {
        this.vehicleList.add(vehicle);
        this.currentCapacity++;
    }

    public void toUnpark(Object vehicle) {
        if (this.vehicleList.contains(vehicle)) {
            this.vehicleList.remove(vehicle);
            //this.currentCapacity--;
        }
    }

    public boolean checkIfParked() throws ParkingLotException {
        if (this.actualCapacity == vehicleList.size()) {
            for (ParkingLotObserver observer : observerList)
                observer.slotsAreFull();
            throw new ParkingLotException("Could'nt park", ParkingLotException.ExceptionType.SLOTS_FULL);
        }
        return true;
    }

    public boolean checkIfUnParked() throws ParkingLotException {
        if (this.vehicleList.size() < this.actualCapacity) {
            for (ParkingLotObserver observer : observerList)
                observer.slotsAreEmpty();
            return true;
        }
        throw new ParkingLotException("Could'nt unpark", ParkingLotException.ExceptionType.NOT_UNPARKED);
    }

    public void registerObserver(ParkingLotObserver observer) {
        observerList.add(observer);
    }

    public void setTotalSlots(int totalSlots) {
        this.actualCapacity=totalSlots;
    }
}
