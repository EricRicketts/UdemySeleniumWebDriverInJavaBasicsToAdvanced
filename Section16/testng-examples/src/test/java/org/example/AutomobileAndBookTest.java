package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Object.*;

public class AutomobileAndBookTest {

    private Automobile firstCar, secondCar;
    private Book book;

    @DataProvider
    public Object[][] getData() {
        String[][] data = new String[2][2];
        data[0][0] = "Ford";
        data[0][1] = "Taurus";
        data[1][0] = "Toyota";
        data[1][1] = "Corolla";

        return data;
    }

    @BeforeMethod
    public void setUp() {
        firstCar = new Automobile();
        secondCar = new Automobile();
        book = new Book(
                "The Strange Case of Doctor Jekyll and Mister Hyde",
                "Robert Louis Stevenson",
                "Penguin Classics",
                1886,
                    new BigDecimal("8.99")
        );
    }

    @Test(dataProvider = "getData")
    public void testCarModelAndMake(String make, String model) {
        String[][] expected = {{"Ford", "Taurus"}, {"Toyota", "Corolla"}};
        String[][] results = new String[2][2];
        if (make.equalsIgnoreCase("ford") &&
                model.equalsIgnoreCase("taurus")
        ) {
            firstCar.setMake(make);
            firstCar.setModel(model);
            results[0][0] = firstCar.getMake();
            results[0][1] = firstCar.getModel();
            Assert.assertEquals(expected[0][0], results[0][0]);
            Assert.assertEquals(expected[0][1], results[0][1]);
        } else {
            secondCar.setMake(make);
            secondCar.setModel(model);
            results[1][0] = secondCar.getMake();
            results[1][1] = secondCar.getModel();
            Assert.assertEquals(expected[1][0], results[1][0]);
            Assert.assertEquals(expected[1][1], results[1][1]);
        }
    }

    @Test
    public void testGetBookAuthor() {
        Assert.assertEquals(book.getAuthor(), "Robert Louis Stevenson");
    }
}
/*
    Note how the @DataProvider is set up and used.  The @DataProvider annotation
*/
