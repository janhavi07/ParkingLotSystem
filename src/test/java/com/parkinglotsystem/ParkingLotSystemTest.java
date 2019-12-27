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
        boolean toPark = parkingLotManagementSystem.toPark(new Object());
        Assert.assertTrue(toPark);
    }

    @Test
    public void givenAVehicle_WantToUnpark_ShouldReturnTrue() {
        parkingLotManagementSystem.toPark(vehicle);
        boolean toUnpark = parkingLotManagementSystem.toUnpark(vehicle);
        Assert.assertTrue(toUnpark);
    }
}
