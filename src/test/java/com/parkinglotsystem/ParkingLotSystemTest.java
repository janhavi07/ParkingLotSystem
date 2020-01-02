package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ParkingLotSystemTest {

    ParkingLotSystem parkingLotSystem;
    Object vehicle, vehicle2;
    ParkingLotOwner parkingLotOwner;
    SecurityPerson securityPerson;
    ParkingLotObserver observer1;
    ParkingLotObserver observer2;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
        vehicle2 = new Object();
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
        observer1 = new ParkingLotOwner();
        observer2 = new SecurityPerson();
        parkingLotSystem.initializeSlots();
    }

    @Test
    public void givenAVehicle_WantToPark_ShouldReturnTrue() {
        try {
            parkingLotSystem.toPark(vehicle);
            boolean parked = parkingLotSystem.checkIfParked(vehicle);
            Assert.assertTrue(parked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_VehicleCouldNotParked_SHouldThrowException() {
        boolean checkIfParked = false;
        try {
            parkingLotSystem.toPark(vehicle);
            checkIfParked = parkingLotSystem.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_PARKED, e.type);
        }
    }


    @Test
    public void givenAVehicle_WantToUnpark_ShouldReturnTrue() {
        try {
            parkingLotSystem.toPark(vehicle);
            boolean toUnpark = false;
            parkingLotSystem.toUnpark(vehicle);
            toUnpark = parkingLotSystem.checkIfUnParked(vehicle);
            Assert.assertTrue(toUnpark);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAVehicle_WantToUnpark_ButCouldNotUnpark_ShouldThrowException() {
        try {
            parkingLotSystem.toPark(vehicle);
            boolean checkIfUnParked = false;
            parkingLotSystem.toUnpark(vehicle2);
            checkIfUnParked = parkingLotSystem.checkIfUnParked(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //////////////////////////
    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLotSystem.setTotalSlots(1);
            parkingLotSystem.initializeSlots();
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.toPark(vehicle2);
            parkingLotSystem.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOTS_FULL, e.type);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformTheOwner() {
        try {
            parkingLotSystem.registerObserver(parkingLotOwner);
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.toPark(vehicle2);
            parkingLotSystem.checkIfParked(vehicle);
        } catch (ParkingLotException e) {
            boolean checkIfFull = parkingLotOwner.checkAvailability();
            Assert.assertTrue(checkIfFull);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformTheSecurityDepartment() {
        try {
            parkingLotSystem.registerObserver(securityPerson);
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.toPark(vehicle2);
            parkingLotSystem.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            boolean checkIfFull = securityPerson.checkAvailability();
            Assert.assertTrue(checkIfFull);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformBothTheObservers() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.registerObserver(securityPerson);
        try {
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.toPark(vehicle2);
            parkingLotSystem.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            boolean checkIfFull = securityPerson.checkAvailability();
            boolean checkIfFull1 = parkingLotOwner.checkAvailability();
            Assert.assertTrue(checkIfFull && checkIfFull1);
        }
    }

    @Test
    public void givenAParkingSlot_WhenEmpty_ShouldInformBothTheObservers() {
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.registerObserver(securityPerson);
        try {
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.toUnpark(vehicle);
            parkingLotSystem.checkIfUnParked(vehicle);
            Assert.assertFalse(securityPerson.checkAvailability() && parkingLotOwner.checkAvailability());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingSlot_WhenGivenMultipleVehicles_MoreThanItsCapacity_ShouldThrowException() {
        parkingLotSystem.setTotalSlots(2);
        parkingLotSystem.initializeSlots();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.toPark(vehicle2);
            parkingLotSystem.toPark(vehicle3);
            parkingLotSystem.checkIfParked(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOTS_FULL, e.type);
        }
    }

    @Test
    public void givenAParkingLot_SystemWillReturnListOfAvailableSlots() {
        ArrayList<Integer> expectedSlot = new ArrayList<>();
        expectedSlot.add(0);
        expectedSlot.add(1);
        parkingLotSystem.setTotalSlots(2);
        parkingLotSystem.initializeSlots();
        ArrayList<Integer> availableSlots = null;
        try {
            availableSlots = parkingLotSystem.getAvailableSlots();
            Assert.assertEquals(expectedSlot, availableSlots);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAParkingLot_AttendantWillParkTheVehicleInThe_AvailableSlot() {
        parkingLotSystem.setTotalSlots(2);
        parkingLotSystem.initializeSlots();
        try {
            ArrayList<Integer> availableSlots = parkingLotSystem.getAvailableSlots();
            parkingLotSystem.toPark(availableSlots.get(0), vehicle);
            boolean checkIfParked = parkingLotSystem.checkIfParked(vehicle);
            Assert.assertTrue(checkIfParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_WhenSlotsAreFullShouldInformObservers() {
        Object vehicle2 = new Object();
        Object vehicle1 = new Object();
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.registerObserver(securityPerson);
        try {
            ArrayList<Integer> availableSlotsToParkForVehicle1 = parkingLotSystem.getAvailableSlots();
            parkingLotSystem.toPark(availableSlotsToParkForVehicle1.get(0), vehicle);
            ArrayList<Integer> availableSlotsToParkForVehicle2 = parkingLotSystem.getAvailableSlots();
            parkingLotSystem.toPark(availableSlotsToParkForVehicle2.get(0), vehicle1);
        } catch (ParkingLotException e) {
            boolean availability = parkingLotOwner.checkAvailability();
            boolean availability1 = securityPerson.checkAvailability();
            Assert.assertTrue(availability && availability1);
        }
    }

    @Test
    public void givenAParkingLot_DriverWantToFindHisVehicle_ShouldReturnCorrectParkedSlot() {
        parkingLotSystem.setTotalSlots(2);
        parkingLotSystem.initializeSlots();
        try {
            ArrayList<Integer> availableSlots = parkingLotSystem.getAvailableSlots();
            parkingLotSystem.toPark(availableSlots.get(0), vehicle);
            int slotNumberOfParkedVehicle = parkingLotSystem.findVehicleInParkingLot(vehicle);
            Assert.assertEquals(0, slotNumberOfParkedVehicle);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAParkingLot_DriverWantToFindUnParkedVehicle_ShouldReturnException() {
        parkingLotSystem.setTotalSlots(2);
        parkingLotSystem.initializeSlots();
        try {
            parkingLotSystem.getAvailableSlots();
            parkingLotSystem.findVehicleInParkingLot(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenAParkingLot_OwnerWantsToknowWhenAVehicleWasParked() {
        try {
            parkingLotSystem.toPark(vehicle);
            LocalDateTime parkedTime = parkingLotSystem.getParkedTime(vehicle);
            Assert.assertEquals(LocalDateTime.now(),parkedTime);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

}
