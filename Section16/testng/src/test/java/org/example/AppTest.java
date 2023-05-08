package org.example;

import org.testng.Assert;
import org.testng.annotations.*;
/**
 * Unit test for simple App.
 */
public class AppTest {

    private Person person;
    @BeforeTest
    public void setUp() {
        person = new Person();
    }

    @Test
    public void testPersonAge() {
        person.setAge(15);
        Assert.assertEquals(person.getAge(), 15);
    }
}
