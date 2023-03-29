package com.example.handlingcalendarui;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;

  @BeforeAll
  public static void oneTimeSetup() {
    System.setProperty("webdriver.http.factory", "jdk-http-client");
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    final String url = "https://www.path2usa.com/travel-companion/";
    final int implicitWaitTime = 5;
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(url);

    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testCalendarUI() throws InterruptedException {
    int explicitWaitTime = 10;
    Duration  duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    WebElement travelDateInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.travelDateInput)
    );
    Assertions.assertNotNull(travelDateInput);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", travelDateInput);

    // before we click on the date field we need to be make sure the scrolling id done or else
    // we will get an intercepted component error

    // get the current date
    String currentDateString = String.valueOf(java.time.LocalDate.now());
    // format will be YYYY-MM-DD, so get the last element of hte split array
    String[] yearMonthDay = currentDateString.split("-");
    String currentDay = yearMonthDay[2];
    Thread.sleep(3000);
  }
}
