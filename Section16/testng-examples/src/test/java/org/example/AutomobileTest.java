package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class AutomobileTest {

    private Automobile firstCar, secondCar;

    @BeforeMethod
    public void setUp() {
        firstCar = new Automobile();
        secondCar = new Automobile(
                "Ford", "Taurus", 2008, new BigDecimal("12500.50")
        );
    }

    @Test
    public void testGetSecondCardMake() {
        String expectedMake = "Ford";
        Assert.assertEquals(secondCar.getMake(), expectedMake);
    }

    @Test
    public void testGetSecondCarModel() {
        String expectedModel = "Taurus";
        Assert.assertEquals(secondCar.getModel(), expectedModel);
    }

    @Test
    public void testGetSecondCarYear() {
        int expectedYear = 2008;
        Assert.assertEquals(secondCar.getYear(), expectedYear);
    }

    @Test
    public void testSecondCarGetPrice() {
        BigDecimal expectedPrice = new BigDecimal("12500.50");
        Assert.assertEquals(secondCar.getPrice(), expectedPrice);
    }

    @Test
    public void testSetFirstCarMake() {
        String make = "Honda";
        firstCar.setMake(make);
        Assert.assertEquals(firstCar.getMake(), make);
    }

    @Test
    public void testSetFirstCarModel() {
        String model = "Fit";
        firstCar.setModel(model);
        Assert.assertEquals(firstCar.getModel(), model);
    }

    @Test
    public void testSetFirstCarYear() {
        int year = 2012;
        firstCar.setYear(year);
        Assert.assertEquals(firstCar.getYear(), year);
    }

    @Test
    public void testSetFirstCarPrice() {
        BigDecimal price = new BigDecimal("8450.60");
        firstCar.setPrice(price);
        Assert.assertEquals(firstCar.getPrice(), price);
    }
}
