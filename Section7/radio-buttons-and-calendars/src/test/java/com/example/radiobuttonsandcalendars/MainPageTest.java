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
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class MainPageTest {
  String[] monthsArray = {
      "january", "february", "march", "april", "may", "june",
      "july", "august", "september", "october", "november", "december"
  };

  List<String> monthsList = Arrays.asList(monthsArray);

  private final String departureCityDefaultText = "Departure City";
  private final String departureCityText = "Bengaluru (BLR)";
  private final String arrivalCityText = "Chennai (MAA)";
  private final String disabledArrivalDateText = "display: block; opacity: 0.5;";
  private final String enabledArrivalDateText = "display: block; opacity: 1;";

  private final String passengerInformationText = "3 Adult";
  private WebDriver driver;
  private MainPage mainPage;
  private final String url = "https://rahulshettyacademy.com/dropdownsPractise/";
  private Duration duration;
  private WebDriverWait wait;

  private String formatMonthOrDay(String monthOrDay) {
    return monthOrDay.length() < 2 ? "0" + monthOrDay : monthOrDay;
  }
  private LocalDate createLocalDateInstance(String monthAndYear, String day) {
    String formattedMonth, formattedDay, formattedDate;
    int monthNumber;
    DateTimeFormatter formatDateTemplate;
    LocalDate date;
    // monthAndYear come in formatted as nameOfMonth<space>yyyy
    // example February 2023

    // split on the space first element is the month the second element is the year
    String[] monthAndYearArray = monthAndYear.split("\s+");
    String month = monthAndYearArray[0], year =  monthAndYearArray[1];

    // use the list of months to get the month number and prefix a "0" to
    // the month number if it is < 10 do the same for the day of the month
    monthNumber = monthsList.indexOf(month.toLowerCase()) + 1;
    formattedMonth = formatMonthOrDay(Integer.toString(monthNumber));
    formattedDay = formatMonthOrDay(day);

    // date time format will be yyyy-mm-dd, example 2023-02-24
    formatDateTemplate = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ENGLISH);
    formattedDate = year + "-" + formattedMonth + "-" + formattedDay;

    // use the DateTimeFormatter and its desired string representation to get a LocalDate object
    date = LocalDate.parse(formattedDate, formatDateTemplate);
    return date;
  }
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

  @Test
  public void testOneWayTrip() throws InterruptedException {
    // we land on the airline reservation page and check that the one way
    // radio button is selected and the round trip button is not selected
    mainPage.oneWayTripRadioButton.click();
    assertTrue(mainPage.oneWayTripRadioButton.isSelected());
    assertFalse(mainPage.roundTripRadioButton.isSelected());

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
    // greyed out, ie, disabled
    assertEquals(disabledArrivalDateText, mainPage.enableDisableArrivalController.getAttribute("style"));

    // now select the input for the number and kind of passengers
    mainPage.numberAndKindOfPassengersInput.click();

    // wait until the increment number of adults button is visible and the add two more adults
    WebElement incrementNumberOfAdultsButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.incrementNumberOfAdultsButton)
    );

    // click the "+" button twice assert on three adults as passengers
    for (int i = 0; i < 2; i++) incrementNumberOfAdultsButton.click();
    boolean twoMoreAdultsAdded = wait.until(
        ExpectedConditions.textToBePresentInElement(mainPage.numberOfAdultsNumber, "3")
    );
    assertTrue(twoMoreAdultsAdded);

    // click the done button to finish adding two more adults
    mainPage.doneButtonForNumberOfPassengers.click();

    boolean numberOfPassengersCaptured = wait.until(
        ExpectedConditions.textToBePresentInElement(mainPage.numberAndKindOfPassengersInput, passengerInformationText)
    );

    assertTrue(numberOfPassengersCaptured);

    mainPage.seniorCitizenDiscountButton.click();
    assertTrue(mainPage.seniorCitizenDiscountButton.isSelected());

    mainPage.findFlightsButton.click();

    boolean flightFound = wait.until(
        ExpectedConditions.attributeToBe(mainPage.departureInput, "value",
            departureCityDefaultText)
    );

    assertTrue(flightFound);
  }
}
