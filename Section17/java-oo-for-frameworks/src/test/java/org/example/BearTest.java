package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BearTest {

    private Bear bear;
    @BeforeMethod
    public void setUp() {
        bear = new Bear("Chordates", "Mammals",
                "Omnivores", "Bears", "Brown Bear", 2);
        bear.setHibernating(false);
        bear.setName("Bart");
    }

    @Test
    public void testGetPhylum() {
        String expectedPhylum = "Chordates";
        Assert.assertEquals(bear.getPhylum(), expectedPhylum);
    }

    @Test
    public void testGetAnimalClass() {
        String expectedAnimalClass = "Mammals";
        Assert.assertEquals(bear.getAnimalClass(), expectedAnimalClass);
    }

    @Test
    public void testGetOrder() {
        String expectedOrder = "Omnivores";
        Assert.assertEquals(bear.getOrder(), expectedOrder);
    }

    @Test
    public void testGetFamily() {
        String expectedFamily = "Bears";
        Assert.assertEquals(bear.getFamily(), expectedFamily);
    }

    @Test
    public void testGetSpecies() {
        String expectedSpecies = "Brown Bear";
        Assert.assertEquals(bear.getSpecies(), expectedSpecies);
    }

    @Test
    public void testIncrementNumberOfBears() {
        bear.incrementNumberOfBears();
        Assert.assertEquals(bear.getNumberOfBears(), 3);
    }

}
