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
        vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH 09 5676", "Mangesh");
        vehicle2 = new Vehicle("RED", "INOVA", "MH 09 5676", "Mangesh");
        vehicle4 = new Vehicle("WHITE", "TOYOTA", "MH 09 5676", "Mangesh");
        vehicle5 = new Vehicle("WHITE", "TOYOTA", "MH 09 5676", "Mangesh");
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

    @Test
    public void givenAVehicleWithDetailsAndAttendantName_WhenWantToPark_ShouldParkAtCorrectSlot() {
        try {
            List<String> lot = new ArrayList<>();
            lot.add("SlotNumber :0,attendantName='7894', numberPlate='MH 09 5676', vehicleName='TOYOTA', color='BLUE");
            vehicle1 = new Vehicle("BLUE", "TOYOTA", "MH 09 5676", "7894");
            vehicle2 = new Vehicle("YELLOW", "INOVA", "MH 09 1996", "123");
            Vehicle vehicle3 = new Vehicle("WHITE", "TOYOTA", "MH 098 130 ", "abc");
            Vehicle vehicle4 = new Vehicle("WHITE", "TOYOTA", "MH 00 5676", "mmv");
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle2);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle3);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle4);
            List<List<String>> details = parkingLotSystem.getDetails("BLUE", "TOYOTA");
            System.out.println(details.toString());
            Assert.assertEquals(lot, details.get(0));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenABMWVehicles_WhenPoliceDepartmentWwantToKnowLocation_ShouldReturnListOfCorrectSlot() {
        try {
            List<String> lot = new ArrayList<>();
            lot.add("SlotNumber :0,attendantName='7894', numberPlate='MH 09 5676', vehicleName='BMW', color='BLUE");
            vehicle1 = new Vehicle("BLUE", "BMW", "MH 09 5676", "7894");
            vehicle2 = new Vehicle("YELLOW", "INOVA", "MH 09 1996", "123");
            Vehicle vehicle3 = new Vehicle("WHITE", "TOYOTA", "MH 098 130 ", "abc");
            Vehicle vehicle4 = new Vehicle("WHITE", "TOYOTA", "MH 00 5676", "mmv");
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle2);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle3);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle4);
            List<List<String>> details = parkingLotSystem.getDetailsOfBMWVehicles("BMW");
            System.out.println(details.toString());
            Assert.assertEquals(lot, details.get(0));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLotSystem_WhenPoliceDepartmentWwantToKnowAllCarsParkedInLast30Min_ShouldReturnListOfCars() {
        try {
            List<String> lot = new ArrayList<>();
            lot.add("attendantName='7894', numberPlate='MH 09 5676', vehicleName='BMW', color='BLUE, attendantName='abc', numberPlate='MH 098 130 ', vehicleName='TOYOTA', color='WHITE");
            vehicle1 = new Vehicle("BLUE", "BMW", "MH 09 5676", "7894");
            vehicle2 = new Vehicle("YELLOW", "INOVA", "MH 09 1996", "123");
            Vehicle vehicle3 = new Vehicle("WHITE", "TOYOTA", "MH 098 130 ", "abc");
            Vehicle vehicle4 = new Vehicle("WHITE", "TOYOTA", "MH 00 5676", "mmv");
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle1);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle2);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle3);
            parkingLotSystem.parkVehicles(DriverType.NORMAL, vehicle4);
            List<List<Vehicle>> details = parkingLotSystem.getListOfCarsParkedInLast30Min();
            Assert.assertEquals(lot.toString(), details.get(0).toString());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

}
