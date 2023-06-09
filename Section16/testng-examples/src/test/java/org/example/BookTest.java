package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class BookTest {

    private Book firstBook, secondBook;

    @BeforeMethod
    public void setUp() {
        firstBook = new Book();
        secondBook = new Book(
                "Treasure Island", "Robert Louis Stevenson", "Penguin Classics",
                1883, new BigDecimal("8.99")
        );
    }

    @Test
    public void testGetSecondBookTitle() {
        String expectedTitle = "Treasure Island";
        Assert.assertEquals(secondBook.getTitle(), expectedTitle);
    }

    @Test
    public void testGetSecondBookAuthor() {
        String expectedAuthor = "Robert Louis Stevenson";
        Assert.assertEquals(secondBook.getAuthor(), expectedAuthor);
    }

    @Test
    public void testGetSecondBookPublisher() {
        String expectedPublisher = "Penguin Classics";
        Assert.assertEquals(secondBook.getPublisher(), expectedPublisher);
    }

    @Test
    public void testGetSecondBookYear() {
        int expectedYear = 1883;
        Assert.assertEquals(secondBook.getYear(), expectedYear);
    }

    @Test
    public void testGetSecondBookPrice() {
        BigDecimal expectedPrice = new BigDecimal("8.99");
        Assert.assertEquals(secondBook.getPrice(), expectedPrice);
    }

    @Test
    public void testSetFirstBookTitle() {
        String title = "Call of the Wild";
        firstBook.setTitle(title);
        Assert.assertEquals(firstBook.getTitle(), title);
    }

    @Test
    public void testSetFirstBookAuthor() {
        String author = "Jack London";
        firstBook.setAuthor(author);
        Assert.assertEquals(firstBook.getAuthor(), author);
    }

    @Test
    public void testSetFirstBookPublisher() {
        String publisher = "Penguin Classics";
        firstBook.setPublisher(publisher);
        Assert.assertEquals(firstBook.getPublisher(), publisher);
    }

    @Test
    public void testSetFirstBookYear() {
        int year = 1903;
        firstBook.setYear(year);
        Assert.assertEquals(firstBook.getYear(), year);
    }

    @Test
    public void testSetFirstBookPrice() {
        BigDecimal price = new BigDecimal("8.99");
        firstBook.setPrice(price);
        Assert.assertEquals(firstBook.getPrice(), price);
    }
}