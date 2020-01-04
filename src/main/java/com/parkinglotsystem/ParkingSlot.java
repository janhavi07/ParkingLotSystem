package com.parkinglotsystem;

import java.sql.Time;

public class ParkingSlot {
    public Vehicle vehicle;
    private Time time;
    private int slotNumber;
    private long milli = 123456789999l;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }


    public Time getTime() {
        return time;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setVehicleAndInTime(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time = new Time(milli);
    }

}
