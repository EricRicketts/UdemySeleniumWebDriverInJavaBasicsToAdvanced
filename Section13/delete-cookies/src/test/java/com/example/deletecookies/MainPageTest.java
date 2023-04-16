package com.example.deletecookies;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;

  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    String url = "https://rahulshettyacademy.com/AutomationPractice/";
    duration = Duration.ofSeconds(explicitWaitTime);
    ChromeOptions options = new ChromeOptions();
    // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(url);

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testDeleteCookies() {
    // delete all cookies
    driver.manage().deleteAllCookies();

    // scroll the product view into view
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true)", mainPage.productTable);

    // assert the product table is in view
    boolean productTableIsVisible = wait.until(ExpectedConditionUtils.isVisibleInViewport(mainPage.productTable));
    Assertions.assertTrue(productTableIsVisible);
  }
}
