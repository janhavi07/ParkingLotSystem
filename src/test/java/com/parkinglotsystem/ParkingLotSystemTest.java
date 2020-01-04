package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystemTest {

    Vehicle vehicle1, vehicle2, vehicle4, vehicle5;
    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        vehicle1 = new Vehicle("WHITE");
        vehicle2 = new Vehicle("RED");
        vehicle4 = new Vehicle("WHITE");
        vehicle5 = new Vehicle("WHITE");
        parkingLotSystem = new ParkingLotSystem(2, 2);
    }

    @Test
    public void givenMultipleParkingLots_ShouldParkAVehicleAnd_ReturnTheLotNumber() {
        try {
            Vehicle vehicle3 = new Vehicle("");
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, vehicle2);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle3);
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
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle2);
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, vehicle1);
            ParkingLot lotOfParkedVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle1);
            Assert.assertEquals(parkingLotSystem.parkingLots.get(0), lotOfParkedVehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAHandicapDriver_WhenWantToPark_ButLotsAreFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, new Vehicle(""));
            parkingLotSystem.parkVehicles(DriverType.NORMAL, new Vehicle(""));
            parkingLotSystem.parkVehicles(DriverType.NORMAL, new Vehicle(""));
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOTS_FULL, e.type);
        }
    }

    @Test
    public void givenLargeVehicle_WhenWantToPark_ThenParkOnLotWhichHasMoreEmptySpace() {
        try {
            parkingLotSystem = new ParkingLotSystem(4, 2);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, new Vehicle(""));
            parkingLotSystem.parkVehicles(DriverType.NORMAL, new Vehicle(""));
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, new Vehicle(""));
            parkingLotSystem.parkVehicles(VehicleType.LARGE, vehicle2);
            ParkingLot lotOfParkedVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle2);
            Assert.assertEquals(parkingLotSystem.parkingLots.get(1), lotOfParkedVehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleLargeVehicle_WhenWantToPark_ThenParkOnTheLotWithMoreEmptySpace() {
        try {
            parkingLotSystem = new ParkingLotSystem(4, 2);
            parkingLotSystem.parkVehicles(VehicleType.LARGE, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, new Vehicle("WHITE"));
            parkingLotSystem.parkVehicles(DriverType.NORMAL, new Vehicle("WHITE"));
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, new Vehicle("WHITE"));
            parkingLotSystem.parkVehicles(VehicleType.LARGE, vehicle2);
            ParkingLot lotOfParkedVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle2);
            Assert.assertEquals(parkingLotSystem.parkingLots.get(1), lotOfParkedVehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicleWithAColor_WhenWantToPark_ShouldParkAtCorrectSlot() {
        try {
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            ParkingLot lotOfParkedVehicle = parkingLotSystem.findLotOfParkedVehicle(vehicle1);
            lotOfParkedVehicle.findVehicleInParkingLot(vehicle1);
            int parkedSlot = lotOfParkedVehicle.findVehicleInParkingLot(vehicle1);
            Assert.assertEquals("WHITE", ParkingLotSystem.parkingLots.get(0).parkingSlots.get(parkedSlot).vehicle.getColor());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleWhiteVehicles_WhenParked_ShouldReturnLocationOfIt() {
        try {
            ;
            List<List<Integer>> lot = new ArrayList<>();
            List<Integer> tempList = new ArrayList<>();
            List<Integer> tempList1 = new ArrayList<>();
            tempList.add(0);
            tempList.add(1);
            tempList1.add(1);
            lot.add(new ArrayList<>(tempList));
            lot.add(new ArrayList<>(tempList1));
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle2);
            parkingLotSystem.parkVehicles(DriverType.HANDICAP, new Vehicle("WHITE"));
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle4);
            List<List<Integer>> listOfLocationOfWhiteVehicles = parkingLotSystem.getLocationOfWhiteVehicle
                    ("WHITE");
            Assert.assertEquals(lot.toString(), listOfLocationOfWhiteVehicles.toString());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
