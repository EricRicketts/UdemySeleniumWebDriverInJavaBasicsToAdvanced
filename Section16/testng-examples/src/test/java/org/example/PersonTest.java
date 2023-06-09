package org.example;

import org.testng.Assert;
import org.testng.annotations.*;

public class PersonTest {

    private Person firstPerson, secondPerson;
    @BeforeClass
    public static void oneTimeSetup() {
        SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    }

    @BeforeMethod
    public void setUp() {
       firstPerson = new Person();
       secondPerson = new Person("Elmer", "Fudd", 35);
    }

    @Test
    public void testFirstPersonAge() {
        Assert.assertEquals(firstPerson.getAge(), 0);
    }

    @Test
    public void testFirstPersonSetAge() {
        int age = 30;
        firstPerson.setAge(age);
        Assert.assertEquals(firstPerson.getAge(), age);
    }

    @Test
    public void testFirstPersonSetFirstName() {
        String firstName = "Daffy";
        firstPerson.setFirstName(firstName);
        Assert.assertEquals(firstPerson.getFirstName(), firstName);
    }

    @Test
    public void testFirstPersonSetLastName() {
        String lastName = "Duck";
        firstPerson.setLastName(lastName);
        Assert.assertEquals(firstPerson.getLastName(), lastName);
    }

    @Test
    public void testSecondPersonGetAge() {
        int expectedAge = 35;
        Assert.assertEquals(secondPerson.getAge(), expectedAge);
    }

    @Test
    public void testSecondPersonGetFirstName() {
        String expectedFirstName = "Elmer";
        Assert.assertEquals(secondPerson.getFirstName(), expectedFirstName);
    }

    @Test
    public void testSecondPersonGetLastName() {
        String expectedLastName = "Fudd";
        Assert.assertEquals(secondPerson.getLastName(), expectedLastName);
    }

    @Test
    public void testSecondPersonFullName() {
        String expectedFullName = "Elmer Fudd";
        Assert.assertEquals(secondPerson.getFullName(), expectedFullName);
    }

}
