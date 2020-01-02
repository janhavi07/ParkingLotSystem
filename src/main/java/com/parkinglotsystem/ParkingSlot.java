package com.parkinglotsystem;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class ParkingSlot {
    private  Object vehicle;
    private LocalDateTime time;
    private int slotNumber;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public ParkingSlot() {
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Object getVehicle() {
        return vehicle;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setVehicleAndInTime(Object vehicle) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(time, that.time);
    }
}
