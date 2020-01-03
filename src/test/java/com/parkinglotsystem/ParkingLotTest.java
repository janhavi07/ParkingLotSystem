package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.ArrayList;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle, vehicle2;
    ParkingLotOwner parkingLotOwner;
    SecurityPerson securityPerson;
    ParkingLotObserver observer1;
    ParkingLotObserver observer2;
    private long milli = 123456789999l;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(1);
        vehicle = new Object();
        vehicle2 = new Object();
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
        observer1 = new ParkingLotOwner();
        observer2 = new SecurityPerson();
        parkingLot.initializeSlots();
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
            parkingLot.toPark(new Object());
            parkingLot.checkIfParked(vehicle2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOTS_FULL,e.type);
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
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
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
        Object vehicle2 = new Object();
        Object vehicle1 = new Object();
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
            Time expectedTime = new Time(milli);
            parkingLot.toPark(vehicle);
            Time parkedTime = parkingLot.getParkedTime(vehicle);
            Assert.assertEquals(expectedTime, parkedTime);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
