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
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

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

  @Disabled("no need to run work on next test only")
  @Test
  public void testCalendarUI() throws ParseException {
    int explicitWaitTime = 10;
    String dayFromAriaLabel = "";
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // wait until elements at the bottom of the page are visible
    // to ensure the page has loaded
    WebElement socialMediaIconsDivElement = wait.until(
        ExpectedConditions.visibilityOf(mainPage.socialMediaIconsDivElement)
    );
    Assertions.assertNotNull(socialMediaIconsDivElement);

    // get the current date format is YYYY-MM-DD
    String currentDateString = String.valueOf(java.time.LocalDate.now());
    String currentDay = currentDateString.split("-")[2];


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

    // cycle through all the days of the month until you land on the current day
    for (int index = 0; index < mainPage.allDatesForTravel.size(); index++) {
      String date;
      // this seems to have rid myself of the element staleness exception in this part of the loop
      try {
        date = mainPage.allDatesForTravel.get(index).getAttribute("aria-label");
      } catch(StaleElementReferenceException error) {
        date = mainPage.allDatesForTravel.get(index).getAttribute("aria-label");
      }
      String formattedDate = HandlingCalendarUIDateUtils.convertAriaLabelDateToLocalDateFormat(date);
      if (Objects.equals(formattedDate, currentDateString)) {
        // the aria label format will be something like "month day, year" => April 3, 2023
        // split on the comma to get something like "April 3" then split on the space to
        // get the day of the month
        dayFromAriaLabel = date.split(",")[0].split(" ")[1];
        /*
        None of the code works below because the span element is non-interactive, in the calendar the
        spans are the only elements which contain the dates.  Ideally, these spans should be wrapped
        in a paragraph element which is interactive
        So in order to run some kind of test I decide to compare the day of the month from the LocalDate
        object and then parse the day from the aria-label
        new Actions(driver).moveToElement(mainPage.allDatesForTravel.get(index)).build().perform();
        dayToSelect = mainPage.allDatesForTravel.get(index).getText();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click", dayToSelect);
        selectedTravelDate = driver.findElement(By.id("form-field-travel_comp_date")).getAttribute("value");
        */
        break;
      }
    }
    Assertions.assertEquals(Integer.parseInt(currentDay), Integer.parseInt(dayFromAriaLabel));
  }

  @Test
  public void testNavigatingMonths() throws InterruptedException {
    int explicitWaitTime = 10;
    String nextMonthName;
    int nextMonthNumber;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // wait until elements at the bottom of the page are visible
    // to ensure the page has loaded
    WebElement socialMediaIconsDivElement = wait.until(
            ExpectedConditions.visibilityOf(mainPage.socialMediaIconsDivElement)
    );
    Assertions.assertNotNull(socialMediaIconsDivElement);

    // get the current date format is YYYY-MM-DD
    String currentDateString = String.valueOf(java.time.LocalDate.now());
    int currentMonthNumber = Integer.parseInt(currentDateString.split("-")[1]);
    nextMonthNumber = currentMonthNumber;
    String currentMonthName = new DateFormatSymbols().getMonths()[currentMonthNumber - 1];


    // ensure the travel date input field is visible
    WebElement travelDateInput = wait.until(
      ExpectedConditions.visibilityOf(mainPage.travelDateInput)
    );
    // scroll the travel date input to the top of the page it is visible within the viewport
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", travelDateInput);

    // got this function from
    // https://medium.com/bliblidotcom-techblog/how-to-create-custom-expectedcondition-to-check-whether-an-element-within-web-viewport-not-on-fad42bc4d0f9
    boolean isVisibleInViewport = wait.until(ExpectedConditionUtils.isVisibleInViewport(travelDateInput));
    Assertions.assertTrue(isVisibleInViewport);

    // move the mouse to the center of the travel date input and then click
    new Actions(driver).moveToElement(travelDateInput).build().perform();
    mainPage.travelDateInput.click();
    // grab the current display month from the calendar and compare it with
    // the month derived from the LocalDate object
    String currentMonthFromCalendar = mainPage.currentDisplayMonth.getText();
    Assertions.assertEquals(currentMonthName, currentMonthFromCalendar);

    nextMonthNumber += 1;
    nextMonthName = new DateFormatSymbols().getMonths()[nextMonthNumber - 1];
    WebElement nextMonthAdvanceIcon = wait.until(
        ExpectedConditions.visibilityOf(mainPage.nextMonthAdvanceIcon)
    );
    nextMonthAdvanceIcon.click();
    String currentMonthLocator = "div.flatpickr-month > div.flatpickr-current-month";
    boolean currentMontIsGone = wait.until(
      ExpectedConditions.invisibilityOfElementWithText(By.cssSelector(currentMonthLocator), currentMonthName + " ")
    );
    Assertions.assertTrue(currentMontIsGone);
    WebElement nextMonthElement = driver.findElement(By.cssSelector(currentMonthLocator));
    new Actions(driver).moveToElement(nextMonthElement).build().perform();
    Assertions.assertEquals(nextMonthName, driver.findElement(By.cssSelector(currentMonthLocator)).getText().trim());
  }
}
