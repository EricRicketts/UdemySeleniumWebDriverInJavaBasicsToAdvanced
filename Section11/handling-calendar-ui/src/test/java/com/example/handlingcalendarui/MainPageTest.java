package com.example.handlingcalendarui;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.example.handlingcalendarui.ExpectedConditionUtils;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
  public void testCalendarUI() throws InterruptedException, ParseException {
    int explicitWaitTime = 10;
    String selectedTravelDate = "";
    String allDatesCSSLocator = "div.dayContainer > span";
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // wait until the calendar input is visible, then click to
    // bring up the calendar itself
    WebElement socialMediaIconsDivElement = wait.until(
        ExpectedConditions.visibilityOf(mainPage.socialMediaIconsDivElement)
    );
    Assertions.assertNotNull(socialMediaIconsDivElement);

    // get the current date format is YYYY-MM-DD
    String currentDateString = String.valueOf(java.time.LocalDate.now());


    // ensure the travel date input field is visible
    WebElement travelDateInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.travelDateInput)
    );
    // scroll the travel date input to the top of the page it is visible within the viewport
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", travelDateInput);

    // got this function from
    // https://medium.com/bliblidotcom-techblog/how-to-create-custom-expectedcondition-to-check-whether-an-element-within-web-viewport-not-on-fad42bc4d0f9
    // the code below works I really should put it in a separate class like the URL suggested
    boolean isVisibleInViewport = wait.until(ExpectedConditionUtils.isVisibleInViewport(travelDateInput));
    Assertions.assertTrue(isVisibleInViewport);

    // move the mouse to the center of the travel date input and then click
    new Actions(driver).moveToElement(travelDateInput).build().perform();
    mainPage.travelDateInput.click();

    // wait for the calendar of the current month to appear
    WebElement currentCalendarMonth = wait.until(
            ExpectedConditions.visibilityOf(mainPage.currentCalendarMonth)
    );
    Assertions.assertNotNull(currentCalendarMonth);

    // wait for all the days of month to appear
    WebElement dayOfMonthContainer = wait.until(
            ExpectedConditions.visibilityOf(mainPage.dayOfMonthContainer)
    );
    Assertions.assertNotNull(dayOfMonthContainer);

    // cycle through all the days viewed in the calendar until you come across
    // the desired date.  I had to do this because I was not able to extract
    // text from the span element, I have no idea why this did not work.  So the solution
    // is to format the day from the aria-label into the format extracted from LocalDate

    // unfortunately, this code performs inconsistently sometimes it works sometimes it does not
    for (int index = 0; index < driver.findElements(By.cssSelector("div.dayContainer > span")).size(); index++) {
      String date = "";
      try {
        date = driver.findElements(By.cssSelector("div.dayContainer > span"))
                .get(index).getAttribute("aria-label");
      } catch(StaleElementReferenceException error) {
        date = driver.findElements(By.cssSelector("div.dayContainer > span"))
                .get(index).getAttribute("aria-label");
      }
      String formattedDate = HandlingCalendarUIDateUtils.convertAriaLabelDateToLocalDateFormat(date);
      if (Objects.equals(formattedDate, currentDateString)) {
        driver.findElements(By.cssSelector("div.dayContainer > span")).get(index).click();
        selectedTravelDate = driver.findElement(By.id("form-field-travel_comp_date")).getAttribute("value");
        break;
      }
    }
    Assertions.assertEquals(currentDateString, selectedTravelDate);
  }
}
