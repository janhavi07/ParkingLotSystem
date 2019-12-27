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
        vehicle = new Object();
    }

    @Test
    public void givenAVehicle_WantToPark_ShouldReturnTrue() {
        parkingLotManagementSystem.toPark(vehicle);
        boolean toPark = false;
        try {
            toPark = parkingLotManagementSystem.checkIfParked();
            Assert.assertTrue(toPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_AsNullToPark_ShouldThrowException() {
        parkingLotManagementSystem.toPark(null);
        try {
            boolean checkIfParked = parkingLotManagementSystem.checkIfParked();
            Assert.assertTrue(checkIfParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_PARKED, e.type);
        }
    }

    @Test
    public void givenAVehicle_WantToUnpark_ShouldReturnTrue() {
        parkingLotManagementSystem.toPark(vehicle);
        parkingLotManagementSystem.toUnpark(vehicle);
        boolean toUnpark = parkingLotManagementSystem.checkIfUnParked();
        Assert.assertTrue(toUnpark);
    }

}
