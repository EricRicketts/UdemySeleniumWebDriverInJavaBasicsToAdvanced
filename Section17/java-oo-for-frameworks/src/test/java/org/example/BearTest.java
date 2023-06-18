package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BearTest {

    private Bear bear;
    @BeforeMethod
    public void setUp() {
        bear = new Bear("Brown Bear", 2);
        bear.setHibernating(false);
        bear.setName("Bart");
    }

    @Test
    public void testGetPhylum() {
        String expectedPhylum = "Chordates";
        Assert.assertEquals(bear.getPhylum(), expectedPhylum);
    }

    @Test
    public void testIncrementNumberOfBears() {
        bear.incrementNumberOfBears();
        Assert.assertEquals(bear.getNumberOfBears(), 3);
    }

}
