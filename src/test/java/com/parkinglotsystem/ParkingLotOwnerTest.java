package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotOwnerTest {

    @Test
    public void givenParkingLotFull_ShouldReturnTrue() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLotOwner.slotsAreFull();
        boolean checkAvailability = parkingLotOwner.checkAvailability();
        Assert.assertTrue(checkAvailability);
    }

    @Test
    public void givenParkingLotHasEmptySlots_ShouldReturnFalse() {
        ParkingLotOwner lotOwner = new ParkingLotOwner();
        lotOwner.slotsAreEmpty();
        boolean checkAvailability = lotOwner.checkAvailability();
        Assert.assertFalse(checkAvailability);
    }
}
