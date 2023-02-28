package com.example.greenkart;

import org.example.SetWebDriverLocation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

public class MainPageTest {
  private final String url = "https://rahulshettyacademy.com/seleniumPractise/#/";
  private final String productAddedText = "ADDED";
  private final String buttonXpath = "//button[text()='ADD TO CART']";
  private final String[] desiredProducts = {
      "Cucumber", "Brocolli", "Tomato",
      "Mushroom", "Apple", "Mango",
      "Strawberry", "Almonds", "Walnuts"
  };
  // convert the array to a list, can now search for items within the list
  private List<String> desiredProductsList = Arrays.asList(desiredProducts);
  List<WebElement> products;
  int desiredProductCount, numberOfProductItems;

  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;
  private WebElement[] fruitOrVegetableNamesArray;
  private WebElement[] addToCardButtonsArray;


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
    products = mainPage.productTitles;
    desiredProductCount = 0; numberOfProductItems = 30;
  }
  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testAddToCartAndCheckout() throws InterruptedException {
    /*
    We have a large amount of grocery items at the website, Rahul wants Cucumber to be initially
    selected.  However, when we query the "ADD TO CART" button 30 elements are found.  The technique
    Rahul wants to use is to identify the proper "ADD TO CART" button associated with the
    Cucumber.  We can do that by querying the <h4> element which has the desired text.  Since
    the <h4> element in part of the larger element which also contains the "ADD TO CART" button
    once we get the index for the <h4> element we have the index for the Cucumber's "ADD TO CART"
    button.

    The best way to approach this problem, which was contrary to what I initially tried, is to iterate
    through all the fruits and vegetables and see if the current food product being iterated on is in
    the desired list, if so click on the product.

    If one reverses the order of iteration, i.e., iterate through the desired product list and then
    search all products to find the desired fruit or vegetable to buy, the break statement has to be
    used to prevent a search of the entire list each time.

    One can see using the first approach the entire list of products is being searched only one time, while
    using my initial approach the entire list of fruits and vegetables is being searched multiple times.
    */

    /*
      I put in an event driven delay to make sure the last image was visible, which did not work.  I got the
      following error: IndexOutOfBoundsException: Index 25 out of bounds for length 23, this means the product
      list when initialized held 23 items but now later in time due to the for loop it is being asked to access
      index 25 as the index variable has continued to advance in the for loop.

      My initial thoughts are to figure out some way to wait until the list is fully loaded before iterating
      through the for loop.

      Rahul explained the problem it comes down to the nature of the search for the "ADD TO CART" button.  The
      problem is once a user selects "ADD TO CART" the button changes to "ADDED" so the number of "ADD TO CART"
      button is now less for a brief period of time.  We need to put in a wait condition until there are no
      more "ADDED" buttons
     */

    /*
      Since I added the second test which runs much faster, if the first test finishes the browser
      might not be done disappearing before the next one appears in such a case it might be good to
      ensure the browser has closed and is not visible in the @AfterMethod
    */
    WebElement walnutImageVisible = wait.until(
        ExpectedConditions.visibilityOf(mainPage.walnutImage)
    );

    Assert.assertNotNull(walnutImageVisible);

    for (int index = 0; index < products.size(); index++) {
      // this has the entire product description formatted
      // like Broccoli - 1 Kg
      String productTitle = products.get(index).getText();
      // split the string on spaces and then grab the first
      // non-space text which would be the product name
      String productName = productTitle.split("-")[0].trim();


      // check to see if the current product name is part of our desired
      // products to buy
      if (desiredProductsList.contains(productName)){
        desiredProductCount++;
        driver.findElements(By.xpath(buttonXpath)).get(index).click();
        boolean noAddedButtonsPresent = wait.until(
            ExpectedConditions.invisibilityOfAllElements(mainPage.allAddedButtons)
        );
        assertTrue(noAddedButtonsPresent);
        if (desiredProductCount >= desiredProducts.length) break;
      }
    }
    List<WebElement> numberOfItemsAndTotalPrice = wait.until(
        ExpectedConditions.visibilityOfAllElements(mainPage.numberOfItemsAndTotalPrice)
    );
    String numberOfItems = numberOfItemsAndTotalPrice.get(0).getText();
    String totalPrice = numberOfItemsAndTotalPrice.get(1).getText();
    String[] expected = {"9", "956"};
    String[] results = {numberOfItems, totalPrice};

    Assert.assertEquals(results, expected);
    /*
      Note there is a weakness in this solution as I have to manipulate the button status before
      ensuring I can click the correct button.  Rahul stated what is generally good practise in the
      end-to-end testing community, avoid identifying elements by text as text can change, in the
      solution below the "ADD TO CART" buttons are identified by an element xpath which is more
      robust.  The test will also run faster as I will not be waiting for the "ADDED" text to clear
    */
  }

  @Test
  public void addToCartAndCheckoutMoreRobust() {
    WebElement walnutImageVisible = wait.until(
        ExpectedConditions.visibilityOf(mainPage.walnutImage)
    );

    Assert.assertNotNull(walnutImageVisible);

    for (int index = 0; index < products.size(); index++) {
      // this has the entire product description formatted
      // like Broccoli - 1 Kg
      String productTitle = products.get(index).getText();
      // split the string on spaces and then grab the first
      // non-space text which would be the product name
      String productName = productTitle.split("-")[0].trim();

      // check to see if the current product name is part of our desired
      // products to buy
      if (desiredProductsList.contains(productName)){
        desiredProductCount++;
        // click the "ADD TO CART" button which is positionally located
        mainPage.addToCartButtonsWithXpath.get(index).click();
        if (desiredProductCount >= desiredProducts.length) break;
      }
    }
    String numberOfItems = mainPage.numberOfItemsAndTotalPrice.get(0).getText();
    String totalPrice = mainPage.numberOfItemsAndTotalPrice.get(1).getText();
    String[] expected = {"9", "956"};
    String[] results = {numberOfItems, totalPrice};

    Assert.assertEquals(results, expected);

  }
}
/*
One thing to consider for a future project is to take Mike Schiemer's suggestion and randomly pick 6 items
out of the 30 and then only iterate through those six, the immediate problem is asserting on the total price,
for each item we are going to have to grab the price and then keep adding the price to the sum total
 */
