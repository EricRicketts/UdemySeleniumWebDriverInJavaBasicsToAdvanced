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

  @Test
  public void testOneWayTrip() throws InterruptedException {
    // we land on the airline reservation page and check that the one way
    // radio button is selected
    assertTrue(mainPage.oneWayTripRadioButton.isSelected());
    assertFalse(mainPage.roundTripRadioButton.isSelected());

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

    // assert it is still a one way trip
    assertTrue(mainPage.oneWayTripRadioButton.isSelected());
  }

  @Test
  public void testRoundTrip() throws InterruptedException {
    // check the round trip button to enable all calendars
    mainPage.roundTripRadioButton.click();
    assertTrue(mainPage.roundTripRadioButton.isSelected());
    assertFalse(mainPage.oneWayTripRadioButton.isSelected());

    // click the departure input to enable to departure city choice
    mainPage.departureInput.click();

    WebElement departureCity = wait.until(
        
    )
  }
}
