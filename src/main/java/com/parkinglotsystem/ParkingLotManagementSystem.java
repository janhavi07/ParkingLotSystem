package com.parkinglotsystem;

public class ParkingLotManagementSystem {

    private final int actualCapacity;
    private int currentCapacity;
    private Object vehicle;
    private ParkingLotOwner owner;
    private SecurityPerson security;

    public ParkingLotManagementSystem(int capacityOfParkingLot) {
        this.actualCapacity = capacityOfParkingLot;
        this.currentCapacity = 0;
    }

    public void toPark(Object vehicle) {
        this.vehicle = vehicle;
        this.currentCapacity++;
    }

    public void toUnpark(Object vehicle) {
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            this.currentCapacity--;
        }
    }

    public boolean checkIfParked() throws ParkingLotException {
        if (this.actualCapacity == this.currentCapacity) {
            this.owner.slotsAreFull();
            this.security.slotsAreFull();
            throw new ParkingLotException("Could'nt park", ParkingLotException.ExceptionType.SLOTS_FULL);
        }
        return true;
    }

    public boolean checkIfUnParked() throws ParkingLotException {
        if (this.vehicle == null)
            return true;
        throw new ParkingLotException("Could'nt unpark", ParkingLotException.ExceptionType.NOT_UNPARKED);
    }


    public void registerOwner(ParkingLotOwner parkingLotOwner) {
     this.owner=parkingLotOwner;
    }

    public void registerSecurity(SecurityPerson securityPerson) {
        this.security=securityPerson;
    }
}
