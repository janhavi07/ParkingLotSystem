package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
    }

    @Test
    public void givenAVehicle_WantToPark_ShouldReturnTrue() {
        parkingLotSystem.toPark(vehicle);
        boolean toPark = false;
        try {
            toPark = parkingLotSystem.checkIfParked(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(toPark);
    }

    @Test
    public void givenAVehicle_AsNullToPark_ShouldThrowException() {
        parkingLotSystem.toPark(vehicle);
        boolean checkIfParked = false;
        try {
            checkIfParked = parkingLotSystem.checkIfParked(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(checkIfParked);
    }

    @Test
    public void givenAParkingLot_VehicleCouldNotParked_SHouldThrowException() {
        parkingLotSystem.toPark(vehicle);
        boolean checkIfParked = false;
        try {
            checkIfParked = parkingLotSystem.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_PARKED, e.type);
        }
    }

    @Test
    public void givenAVehicle_WantToUnpark_ShouldReturnTrue() {

        parkingLotSystem.toPark(vehicle);
        parkingLotSystem.toUnpark(vehicle);
        boolean toUnpark = false;
        try {
            toUnpark = parkingLotSystem.checkIfUnParked();
            Assert.assertTrue(toUnpark);
        } catch (ParkingLotException e) {
        }

    }

    @Test
    public void givenAVehicle_WantToUnpark_ButCouldNotUnpark_ShouldThrowException() {
        parkingLotSystem.toPark(vehicle);
        parkingLotSystem.toUnpark(new Object());
        boolean checkIfUnParked = false;
        try {
            checkIfUnParked = parkingLotSystem.checkIfUnParked();
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_UNPARKED, e.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
        parkingLotSystem.toPark(vehicle);
        boolean ifSlotsFull = false;
        parkingLotSystem.toPark(vehicle);
        try {
            parkingLotSystem.checkIfParked(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformTheOwner() {
        parkingLotSystem.registerObserver(observer1);
        parkingLotSystem.toPark(vehicle);
        try {
            parkingLotSystem.checkIfParked(vehicle);
        } catch (ParkingLotException e) {
            boolean checkIfFull = parkingLotOwner.checkAvailability();
            Assert.assertTrue(checkIfFull);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformTheSecurityDepartment() {
        parkingLotSystem.registerObserver(observer2);
        parkingLotSystem.toPark(vehicle);
        parkingLotSystem.toPark(vehicle2);
        try {
            parkingLotSystem.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            boolean checkIfFull = securityPerson.checkAvailability();
            Assert.assertTrue(checkIfFull);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformBothTheObservers() {
        parkingLotSystem.registerObserver(observer2);
        parkingLotSystem.registerObserver(observer1);
        try {
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.checkIfParked(vehicle);
        } catch (ParkingLotException e) {
            boolean checkIfFull = securityPerson.checkAvailability();
            boolean checkIfFull1 = parkingLotOwner.checkAvailability();
            Assert.assertTrue(checkIfFull && checkIfFull1);
        }
    }

    @Test
    public void givenAParkingSlot_WhenEmpty_ShouldInformBothTheObservers() {
        parkingLotSystem.registerObserver(observer2);
        parkingLotSystem.registerObserver(observer1);
        try {
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.toUnpark(vehicle);
            parkingLotSystem.checkIfUnParked();
            Assert.assertFalse(securityPerson.checkAvailability() && parkingLotOwner.checkAvailability());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingSlot_WhenGivenMultipleVehicles_ShouldParkThem() {
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        parkingLotSystem.setTotalSlots(5);
        try {
            parkingLotSystem.toPark(vehicle);
            parkingLotSystem.toPark(vehicle2);
            parkingLotSystem.toPark(vehicle3);
            boolean checkIfParked = parkingLotSystem.checkIfParked(vehicle);
            Assert.assertTrue(checkIfParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingSlot_WhenGivenMultipleVehicles_MoreThanItsCapacity_ShouldThrowException() {
        parkingLotSystem.setTotalSlots(2);
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
        ArrayList<Integer> availableSlots = parkingLotSystem.getAvailableSlots();
        Assert.assertEquals(expectedSlot, availableSlots);
    }

    @Test
    public void givenAParkingLot_AttendantWillParkTheVehicleInThe_AvailableSlot() {
        parkingLotSystem.setTotalSlots(2);
        parkingLotSystem.initializeSlots();
        ArrayList<Integer> availableSlots = parkingLotSystem.getAvailableSlots();
        try {
            parkingLotSystem.toPark(availableSlots.get(0), vehicle);
            boolean checkIfParked = parkingLotSystem.checkIfParked(vehicle);
            Assert.assertTrue(checkIfParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_IfSlotsAreFull_WillThrowException() {
        Object vehicle2 = new Object();
        Object vehicle1 = new Object();
        parkingLotSystem.setTotalSlots(2);
        parkingLotSystem.initializeSlots();
        ArrayList<Integer> availableSlots = parkingLotSystem.getAvailableSlots();
        try {
            parkingLotSystem.toPark(availableSlots.get(0), vehicle);
            parkingLotSystem.toPark(availableSlots.get(1), vehicle1);
            parkingLotSystem.toPark(availableSlots.get(2), vehicle2);
            parkingLotSystem.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOTS_FULL, e.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenSlotsAreFullShouldInformObservers() {
        Object vehicle2 = new Object();
        Object vehicle1 = new Object();
        parkingLotSystem.setTotalSlots(1);
        parkingLotSystem.initializeSlots();
        parkingLotSystem.registerObserver(parkingLotOwner);
        parkingLotSystem.registerObserver(securityPerson);
        ArrayList<Integer> availableSlots = parkingLotSystem.getAvailableSlots();
        try {
            parkingLotSystem.toPark(availableSlots.get(0), vehicle);
            parkingLotSystem.toPark(availableSlots.get(1), vehicle1);
            parkingLotSystem.toPark(availableSlots.get(2), vehicle2);
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
        ArrayList<Integer> availableSlots = parkingLotSystem.getAvailableSlots();
        try {
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
        parkingLotSystem.getAvailableSlots();
        try {
            parkingLotSystem.findVehicleInParkingLot(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }
}
