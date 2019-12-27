package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotSystemTest {

    @Test
    public void givenAVehicle_WantToPark_ShouldReturnTrue() {
        ParkingLotManagementSystem parkingLotManagementSystem = new ParkingLotManagementSystem();
        boolean toPark = parkingLotManagementSystem.toPark();
        Assert.assertTrue(toPark);
    }
}
