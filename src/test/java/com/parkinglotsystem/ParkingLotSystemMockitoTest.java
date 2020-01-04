package com.parkinglotsystem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ParkingLotSystemMockitoTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    ParkingLotSystem parkingLotSystem;
    @Mock
    ParkingLot parkingLot;
    NormalDriver driver;
    StrategyTypeFactory strategyTypeFactory;
    ArrayList<ParkingLot> lots;
    ParkingStrategy strategy;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(2, 2);
        driver = new NormalDriver();
        this.parkingLot = mock(ParkingLot.class);
        this.strategyTypeFactory = mock(StrategyTypeFactory.class);
        this.driver = mock(NormalDriver.class);
        this.strategy = mock(ParkingStrategy.class);
        this.lots = new ArrayList<>();
        this.lots.add(parkingLot);
        this.parkingLotSystem.setMockObject(parkingLot, strategyTypeFactory, driver, strategy);

    }
/*
    @Test
    public void givenVehicle_shouldParkVehicle() {
        try {
            Object vehicle = new Object();
            when(strategyTypeFactory.getParkingStrategy(DriverType.NORMAL)).thenReturn(strategy);
            when(strategy.getLot()).thenReturn(parkingLot);
            verify(parkingLot).toPark(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

   @Test
    public void givenVehicle_ShouldUnParkTheVehicle() {
        this.parkingLot = mock(ParkingLot.class);
        this.parkingLotSystem.setMockObject(assignLot, parkingLot);
        Object vehicle = new Object();
        parkingLotSystem.unParkVehicle(vehicle);
        verify(parkingLot).unParkVehicle(vehicle);
    }

    @Test
    public void givenVehicle_ShouldParkVehicleEvenlyOnEmptySlot() {
        try {
            parkingLotSystem.createParkingLot(2, 2);
            Object vehicle = new Object();
            parkingLotSystem.parkVehicle(DriverType.NORMAL, vehicle);
            Object vehicle2 = new Object();
            parkingLotSystem.parkVehicle(DriverType.NORMAL, vehicle2);
            ParkingLot parkedVehicleLot = parkingLotSystem.getParkedVehicleLot(vehicle);
            Assert.assertEquals(parkingLotSystem.parkingLotsList.get(0), parkedVehicleLot);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenVehicleWithHandicappedDriver_ShouldParkVehicleAtNearestFreeSpace() {
        try {
            parkingLotSystem.createParkingLot(2, 2);
            Object vehicle = new Object();
            parkingLotSystem.parkVehicle(DriverType.HANDICAP, vehicle);
            Object vehicle2 = new Object();
            parkingLotSystem.parkVehicle(DriverType.HANDICAP, vehicle2);
            ParkingLot parkedVehicleLot = parkingLotSystem.getParkedVehicleLot(vehicle2);
            Assert.assertEquals(parkingLotSystem.parkingLotsList.get(0), parkedVehicleLot);
        } catch (ParkingLotException e) {
        }
    }*/
}
