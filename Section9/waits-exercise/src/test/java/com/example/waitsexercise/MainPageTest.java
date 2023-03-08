package com.example.waitsexercise;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
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

  private void addItemsToCart(List<WebElement> items) {
    String addToCartLocator = "//div[@class='product-action']/button";
    items.forEach(item -> item.findElement(By.xpath(addToCartLocator)).click());
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
    addItemsToCart(items);
    Thread.sleep(5000);
  }
}
