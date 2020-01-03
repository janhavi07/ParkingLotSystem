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
            Object vehicle3 = new Object();
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, vehicle2);
            parkingLotSystem.parkVehicles(DriverType.NORMAL,vehicle3);
            ParkingLot lotOfThisVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle3);
            Assert.assertEquals(parkingLotSystem.parkingLots.get(1), lotOfThisVehicle);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenMultipleParkingLots_WhenAVehicleIsNotParked_ShouldReturnNull() {
        try {
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, vehicle1);
            ParkingLot lotOfParkedVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle2);
            Assert.assertEquals(null, lotOfParkedVehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAHandicapDriver_WhenParkedToNearestLocation_ShouldReturnNearestLot() {
        try {
            parkingLotSystem.parkVehicles(DriverType.NORMAL,vehicle2);
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, vehicle1);
            ParkingLot lotOfParkedVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle1);
            Assert.assertEquals(parkingLotSystem.parkingLots.get(0),lotOfParkedVehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAHandicapDriver_WhenWantToPark_ButLotsAreFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicles(DriverType.NORMAL,vehicle1);
            parkingLotSystem.parkVehicles(DriverType.NORMAL,new Object());
            parkingLotSystem.parkVehicles(DriverType.NORMAL,new Object());
            parkingLotSystem.parkVehicles(DriverType.NORMAL,new Object());
            parkingLotSystem.parkVehicles(DriverType.HANDICAP,vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOTS_FULL,e.type);
        }
    }
}
