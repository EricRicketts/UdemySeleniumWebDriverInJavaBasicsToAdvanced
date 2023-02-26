package com.example.greenkart;

import org.example.SetWebDriverLocation;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class MainPageTest {
  private final String url = "https://rahulshettyacademy.com/seleniumPractise/#/";
  private final String cucumberHeading = "Cucumber - 1 Kg";
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;


  @BeforeClass
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeMethod
  public void setUp() {
    duration = Duration.ofSeconds(10);
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.get(url);

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testAddToCartAndCheckout() throws InterruptedException {
    WebElement numberOfItems, totalPrice;
    String[] expected, results;
    /*
    We have a large amount of grocery items at the website, Rahul wants Cucumber to be initially
    selected.  However, when we query the "ADD TO CART" button 30 elements are found.  The technique
    Rahul wants to use is to identify the proper "ADD TO CART" button associated with the
    Cucumber.  We can do that by querying the <h4> element which has the desired text.  Since
    the <h4> element in part of the larger element which also contains the "ADD TO CART" button
    once we get the index for the <h4> element we have the index for the Cucumber's "ADD TO CART"
    button.
    */
    // first ensure there are no items in the cart
    numberOfItems = mainPage.numberOfItemsAndTotalPrice.get(0);
    totalPrice = mainPage.numberOfItemsAndTotalPrice.get(1);

    expected = new String[]{"0", "0"};
    results = new String[]{numberOfItems.getText(), totalPrice.getText()};
    Assert.assertEquals(results, expected);

    int productIndex = 0;
    // iterate through the product titles and find the cucumber title and its index
    for (int index = 0; index < mainPage.productTitles.size(); index+=1) {
      WebElement product = mainPage.productTitles.get(index);
      if (product.getText().equals(cucumberHeading)) {
        productIndex = index;
        break;
      }
    }

    // once the index is found add one cucumber to the cart
    mainPage.addToCartButtons.get(productIndex).click();
    Thread.sleep(2000);
  }
}
