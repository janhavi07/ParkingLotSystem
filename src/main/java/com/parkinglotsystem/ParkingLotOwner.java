package com.parkinglotsystem;

public class ParkingLotOwner {

    private boolean slotsFull;

    public void slotsAreFull() {
        this.slotsFull=true;
    }

    public boolean checkIfFull(){
        return slotsFull;
    }
}
