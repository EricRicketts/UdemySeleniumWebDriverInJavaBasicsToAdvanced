package com.example.waitsexercise;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class MainPageTest {
  private final String url = "https://rahulshettyacademy.com/seleniumPractise/#/";
  private int implicitTimeout = 10;
  private int explicitTimeout;
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;
  private int expectedCartPrice;

  WebElement walnuts;

  public void addItemsToCart(List<String> items) {
    int itemCount = 0;

    // loop over the given list of all items to see if a given item
    // needs to be added to the cart
    for (int index = 0; index < mainPage.allProducts.size(); index++) {
      // get the current product element then find its name and price
      WebElement currentProduct = mainPage.allProducts.get(index);
      WebElement currentProductTitle = currentProduct.findElement(By.className("product-name"));
      String currentProductName = currentProductTitle.getText().split("-")[0].trim();
      String currentProductPrice = currentProduct.findElement(By.className("product-price")).getText();


      // checks to see if the current item is in the list to be added to the cart
      if (items.contains(currentProductName)) {
        // check how many items have been added to the cart
        itemCount++;
        // since we are already adding this item to the cart we
        // might as well sum the prices of all items in the cart
        expectedCartPrice += Integer.parseInt(currentProductPrice);
        // add the item to the cart
        mainPage.allAddToCartButtons.get(index).click();
        // if all the items are added no need to look for more items
        if (itemCount >= items.size()) break;
      }
    }
  }
  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    expectedCartPrice = 0;
    explicitTimeout = 15;
    duration = Duration.ofSeconds(explicitTimeout);
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitTimeout));
    driver.get(url);

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
    walnuts = wait.until(
        ExpectedConditions.visibilityOf(mainPage.walnuts)
    );
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testAddItemsToCart() {
    String[] itemsArray = {"Cucumber", "Brocolli", "Beetroot"};
    List<String> items = Arrays.asList(itemsArray);

    // assert the page has fully loaded by making sure the last
    // item at the bottom of the webpage is visible
    Assertions.assertNotNull(walnuts);

    // add items to the cart
    addItemsToCart(items);

    // wait until expected number of items are in cart
    boolean cartNumberOfItemsUpdated = wait.until(
        ExpectedConditions.textToBePresentInElement(
            mainPage.numberOfItemsAndTotalPrice.get(0), // first element is number of items
            Integer.toString(itemsArray.length)) // expected number of items in cart
    );

    Assertions.assertTrue(cartNumberOfItemsUpdated);

    // we have already asserted on the number of cart items now assert on the
    // total cart price
    String resultantCartPrice = mainPage.numberOfItemsAndTotalPrice.get(1).getText();
    Assertions.assertEquals(Integer.toString(expectedCartPrice), resultantCartPrice);
  }
}
