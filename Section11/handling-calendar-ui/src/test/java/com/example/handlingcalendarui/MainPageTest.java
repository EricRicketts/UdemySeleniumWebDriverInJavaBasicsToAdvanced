package com.example.handlingcalendarui;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import javax.annotation.Nullable;
import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;

  private static ExpectedCondition<Boolean> isVisibleInViewport(WebElement element) {
    return new ExpectedCondition<>() {
      @Nullable
      @Override
      public Boolean apply(@Nullable WebDriver input) {
        if (input != null) {
          return (Boolean) ((JavascriptExecutor) input).executeScript(
              "var element = arguments[0]; " +
                  "var rect = element.getBoundingClientRect(); " +
                  "return ( " +
                  "rect.top >= 0 && " +
                  "rect.left >= 0 && " +
                  "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                  "rect.right <= (window.innerWidth || document.documentElement.clientWidth) " +
                  ");"
              , element);
        } else {
          return false;
        }
      }
    };
  }
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


    // get the current date
    String currentDateString = String.valueOf(java.time.LocalDate.now());
    // format will be YYYY-MM-DD, so get the last element of hte split array
    String[] yearMonthDay = currentDateString.split("-");
    String currentDay = yearMonthDay[2];


    // ensure the travel date input field is visible
    WebElement travelDateInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.travelDateInput)
    );
    // scroll the travel date input to the top of the page it is visible within the viewport
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", travelDateInput);

    // got this function from
    // https://medium.com/bliblidotcom-techblog/how-to-create-custom-expectedcondition-to-check-whether-an-element-within-web-viewport-not-on-fad42bc4d0f9
    // the code below works but I really should put it in a separate class like the URL suggested
    boolean isVisibleInViewport = wait.until(isVisibleInViewport(travelDateInput));
    Assertions.assertTrue(isVisibleInViewport);

    // move the mouse to the center of the travel date input and then click
    new Actions(driver).moveToElement(travelDateInput).build().perform();
    mainPage.travelDateInput.click();

    // cycle through all the days viewed in the calendar until you come across
    // the desired day of the month
    for (int index = 0; index < mainPage.allDatesForTravel.size(); index++) {
      currentDayOfMonth = mainPage.allDatesForTravel.get(index);
      if (currentDayOfMonth.getText().equalsIgnoreCase(currentDay)) {
        currentDayOfMonth.click();
        break;
      }
    }
  }
}
