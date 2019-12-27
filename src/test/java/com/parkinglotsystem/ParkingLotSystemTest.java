package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {

    ParkingLotManagementSystem parkingLotManagementSystem;
    Object vehicle;
    @Before
    public void setUp() throws Exception {
        parkingLotManagementSystem = new ParkingLotManagementSystem();
        vehicle=new Object();
    }

    @Test
    public void givenAVehicle_WantToPark_ShouldReturnTrue() {
        parkingLotManagementSystem.toPark(new Object());
        boolean toPark = parkingLotManagementSystem.checkIfParked();
        Assert.assertTrue(toPark);
    }

    @Test
    public void givenAVehicle_WantToUnpark_ShouldReturnTrue() {
        parkingLotManagementSystem.toPark(vehicle);
        parkingLotManagementSystem.toUnpark(vehicle);
        boolean toUnpark = parkingLotManagementSystem.checkIfUnParked();
        Assert.assertTrue(toUnpark);
    }

}
