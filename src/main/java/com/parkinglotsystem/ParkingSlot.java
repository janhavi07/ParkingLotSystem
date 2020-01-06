package com.parkinglotsystem;

import java.time.LocalDateTime;

public class ParkingSlot {
    public Vehicle vehicle;
    private LocalDateTime time;
    private int slotNumber;
    private long milli = 123456789999l;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void setVehicleAndInTime(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
    }
}
