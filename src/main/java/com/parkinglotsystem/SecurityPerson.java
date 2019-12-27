package com.parkinglotsystem;

public class SecurityPerson {

    private boolean slotFull;

    public void slotsAreFull() {
        this.slotFull=true;
    }

    public boolean checkIfFull() {
        return this.slotFull;
    }
}
