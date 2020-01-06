package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Vehicle vehicle, vehicle2;
    ParkingLotOwner parkingLotOwner;
    SecurityPerson securityPerson;
    ParkingLotObserver observer1;
    ParkingLotObserver observer2;
    private long milli = 123456789999l;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(1);
        vehicle = new Vehicle("BLUE", "TOYOTA", "MH 09 5676", "Mangesh");
        vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH 09 5676", "Mangesh");
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
        observer1 = new ParkingLotOwner();
        observer2 = new SecurityPerson();
    }

    @Test
    public void givenAVehicle_WantToPark_ShouldReturnTrue() {
        try {
            parkingLot.toPark(vehicle);
            boolean parked = parkingLot.checkIfParked(vehicle);
            Assert.assertTrue(parked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_VehicleCouldNotParked_SHouldThrowException() {
        try {
            parkingLot.toPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_PARKED, e.type);
        }
    }


    @Test
    public void givenAVehicle_WantToUnpark_ShouldReturnTrue() {
        try {
            parkingLot.toPark(vehicle);
            boolean toUnpark = false;
            parkingLot.toUnpark(vehicle);
            toUnpark = parkingLot.checkIfUnParked(vehicle);
            Assert.assertTrue(toUnpark);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAVehicle_WantToUnpark_ButCouldNotUnpark_ShouldThrowException() {
        try {
            parkingLot.toPark(vehicle);
            boolean checkIfUnParked = false;
            parkingLot.toUnpark(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenAVehicle_ButCouldNotUnpark_ShouldThrowException() {
        try {
            parkingLot.toPark(vehicle);
            boolean checkIfUnParked = parkingLot.checkIfUnParked(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_UNPARKED, e.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLot.setTotalSlots(1);
            parkingLot.initializeSlots();
            parkingLot.toPark(vehicle);
            parkingLot.toPark(vehicle2);
            parkingLot.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOTS_FULL, e.type);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformTheOwner() {
        try {
            parkingLot.registerObserver(parkingLotOwner);
            parkingLot.toPark(vehicle);
        } catch (ParkingLotException e) {
            boolean checkIfFull = parkingLotOwner.checkAvailability();
            Assert.assertTrue(checkIfFull);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformTheSecurityDepartment() {
        try {
            parkingLot.registerObserver(securityPerson);
            parkingLot.toPark(vehicle);
        } catch (ParkingLotException e) {
            boolean checkIfFull = securityPerson.checkAvailability();
            Assert.assertTrue(checkIfFull);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformBothTheObservers() {

        parkingLot.setTotalSlots(2);
        parkingLot.initializeSlots();
        try {
            parkingLot.registerObserver(parkingLotOwner);
            parkingLot.registerObserver(securityPerson);
            parkingLot.toPark(vehicle);
            parkingLot.toPark(vehicle2);
            parkingLot.toPark(new Vehicle("RED"));
            parkingLot.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOTS_FULL, e.type);
            boolean checkIfFull = securityPerson.checkAvailability();
            boolean checkIfFull1 = parkingLotOwner.checkAvailability();
            Assert.assertTrue(checkIfFull && checkIfFull1);
        }
    }

    @Test
    public void givenAParkingSlot_WhenEmpty_ShouldInformBothTheObservers() {
        parkingLot.registerObserver(parkingLotOwner);
        parkingLot.registerObserver(securityPerson);
        try {
            parkingLot.toPark(vehicle);
            parkingLot.toUnpark(vehicle);
            parkingLot.checkIfUnParked(vehicle);
            Assert.assertFalse(securityPerson.checkAvailability() && parkingLotOwner.checkAvailability());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingSlot_WhenGivenMultipleVehicles_MoreThanItsCapacity_ShouldThrowException() {
        parkingLot.setTotalSlots(2);
        parkingLot.initializeSlots();
        Vehicle vehicle2 = new Vehicle("");
        Vehicle vehicle3 = new Vehicle("");
        try {
            parkingLot.toPark(vehicle);
            parkingLot.toPark(vehicle2);
            parkingLot.toPark(vehicle3);
            parkingLot.checkIfParked(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOTS_FULL, e.type);
        }
    }

    @Test
    public void givenAParkingLot_SystemWillReturnListOfAvailableSlots() {
        ArrayList<Integer> expectedSlot = new ArrayList<>();
        expectedSlot.add(0);
        expectedSlot.add(1);
        parkingLot.setTotalSlots(2);
        parkingLot.initializeSlots();
        ArrayList<Integer> availableSlots = null;
        availableSlots = parkingLot.getAvailableSlots();
        Assert.assertEquals(expectedSlot, availableSlots);

    }

    @Test
    public void givenAParkingLot_AttendantWillParkTheVehicleInThe_AvailableSlot() {
        parkingLot.setTotalSlots(2);
        parkingLot.initializeSlots();
        ArrayList<Integer> availableSlots = parkingLot.getAvailableSlots();
        parkingLot.toPark(availableSlots.get(0), vehicle);
        boolean checkIfParked = parkingLot.checkIfParked(vehicle);
        Assert.assertTrue(checkIfParked);

    }

    @Test
    public void givenAParkingLot_WhenSlotsAreFullShouldInformObservers() {
        Vehicle vehicle2 = new Vehicle("");
        Vehicle vehicle1 = new Vehicle("");
        parkingLot.registerObserver(parkingLotOwner);
        parkingLot.registerObserver(securityPerson);
        ArrayList<Integer> availableSlotsToParkForVehicle1 = parkingLot.getAvailableSlots();
        parkingLot.toPark(availableSlotsToParkForVehicle1.get(0), vehicle);
        parkingLot.getAvailableSlots();
        boolean availability = parkingLotOwner.checkAvailability();
        boolean availability1 = securityPerson.checkAvailability();
        Assert.assertTrue(availability && availability1);

    }

    @Test
    public void givenAParkingLot_DriverWantToFindHisVehicle_ShouldReturnCorrectParkedSlot() {
        parkingLot.setTotalSlots(2);
        parkingLot.initializeSlots();
        try {
            ArrayList<Integer> availableSlots = parkingLot.getAvailableSlots();
            parkingLot.toPark(availableSlots.get(0), vehicle);
            int slotNumberOfParkedVehicle = parkingLot.findVehicleInParkingLot(vehicle);
            Assert.assertEquals(0, slotNumberOfParkedVehicle);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAParkingLot_DriverWantToFindUnParkedVehicle_ShouldReturnException() {
        parkingLot.setTotalSlots(2);
        parkingLot.initializeSlots();
        try {
            parkingLot.getAvailableSlots();
            parkingLot.findVehicleInParkingLot(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenAParkingLot_OwnerWantsToknowWhenAVehicleWasParked() {
        try {
            LocalDateTime expectedTime = LocalDateTime.now();
            parkingLot.toPark(vehicle);
            LocalDateTime parkedTime = parkingLot.getParkedTime(vehicle);
            Assert.assertEquals(expectedTime, parkedTime);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_WhenWhiteVehiclesAreParked_ReturnListOfWhiteVehicles() {
        try {
            List<Integer> expectedList = new ArrayList<>();
            expectedList.add(1);
            parkingLot.setTotalSlots(2);
            parkingLot.initializeSlots();
            parkingLot.toPark(vehicle);
            parkingLot.toPark(vehicle2);
            List<Integer> listOfWhiteVehicles = parkingLot.findListOfWhiteVehicles("WHITE");
            Assert.assertEquals(expectedList.toString(), listOfWhiteVehicles.toString());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicleWithDetailsAndAttendantName_WhenWantToPark_ShouldParkAtCorrectSlot() {
        try {
            List<String> expectedList = new ArrayList<>();
            expectedList.add("SlotNumber :0,attendantName='Mangesh', numberPlate='MH 09 5676', vehicleName='TOYOTA', color='BLUE");
            parkingLot.setTotalSlots(1);
            parkingLot.initializeSlots();
            parkingLot.toPark(vehicle);
            List<String> detailsOfVehicle = parkingLot.getDetailsOfVehicle("BLUE", "TOYOTA");
            Assert.assertEquals(expectedList.toString(), detailsOfVehicle.toString());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicleNameBMW_WhenParked_ThenReturnItsSlots() {
        List<String> expectedList = new ArrayList<>();
        try {
            Vehicle vehicle = new Vehicle("BLUE", "BMW", "MH 09 5676", "ABC");
            expectedList.add("SlotNumber :0,attendantName='ABC', numberPlate='MH 09 5676', vehicleName='BMW', color='BLUE");
            parkingLot.toPark(vehicle);
            List<String> listOfBMWvehicles = parkingLot.getListOfBMWCars("BMW");
            Assert.assertEquals(expectedList.toString(), listOfBMWvehicles.toString());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_WhenAllCarsParked_ShouldReturnListOfCarsParkedInLast30Minutes() {
        try {
            parkingLot.setTotalSlots(4);
            parkingLot.initializeSlots();
            parkingLot.toPark(vehicle);
            parkingLot.toPark(vehicle2);
            Vehicle vehicle3 = new Vehicle("BLACK", "SEDAN", "MH 09 1000", "TYU");
            Vehicle vehicle4 = new Vehicle("NOCOLOR", "BMW", "MH 09 3333", "CDE");
            List<Vehicle> expectedList = new ArrayList<>();
            expectedList.add(vehicle);
            expectedList.add(vehicle2);
            expectedList.add(vehicle3);
            expectedList.add(vehicle4);
            parkingLot.toPark(vehicle3);
            parkingLot.toPark(vehicle4);
            List<Vehicle> listOfCarsParkedInLast30Min = parkingLot.getListOfCarsParkedInLast30Min();
            Assert.assertEquals(expectedList.toString(),listOfCarsParkedInLast30Min.toString());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
