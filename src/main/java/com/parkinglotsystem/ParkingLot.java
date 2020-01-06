package com.parkinglotsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.parkinglotsystem.ParkingLotException.ExceptionType;

public class ParkingLot {

    public List<ParkingLotObserver> observerList;
    public int vehicleCount = 0;
    protected List<ParkingSlot> parkingSlots;
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

    public void toPark(int slot, Vehicle vehicle) {
        this.parkingSlots.get(slot).setVehicleAndInTime(vehicle);
        vehicleCount++;
    }

    public void toUnpark(Vehicle vehicle) throws ParkingLotException {
        ParkingSlot parkingSlot = this.parkingSlots.stream()
                .filter(slot -> vehicle.equals(slot.getVehicle()))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("VEHICLE NOT FOUND!!!!", ExceptionType.VEHICLE_NOT_FOUND));
        parkingSlot.setVehicleAndInTime(null);
        for (ParkingLotObserver observer : observerList)
            observer.slotsAreEmpty();
        vehicleCount--;
    }

    public boolean checkIfParked(Vehicle vehicle) {
        boolean parked = this.parkingSlots.stream()
                .anyMatch(slot -> vehicle.equals(slot.getVehicle()));
        return parked;
    }

    public boolean checkIfUnParked(Vehicle vehicle) throws ParkingLotException {
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

    public int findVehicleInParkingLot(Vehicle vehicle) throws ParkingLotException {
        ParkingSlot parkingSlot = this.parkingSlots.stream()
                .filter(slot -> slot.getVehicle() == vehicle)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("VEHICLE NOT FOUND!!!!", ExceptionType.VEHICLE_NOT_FOUND));
        return parkingSlot.getSlotNumber();
    }

    public void toPark(Vehicle vehicle) throws ParkingLotException {
        ArrayList<Integer> availableSlots = getAvailableSlots();
        if (availableSlots.size() == 0) {
            throw new ParkingLotException("SLOTS FULL!!!!", ExceptionType.SLOTS_FULL);
        }
        this.parkingSlots.get(availableSlots.get(0)).setVehicleAndInTime(vehicle);
        this.parkingSlots.get(availableSlots.get(0)).setSlotNumber(availableSlots.get(0));
        vehicleCount++;
    }

    public int getNumberOfVehiclesParked() {
        return this.vehicleCount;
    }

    public LocalDateTime getParkedTime(Vehicle vehicle) {
        return this.parkingSlots.stream()
                .filter(slot -> slot.getVehicle().equals(vehicle))
                .findFirst()
                .get()
                .getTime();
    }

    private Stream getVehicleColorStream(String colorOfVehicle) {
        Stream<ParkingSlot> parkingSlotStream = this.parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equals(colorOfVehicle));
        return parkingSlotStream;
    }

    public List<Integer> findListOfWhiteVehicles(String color) {
        Stream<ParkingSlot> colorStream = getVehicleColorStream(color);
        List<Integer> whiteVehicleList = colorStream
                .map(parkingSlot -> parkingSlot.getSlotNumber())
                .collect(Collectors.toList());
        return whiteVehicleList;
    }

    public List<String> getDetailsOfVehicle(String colorOfVehicle, String vehicleName) {
        Stream<ParkingSlot> colorStream = getVehicleColorStream(colorOfVehicle);
        List<String> listOfVehicleDetails = colorStream
                .filter(parkingSlot -> parkingSlot.getVehicle().getVehicleName().equals(vehicleName))
                .map(parkingSlot -> ("SlotNumber :" + parkingSlot.getSlotNumber() + "," + parkingSlot.vehicle.toString()))
                .collect(Collectors.toList());
        return listOfVehicleDetails;
    }

    public List<String> getListOfBMWCars(String carType) {
        List<String> listOfBMWvehicles = this.parkingSlots.stream()
                .filter(slot -> carType.equalsIgnoreCase(slot.vehicle.getVehicleName()))
                .map(parkingSlot -> ("SlotNumber :" + parkingSlot.getSlotNumber() + "," + parkingSlot.vehicle.toString()))
                .collect(Collectors.toList());
        return listOfBMWvehicles;

    }

    public List<Vehicle> getListOfCarsParkedInLast30Min() {
        List<Vehicle> listOfCarsParkedInLast30Min = this.parkingSlots.stream()
                .filter(slot -> slot.getVehicle()!=null)
                .filter(slot -> slot.getTime().getMinute() <= 30 && slot.getTime().getMinute() > 0)
                .map(slot -> slot.getVehicle())
                .collect(Collectors.toList());
        return listOfCarsParkedInLast30Min;
    }
}
