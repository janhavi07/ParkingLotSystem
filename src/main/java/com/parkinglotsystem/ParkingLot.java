package com.parkinglotsystem;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.parkinglotsystem.ParkingLotException.ExceptionType;

public class ParkingLot {

    public List<ParkingLotObserver> observerList;
    public int vehicleCount = 0;
    private List<ParkingSlot> parkingSlots;
    private int actualCapacity;

    public ParkingLot(int capacityOfParkingLot) {
        this.actualCapacity = capacityOfParkingLot;
        this.observerList = new ArrayList<>();
        this.parkingSlots = new ArrayList();
        this.initializeSlots();
    }

    public void registerObserver(ParkingLotObserver observer) {
        observerList.add(observer);
    }

    public void setTotalSlots(int totalSlots) {
        this.actualCapacity = totalSlots;
    }

    public void initializeSlots() {
        IntStream.range(0, actualCapacity)
                .forEach(slotNumber -> parkingSlots.add(new ParkingSlot(slotNumber)));
    }

    public void toPark(int slot, Object vehicle) {
        this.parkingSlots.get(slot).setVehicleAndInTime(vehicle);
        vehicleCount++;
    }

    public void toUnpark(Object vehicle) throws ParkingLotException {
        ParkingSlot parkingSlot = this.parkingSlots.stream()
                .filter(slot -> vehicle.equals(slot.getVehicle()))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("VEHICLE NOT FOUND!!!!", ExceptionType.VEHICLE_NOT_FOUND));
        parkingSlot.setVehicleAndInTime(null);
        for (ParkingLotObserver observer : observerList)
            observer.slotsAreEmpty();
        vehicleCount--;
    }

    public boolean checkIfParked(Object vehicle) {
        boolean parked = this.parkingSlots.stream()
                .anyMatch(slot -> vehicle.equals(slot.getVehicle()));
        return parked;
    }

    public boolean checkIfUnParked(Object vehicle) throws ParkingLotException {
        boolean present = this.parkingSlots.stream()
                .filter(slot -> (vehicle) == slot.getVehicle())
                .findFirst()
                .isPresent();
        if (!present)
            return true;
        throw new ParkingLotException("Vehicle Not UnPark!!!!!!", ExceptionType.NOT_UNPARKED);
    }


    public ArrayList<Integer> getAvailableSlots() {
        ArrayList<Integer> unOccupiedSlotList = new ArrayList<>();
        IntStream.range(0, actualCapacity)
                .filter(slot -> this.parkingSlots.get(slot).getVehicle() == null)
                .forEach(slot -> unOccupiedSlotList.add(slot));
        if (unOccupiedSlotList.size() == 0) {
            informObserversIfFull();
        }
        return unOccupiedSlotList;
    }

    private void informObserversIfFull() {
        try {
            for (ParkingLotObserver observer : observerList)
                observer.slotsAreFull();
            throw new ParkingLotException("SLOTS UNAVAILABLE", ExceptionType.SLOTS_FULL);
        } catch (ParkingLotException e) {
        }
    }

    public int findVehicleInParkingLot(Object vehicle) throws ParkingLotException {
        ParkingSlot parkingSlot = this.parkingSlots.stream()
                .filter(slot -> slot.getVehicle() == vehicle)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("VEHICLE NOT FOUND!!!!", ExceptionType.VEHICLE_NOT_FOUND));
        return parkingSlot.getSlotNumber();
    }

    public void toPark(Object vehicle) throws ParkingLotException {
        ArrayList<Integer> availableSlots = getAvailableSlots();
        if (availableSlots.size() == 0) {
            throw new ParkingLotException("SLOTS FULL!!!!",ExceptionType.SLOTS_FULL);
        }
        this.parkingSlots.get(availableSlots.get(0)).setVehicleAndInTime(vehicle);
        vehicleCount++;
    }

    public int getNumberOfVehiclesParked() {
        return this.vehicleCount;
    }

    public Time getParkedTime(Object vehicle) {
        return this.parkingSlots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .get()
                .getTime();
    }
}
