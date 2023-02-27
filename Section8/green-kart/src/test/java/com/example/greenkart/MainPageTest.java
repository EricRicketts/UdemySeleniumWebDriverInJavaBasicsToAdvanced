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
    fruitOrVegetableNamesArray =  mainPage.productTitles.toArray(new WebElement[mainPage.productTitles.size()]);
    addToCardButtonsArray = mainPage.addToCartButtons.toArray(new WebElement[mainPage.addToCartButtons.size()]);
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testAddToCartAndCheckout() throws InterruptedException {
    WebElement numberOfItems, totalPrice, fruitOrVegetableAddToCartButton;
    String[] selectedFruitOrVegetableItems = {
        "Brocolli", "Cauliflower", "Cucumber",
         "Carrot", "Brinjal", "Almonds"
    };
    String[] expected, results;
    List<String> selectedFruitOrVegetableList = Arrays.asList(selectedFruitOrVegetableItems);
    boolean hitBranch = false;
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

    // first ensure there are no items in the cart
    numberOfItems = mainPage.numberOfItemsAndTotalPrice.get(0);
    totalPrice = mainPage.numberOfItemsAndTotalPrice.get(1);

    // there should be 0 items and the price should be 0
    expected = new String[]{"0", "0"};
    results = new String[]{numberOfItems.getText(), totalPrice.getText()};
    Assert.assertEquals(results, expected);


    // iterate through the entire list of fruits and vegetables titles, this will
    // only be done one time.  For each product item check to see if
    // it is included in the desired product list, if it is then click to add
    for (int index = 0; index < fruitOrVegetableNamesArray.length; index++) {
      Thread.sleep(2000);
      hitBranch = true;
      WebElement currentFruitOrVegetableNameElement = (WebElement) Array.get(fruitOrVegetableNamesArray, index);
      // strip away the excess text
      String currentFruitOrVegetableAllText = currentFruitOrVegetableNameElement.getText();
      // the format for the product title is => Cucumber - 1 Kg
      String currentFruitOrVegetableName = currentFruitOrVegetableAllText.split("\s+")[0];
      if (selectedFruitOrVegetableList.contains(currentFruitOrVegetableName)) {
        // the current product is in the desired list so select it
        String xpathForButtonSibling = "//following-sibling::div[@class='product-action']/button";
        WebElement currentFruitOrVegetableButtonElement =
            currentFruitOrVegetableNameElement.findElement(By.xpath(xpathForButtonSibling));
        currentFruitOrVegetableButtonElement.click();
//        fruitOrVegetableAddToCartButton = mainPage.addToCartButtons.get(index);
//        fruitOrVegetableAddToCartButton.click();
        // assert the cart has been updated, check the ADDED text appears
        System.out.println("got into if condition " + currentFruitOrVegetableButtonElement);
        boolean addedTextAppears = wait.until(
            ExpectedConditions.textToBePresentInElement(currentFruitOrVegetableButtonElement, productAddedText)
        );
        Assert.assertTrue(addedTextAppears);
      }
    }

    assertTrue(hitBranch);
    // how check for the cart update itself
    /*
    expected = new String[]{"1", "48"};
    results = new String[]{numberOfItems.getText(), totalPrice.getText()};
    Assert.assertEquals(results, expected);

     */
  }
}
