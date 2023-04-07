package com.example.scrollingwithjavascrptexecutor;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;

  @BeforeAll
  public static void oneTimeSetup() {
      SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    String url = "https://rahulshettyacademy.com/AutomationPractice/";
    int implicitTimeWait = 5;
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitTimeWait));
    driver.get(url);

    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testScrollExercise() throws InterruptedException {
    int explicitTimeWait = 10;
    int totalAmount = 0;
    Duration duration = Duration.ofSeconds(explicitTimeWait);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // assert that the product table exists on the webpage before doing anything
    Assertions.assertNotNull(mainPage.productTable);

    // use the utility class to scroll the product table into view and assert it is in the viewport
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", mainPage.productTable);
    boolean productTableInView = wait.until(ExpectedConditionUtils.isVisibleInViewport(mainPage.productTable));
    Assertions.assertTrue(productTableInView);

    // assert that list for all the product purchase amounts is not empty then
    // cycle through the data in the table and sum each amount
    Assertions.assertTrue(mainPage.productAmounts.size() > 0);
    for (int index = 0; index < mainPage.productAmounts.size(); index++) {
      String amountText = mainPage.productAmounts.get(index).getText();
      totalAmount += Integer.parseInt(amountText);
    }

    // go to the div element which contains the table total this should match what
    // is obtained by the loop above
    String totalDivElementText = mainPage.totalAmountElement.getText();

    // the text is Total Amount Collected: 296 (or some number)
    // split on the ":" so the number remains in the last array position
    String[] totalAmountEntireTextArray = totalDivElementText.split(":");
    String totalAmountNumberString = totalAmountEntireTextArray[totalAmountEntireTextArray.length - 1].trim();

    Assertions.assertEquals(Integer.parseInt(totalAmountNumberString), totalAmount);
  }
}
