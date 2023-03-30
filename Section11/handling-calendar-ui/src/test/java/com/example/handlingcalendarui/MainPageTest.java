package com.example.handlingcalendarui;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.Objects;

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
    WebElement currentDayOfMonth;
    int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // wait until the calendar input is visible, then click to
    // bring up the calendar itself
    WebElement socialMediaIconsDivElement = wait.until(
        ExpectedConditions.visibilityOf(mainPage.socialMediaIconsDivElement)
    );
    Assertions.assertNotNull(socialMediaIconsDivElement);

    // scroll the input to the top of the page
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", mainPage.travelDateInput);
    Thread.sleep(1000);

    // get the current date
    String currentDateString = String.valueOf(java.time.LocalDate.now());
    // format will be YYYY-MM-DD, so get the last element of hte split array
    String[] yearMonthDay = currentDateString.split("-");
    String currentDay = yearMonthDay[2];

    // click on the calendar input to reveal the calendar
    // cycle through all the days viewed in the calendar until you come across
    // the desired day of the month
    mainPage.travelDateInput.click();
    for (int index = 0; index < mainPage.allDatesForTravel.size(); index++) {
      currentDayOfMonth = mainPage.allDatesForTravel.get(index);
      if (Objects.equals(currentDayOfMonth.getText(), currentDay)) {
        currentDayOfMonth.click();
        break;
      }
    }
  }
}
