package com.example.waitsexercise;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private int expectedCartPrice;

  WebElement walnuts;
  private final String buttonDefault = "ADD TO CART";
  private final String preDiscountPercentage = "0%";
  private final String promoCode = "rahulshettyacademy";
  private final String promoCodeAppliedString = "Code applied ..!";

  private void addItemsToCart(List<WebElement> items, WebDriver driver, WebDriverWait wait) {
    // since the elements were chosen randomly there is no predictable scroll, thus in order
    // to make sure the right button element is clicked the target button needs to be scrolled
    // into the center of the view.
    items.forEach(item -> {
      // now that we have the desired item, start adding up the price
      String itemPriceString = item.findElement(By.cssSelector("p.product-price")).getText();
      expectedCartPrice += Integer.parseInt(itemPriceString);
      // first scroll the object to the center of the screen, center the object both vertically
      // horizontally it stays put if already in view, this is what the js code does
      // if this is not done then the program will terminate due to not clicking the desired
      // element as the element will be selected in the code but not visible in the webpage
      JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
      String executorScript = "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})";
      jsExecutor.executeScript(executorScript, item);

      // get the button locator using css because the search for the button should be within
      // the scope of the element itself, xpath would search the entire webpage
      String addToCartLocator = "div.product-action > button";
      WebElement addToCartButton = item.findElement(By.cssSelector(addToCartLocator));
      addToCartButton.click();

      // once clicked the button turns to "ADDED" then after a delay it goes to "ADD TO CART"
      boolean buttonTextBackToAddToCart = wait.until(
          ExpectedConditions.textToBePresentInElement(addToCartButton, buttonDefault)
      );
    });
  }

  private ArrayList<Integer> generateRandomItemIndices(int numberOfIndices, int originValue, int boundValue) {
    ArrayList<Integer> randomIndices = new ArrayList<>();
    Random rand = new Random();
    int randomIndicesCount = 0;
    List<Integer> indices = IntStream.range(originValue, boundValue).boxed().toList();
    while (randomIndicesCount < numberOfIndices) {
      int randomIndex = rand.nextInt(boundValue);
      if (randomIndices.contains(randomIndex)) {
        continue;
      } else {
        randomIndices.add(randomIndex);
        randomIndicesCount++;
      }
    }
    return randomIndices;
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
  public void testAddItemsToCartAndCheckout() throws InterruptedException {
    // define an explicit wait for the walnuts to appear
    // which are the items at the bottom of the webpage
    // and then wait for the walnuts to appear
    // doing this to fulfill assignment requirements
    int explicitTimeout = 15;
    Duration duration = Duration.ofSeconds(explicitTimeout);
    WebDriverWait wait = new WebDriverWait(driver, duration);
    int numberOfItemsToBuy = 12;
    List<WebElement> items = new ArrayList<>();

    //  wait and assert on the walnuts because the wait will fail
    // only if it does not return something within the time limit
    // we need to ensure the WebElement is not null
    walnuts = wait.until(
        ExpectedConditions.visibilityOf(mainPage.walnuts)
    );
    Assertions.assertNotNull(walnuts);

    // upon Mike Schiemer's suggestion randomly select the products from the product list
    // in this case we are randomly generating a list of product indices
    ArrayList<Integer> indicesArrayList =
        generateRandomItemIndices(numberOfItemsToBuy, 0, mainPage.allProducts.size());

    // map the product indices to products (WebElement)
    indicesArrayList.forEach(index -> items.add(mainPage.allProducts.get(index)));

    // add items to the cart
    addItemsToCart(items, driver, wait);
    String[] results = new String[]{
        mainPage.cartNumberOfItems.getText(),
        mainPage.cartTotalPrice.getText()
    };
    String[] expected = new String[]{
        Integer.toString(items.size()),
        Integer.toString(expectedCartPrice)
    };

    Assertions.assertArrayEquals(expected, results);

    // now that the total price and number of items is asserted proceed to checkout
    mainPage.cartIcon.click();

    WebElement proceedToCheckoutButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.proceedToCheckoutButton)
    );
    Assertions.assertNotNull(proceedToCheckoutButton);
    proceedToCheckoutButton.click();

    WebElement placeOrderButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.placeOrderButton)
    );
    Assertions.assertNotNull(placeOrderButton);

    // assert totals and discount before applying the discount
    expected = new String[]{
        Integer.toString(expectedCartPrice),
        Integer.toString(expectedCartPrice),
        preDiscountPercentage
    };

    results = new String[]{
        mainPage.totalAmount.getText(),
        mainPage.discountAmount.getText(),
        mainPage.discount.getText()
    };

    Assertions.assertArrayEquals(expected, results);

    // find the promo code input field and type in the promo code
    mainPage.promoCodeInput.sendKeys(promoCode);
    mainPage.applyPromoCodeButton.click();
    WebElement promoCodeApplied = wait.until(
        ExpectedConditions.visibilityOf(mainPage.promoCodeApplied)
    );
    Assertions.assertEquals(promoCodeAppliedString, promoCodeApplied.getText());

    // finally see if the price after discount is correct

    // get the discount percentage as a string
    String percentString = mainPage.discount.getText().split("%")[0];

    // calculate the discounted payment percentage
    BigDecimal percentNumber = new BigDecimal(1.00 - Integer.parseInt(percentString) / 100.00);

    // calculate the discounted price and assert on its value in the application
    BigDecimal totalPrice = new BigDecimal(mainPage.totalAmount.getText());
    BigDecimal percentNumberToTwoDecimals = percentNumber.setScale(2, RoundingMode.DOWN);
    BigDecimal discountedPrice = totalPrice.multiply(percentNumberToTwoDecimals);

    // find the number of decimal points in the value on the webpage
    BigDecimal discountedPriceInApp = new BigDecimal(mainPage.discountAmount.getText());
    int numberOfDecimals = Math.max(0, discountedPriceInApp.stripTrailingZeros().scale());
    BigDecimal resultantDiscountedPrice = discountedPrice.setScale(numberOfDecimals, BigDecimal.ROUND_DOWN);

    Assertions.assertEquals(mainPage.discountAmount.getText(), resultantDiscountedPrice.toString());
  }
}
