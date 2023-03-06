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
  private WebDriver driver;
  private MainPage mainPage;
  private int expectedCartPrice;

  WebElement walnuts;

  private void addItemsToCart(List<String> items) {
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
    int implicitTimeout = 10;
    String url = "https://rahulshettyacademy.com/seleniumPractise/#/";
    expectedCartPrice = 0;
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitTimeout));
    driver.get(url);

    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testAddItemsToCartAndCheckout() {
    // define an explicit wait for the walnuts to appear
    // which are the items at the bottom of the webpage
    // and then wait for the walnuts to appear
    // doing this to fulfill assignment requirements
    int explicitTimeout = 15;
    Duration duration = Duration.ofSeconds(explicitTimeout);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    //  wait and assert on the walnuts because the wait will fail
    // only if it does not return something within the time limit
    // so we need to ensure the WebElement is not null
    walnuts = wait.until(
        ExpectedConditions.visibilityOf(mainPage.walnuts)
    );
    Assertions.assertNotNull(walnuts);

    // convert the desired array of items to buy to a list
    String[] itemsArray = {
        "Cucumber", "Brocolli", "Beetroot", "Cauliflower",
        "Carrot", "Potato", "Apple", "Mango",
        "Corn", "Strawberry", "Almonds", "Cashews"
    };
    List<String> items = Arrays.asList(itemsArray);

    // add items to the cart
    addItemsToCart(items);

    // wait until expected number of items are in cart
    // we do not need to make an assertion here because
    // the call to textToBePresentInElement method returns
    // true or throws an exception, so if no exception is
    // thrown then we know the value is true
    boolean cartNumberOfItemsUpdated = wait.until(
        ExpectedConditions.textToBePresentInElement(
            mainPage.cartNumberOfItems, // number of items in the cart
            Integer.toString(itemsArray.length)) // expected number of items in cart
    );

    // we have already asserted on the number of cart items now assert on the
    // total cart price, remember in the addItemsToCar method we also accumulate
    // the expected price => expectedCartPrice which is a private variable
    String resultantCartPrice = mainPage.cartTotalPrice.getText();
    Assertions.assertEquals(Integer.toString(expectedCartPrice), resultantCartPrice);

    // examine the details of the cart itself, each item in the cart should be
    // listed along with its price
  }
}
