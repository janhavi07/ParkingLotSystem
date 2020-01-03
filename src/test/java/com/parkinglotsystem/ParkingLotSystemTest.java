package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {

    Object vehicle1, vehicle2;
    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        vehicle1 = new Object();
        vehicle2 = new Object();
        parkingLotSystem = new ParkingLotSystem(2, 2);
    }

    @Test
    public void givenMultipleParkingLots_ShouldParkAVehicleAnd_ReturnTheLotNumber() {
        try {
            parkingLotSystem.parkVehicles(vehicle1);
            parkingLotSystem.parkVehicles(vehicle2);
            ParkingLot lotOfThisVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle2);
            Assert.assertEquals(parkingLotSystem.parkingLots.get(1), lotOfThisVehicle);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenMultipleParkingLots_WhenAVehicleIsNotParked_ShouldReturnNull() {
        try {
            parkingLotSystem.parkVehicles(vehicle1);
            ParkingLot lotOfParkedVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle2);
            Assert.assertEquals(null,lotOfParkedVehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
