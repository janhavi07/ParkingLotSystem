package com.parkinglotsystem;

import java.sql.Time;

public class ParkingSlot {
    private  Object vehicle;
    private Time time;
    private int slotNumber;
    private long milli = 123456789999l;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }


    public Time getTime() {
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
        this.time = new Time(milli);
    }
}
