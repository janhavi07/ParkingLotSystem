package com.parkinglotsystem;

public class ParkingLotOwner implements ParkingLotObserver {

    private boolean slotsFull;

    @Override
    public void slotsAreFull() {
        this.slotsFull = true;
    }

    public boolean checkIfFull() {
        return slotsFull;
    }
}
