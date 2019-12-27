package com.parkinglotsystem;

public class ParkingLotOwner implements ParkingLotObserver {

    private boolean slotsFull;

    @Override
    public void slotsAreFull() {
        this.slotsFull = true;
    }

    @Override
    public void slotsAreEmpty() {
        this.slotsFull = false;
    }

    public boolean checkAvailability() {
        return slotsFull;
    }
}
