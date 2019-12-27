package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {

    ParkingLotManagementSystem parkingLotManagementSystem;
    Object vehicle;
    ParkingLotOwner parkingLotOwner;
    SecurityPerson securityPerson;
    ParkingLotObserver observer1;
    ParkingLotObserver observer2;

    @Before
    public void setUp() throws Exception {
        parkingLotManagementSystem = new ParkingLotManagementSystem(1);
        vehicle = new Object();
        parkingLotOwner = new ParkingLotOwner();
        securityPerson = new SecurityPerson();
        observer1 = new ParkingLotOwner();
        observer2=new SecurityPerson();

    }

    @Test
    public void givenAVehicle_WantToPark_ShouldReturnTrue() {
        parkingLotManagementSystem.toPark(vehicle);
        boolean toPark = false;
        try {
            toPark = parkingLotManagementSystem.checkIfParked();
            Assert.assertTrue(toPark);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAVehicle_AsNullToPark_ShouldThrowException() {
        parkingLotManagementSystem.toPark(vehicle);
        try {
            boolean checkIfParked = parkingLotManagementSystem.checkIfParked();
            Assert.assertTrue(checkIfParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenAVehicle_WantToUnpark_ShouldReturnTrue() {
        parkingLotManagementSystem.toPark(vehicle);
        parkingLotManagementSystem.toUnpark(vehicle);
        boolean toUnpark = false;
        try {
            toUnpark = parkingLotManagementSystem.checkIfUnParked();
            Assert.assertTrue(toUnpark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenAVehicle_WantToUnpark_ButCouldNotUnpark_ShouldThrowException() {
        parkingLotManagementSystem.toPark(vehicle);
        parkingLotManagementSystem.toUnpark(new Object());
        try {
            boolean checkIfUnParked = parkingLotManagementSystem.checkIfUnParked();
            Assert.assertTrue(checkIfUnParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_UNPARKED, e.type);
        }
    }

    @Test
    public void givenAParkingLot_WhenFull_ShouldThrowException() {
        parkingLotManagementSystem.toPark(vehicle);
        boolean ifSlotsFull = false;
        parkingLotManagementSystem.toPark(vehicle);
        try {
            parkingLotManagementSystem.checkIfParked();
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOTS_FULL, e.type);
        }
    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformTheOwner() {
        parkingLotManagementSystem.registerObserver(observer1);
        parkingLotManagementSystem.toPark(vehicle);
        try {
            parkingLotManagementSystem.checkIfParked();
            boolean checkIfFull = parkingLotOwner.checkIfFull();
            Assert.assertTrue(checkIfFull);
        } catch (ParkingLotException e) { }

    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformTheSecurityDepartment() {
        parkingLotManagementSystem.registerObserver(observer2);
        parkingLotManagementSystem.toPark(vehicle);
        try {
            parkingLotManagementSystem.checkIfParked();
            boolean checkIfFull = securityPerson.checkIfFull();
            Assert.assertTrue(checkIfFull);
        } catch (ParkingLotException e) { }

    }

    @Test
    public void givenAParkingSlot_WhenFull_ShouldInformBothTheObservers() {
        parkingLotManagementSystem.registerObserver(observer2);
        parkingLotManagementSystem.registerObserver(observer1);
        parkingLotManagementSystem.toPark(vehicle);
        try {
            parkingLotManagementSystem.checkIfParked();
            boolean checkIfFull = securityPerson.checkIfFull();
            boolean checkIfFull1 = parkingLotOwner.checkIfFull();
            Assert.assertTrue(checkIfFull && checkIfFull1 );
        } catch (ParkingLotException e) {
        }

    }
}
