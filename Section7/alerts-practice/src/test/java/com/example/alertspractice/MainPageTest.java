package com.example.alertspractice;

import org.example.SetWebDriverLocation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class MainPageTest {
  private final String url = "https://rahulshettyacademy.com/AutomationPractice/";

  private final String myName = "Eric Ricketts";
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
  public void testAlerts() throws InterruptedException {
    // find the name input for the alert button
    // enter your name
    mainPage.nameInput.click();
    mainPage.nameInput.sendKeys(myName);

    // wait until the text is visible on the input before clicking the alter button
    boolean nameIsVisible = wait.until(
        ExpectedConditions.attributeToBe(mainPage.nameInput, "value", myName)
    );

    assertTrue(nameIsVisible);

    // now that the name is visible click the alert button
    mainPage.alertButton.click();

    Thread.sleep(5000);
  }
}
