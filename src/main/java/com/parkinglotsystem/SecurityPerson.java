package com.parkinglotsystem;

public class SecurityPerson implements ParkingLotObserver {

    private boolean slotFull;

    @Override
    public void slotsAreFull() {
        this.slotFull = true;
    }

    @Override
    public void slotsAreEmpty() {
        this.slotFull = false;
    }

    public boolean checkAvailability() {
        return this.slotFull;
    }
}
