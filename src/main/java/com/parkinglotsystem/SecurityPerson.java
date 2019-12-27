package com.parkinglotsystem;

public class SecurityPerson implements ParkingLotObserver {

    private boolean slotFull;

    @Override
    public void slotsAreFull() {
        this.slotFull = true;
    }

    public boolean checkIfFull() {
        return this.slotFull;
    }
}
