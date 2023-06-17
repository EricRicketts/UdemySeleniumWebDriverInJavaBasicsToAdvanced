package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ListenersTest {

    private Book book;

    @BeforeMethod
    public void setUp() {
        book = new Book(
                "Treasure Island",
                "Robert Louis Stevenson",
                "Penguin Classics",
                1883,
                new BigDecimal("8.99")
        );
    }

    @Test
    public void testGetBookTitle() {
        String expectedTitle = "Treasure Island";
        Assert.assertEquals(book.getTitle(), expectedTitle);
    }

    @Test
    public void testGetBookAuthor() {
        String expectedAuthor = "Robert Louis Stevenson";
        Assert.assertEquals(book.getAuthor(), expectedAuthor);
    }

    @Test
    public void testGetBookPublisher() {
        String expectedPublisher = "Penguin Classics";
        Assert.assertEquals(book.getPublisher(), expectedPublisher);
    }

    @Test
    public void testGetBookYear() {
        int expectedYear = 1883;
        Assert.assertEquals(book.getYear(), expectedYear);
    }

    @Test
    public void testGetBookPrice() {
        BigDecimal expectedPrice = new BigDecimal("18.99");
        Assert.assertEquals(book.getPrice(), expectedPrice);
    }
}
