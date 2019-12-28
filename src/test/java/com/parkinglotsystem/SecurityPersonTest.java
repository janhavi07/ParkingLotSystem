package com.parkinglotsystem;

import org.junit.Assert;
import org.junit.Test;

public class SecurityPersonTest {

    @Test
    public void givenParkingLotFull_ShouldReturnTrue() {
        SecurityPerson securityPerson = new SecurityPerson();
        securityPerson.slotsAreFull();
        boolean checkAvailability = securityPerson.checkAvailability();
        Assert.assertTrue(checkAvailability);
    }

    @Test
    public void givenParkingLotHasEmptySlots_ShouldReturnFalse() {
        SecurityPerson securityPerson = new SecurityPerson();
        securityPerson.slotsAreEmpty();
        boolean checkAvailability = securityPerson.checkAvailability();
        Assert.assertFalse(checkAvailability);
    }
}
