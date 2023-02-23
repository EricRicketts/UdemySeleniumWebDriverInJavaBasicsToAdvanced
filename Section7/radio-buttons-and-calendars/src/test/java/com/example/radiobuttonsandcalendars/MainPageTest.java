package com.example.radiobuttonsandcalendars;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private final String departureCityText = "Bengaluru (BLR)";
  private final String arrivalCityText = "Chennai (MAA)";
  private final String disabledArrivalDateText = "display: block; opacity: 0.5;";
  private final String enabledArrivalDateText = "display: block; opacity: 1;";
  private WebDriver driver;
  private MainPage mainPage;
  private final String url = "https://rahulshettyacademy.com/dropdownsPractise/";
  private Duration duration;
  private WebDriverWait wait;

  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    duration = Duration.ofSeconds(10);
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.get(url);

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Disabled
  @Test
  public void testOneWayTrip() throws InterruptedException {
    // we land on the airline reservation page and check that the one way
    // radio button is selected
    assertTrue(mainPage.oneWayTripRadioButton.isSelected());

    // ensure the arrival date is currently disabled, note is disabled
    // not functionally but by styling, the opacity of the styling causes
    // the input field to appear greyed out.  If the element were truly
    // disabled then there would be no interaction with it
    assertEquals(disabledArrivalDateText, mainPage.enableDisableArrivalController.getAttribute("style"));

    // click on the departure city input to bring up the cities for departure
    mainPage.departureInput.click();

    // wait for the departure city to appear
    WebElement departureCity = wait.until(
        ExpectedConditions.visibilityOf(mainPage.departureCity)
    );

    // select the departure city and assert the departure input has captured he city
    departureCity.click();
    assertEquals(departureCityText, mainPage.departureInput.getAttribute("value"));

    // click on the arrival city input to bring up the cities for arrival
    mainPage.arrivalInput.click();

    // wait for the arrival city to appear
    WebElement arrivalCity = wait.until(
        ExpectedConditions.visibilityOf(mainPage.arrivalCity)
    );

    // select the arrival city and assert the arrival city has captured the city
    arrivalCity.click();
    assertEquals(arrivalCityText, mainPage.arrivalInput.getAttribute("value"));

    // click on the departure date input to bring up the departure calendar
    mainPage.departureDateInput.click();

    // wait for the desired departure date to appear
    WebElement defaultDepartureDate = wait.until(
        ExpectedConditions.visibilityOf(mainPage.defaultDepartureDate)
    );

    // get the departure date text which will only be a day of the month
    String defaultDepartureDateText = defaultDepartureDate.getText();

    // click on the departure date and assert the departure date input has captured the day of the
    // month of departure, the day of the month will appear in the string of the day of departure
    defaultDepartureDate.click();
    assertTrue(mainPage.departureDateInput.getAttribute("value").contains(defaultDepartureDateText));

    // assert it is still a one way trip we do this by making sure the style of the arrival date is still
    // greyed out
    assertEquals(disabledArrivalDateText, mainPage.enableDisableArrivalController.getAttribute("style"));
  }

  @Test
  public void testRoundTrip() throws InterruptedException {
    // before selecting the round trip button ensure the arrival date is greyed out
    // which means its opacity will be 0.5
    assertEquals(disabledArrivalDateText, mainPage.enableDisableArrivalController.getAttribute("style"));

    // check the round trip button to enable all calendars
    mainPage.roundTripRadioButton.click();

    // Experiment with different techniques to see if a radio button
    // is selected or not.  If the round trip button is selected then
    // the one way trip button must not be selected.  Important note here
    // elementToBeSelected is a wait condition it returns boolean true
    // within a set time limit waiting for the element to be selected.
    // if the element fails to be set during the time constraint an
    // exception is thrown.
    boolean roundTripTripRadioButtonSelected = wait.until(
        ExpectedConditions.elementToBeSelected(mainPage.roundTripRadioButton)
    );

    // the round trip button should be selected but the one way trip
    // button should not be selected
    boolean oneWayTripRadioButtonSelected = mainPage.oneWayTripRadioButton.isSelected();
    boolean[] expected = new boolean[]{true, false};
    boolean[] results = new boolean[]{roundTripTripRadioButtonSelected, oneWayTripRadioButtonSelected};
    assertArrayEquals(expected, results);

    // now that round trip is selected the arrival date should not be greyed out, opacity should be 1
    assertEquals(enabledArrivalDateText, mainPage.enableDisableArrivalController.getAttribute("style"));
    // click the departure input to enable to departure city choice
    mainPage.departureInput.click();

    // find the desired departure city and select it, then assert the departure city
    // element has captured it
    WebElement departureCity = wait.until(
      ExpectedConditions.visibilityOf(mainPage.departureCity)
    );

    departureCity.click();

    // this is a more robust way of checking the departure city was selected
    // as the event has to resolve to true or throw an exception
    boolean departureCitySelected = wait.until(
        ExpectedConditions.attributeToBe(mainPage.departureInput, "value", departureCityText)
    );
    assertTrue(departureCitySelected);

    // following the same pattern with the departure city, click the arrival city input
    mainPage.arrivalInput.click();

    // The select list should appear, wait for the arrival city to appear
    // once it appears click it
    WebElement arrivalCity = wait.until(
        ExpectedConditions.visibilityOf(mainPage.arrivalCity)
    );

    arrivalCity.click();

    // ensure the arrival city has been captured by the arrival city input element
    // then assert it was captured
    boolean arrivalCitySelected = wait.until(
        ExpectedConditions.attributeToBe(mainPage.arrivalInput, "value", arrivalCityText)
    );

    assertTrue(arrivalCitySelected);
  }
}
