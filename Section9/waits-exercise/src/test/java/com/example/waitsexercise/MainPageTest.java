package com.example.waitsexercise;

import dev.failsafe.internal.util.Assert;
import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class MainPageTest {
  private final String url = "https://rahulshettyacademy.com/seleniumPractise/#/";
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;

  WebElement walnuts;

  public void addItems(WebDriver driver, List<String> items) {
    int itemCount = 0;
    for (int index = 0; index < mainPage.allProductNames.size(); index++) {
      String currentProductTitle = mainPage.allProductNames.get(index).getText();
      String currentProductName = currentProductTitle.split("-")[0].trim();

      if (items.contains(currentProductName)) {
        itemCount++;
        mainPage.allAddToCartButtons.get(index).click();
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
    duration = Duration.ofSeconds(15);
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
  public void testAddItemsToCart() throws InterruptedException {
    String[] itemsArray = {"Cucumber", "Brocolli", "Beetroot"};
    List<String> items = Arrays.asList(itemsArray);
    Assertions.assertNotNull(walnuts);
    addItems(driver, items);
    Thread.sleep(3000);
  }

}
