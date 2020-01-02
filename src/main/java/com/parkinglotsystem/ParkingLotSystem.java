package com.parkinglotsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static com.parkinglotsystem.ParkingLotException.ExceptionType;

public class ParkingLotSystem {

    public List<ParkingLotObserver> observerList;
    public List<ParkingSlot> parkingSlots;
    private int actualCapacity;

    public ParkingLotSystem(int capacityOfParkingLot) {
        this.actualCapacity = capacityOfParkingLot;
        this.observerList = new ArrayList<>();
        this.parkingSlots = new ArrayList();
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

    public void toPark(int slot, Object vehicle) throws ParkingLotException {
        try {
            this.parkingSlots.get(slot).setVehicleAndInTime(vehicle);
        } catch (IndexOutOfBoundsException e) {
            throw new ParkingLotException("SLOTS UNAVAILABLE", ExceptionType.SLOTS_FULL);
        }
    }

    public void toUnpark(Object vehicle) throws ParkingLotException {
        ParkingSlot parkingSlot = this.parkingSlots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("VEHICLE NOT FOUND!!!!", ExceptionType.VEHICLE_NOT_FOUND));
        parkingSlot.setVehicleAndInTime(null);
        for (ParkingLotObserver observer : observerList)
            observer.slotsAreEmpty();
    }

    public boolean checkIfParked(Object vehicle) throws ParkingLotException {
        try {
            boolean parked = this.parkingSlots.stream()
                    .anyMatch(slot -> slot.getVehicle().equals(vehicle));
            return parked;
        } catch (NullPointerException e) {
            throw new ParkingLotException("Not Parked!!!!!", ExceptionType.NOT_PARKED);
        }
    }

    public boolean checkIfUnParked(Object vehicle) throws ParkingLotException {
        boolean present = this.parkingSlots.stream()
                .filter(slot -> slot.getVehicle() == (vehicle))
                .findFirst()
                .isPresent();
        if (!present)
            return true;
        throw new ParkingLotException("Vehicle Not UnPark!!!!!!", ExceptionType.NOT_UNPARKED);
    }


    public ArrayList<Integer> getAvailableSlots() throws ParkingLotException {
        ArrayList<Integer> unOccupiedSlotList = new ArrayList<>();
        IntStream.range(0, actualCapacity)
                .filter(slot -> this.parkingSlots.get(slot).getVehicle() == null)
                .forEach(slot -> unOccupiedSlotList.add(slot));
        if (unOccupiedSlotList.size() == 0) {
            for (ParkingLotObserver observer : observerList)
                observer.slotsAreFull();
            throw new ParkingLotException("SLOTS UNAVAILABLE", ExceptionType.SLOTS_FULL);
        }
        return unOccupiedSlotList;
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
        this.parkingSlots.get(availableSlots.get(0)).setVehicleAndInTime(vehicle);
    }

    public LocalDateTime getParkedTime(Object vehicle) {
        return this.parkingSlots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .get()
                .getTime();
    }
}
