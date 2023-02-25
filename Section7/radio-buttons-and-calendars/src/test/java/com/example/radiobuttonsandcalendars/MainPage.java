package com.example.radiobuttonsandcalendars;

import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  private final String departureCityXpath =
      "//div[@id='ctl00_mainContent_ddl_originStation1_CTNR'] //a[text()=' Bengaluru (BLR)']";

  private final String arrivalCityCss =
      "div#glsctl00_mainContent_ddl_destinationStation1_CTNR a[text='Chennai (MAA)']";

  @FindBy(how = How.ID, using = "ctl00_mainContent_rbtnl_Trip_0")
  public WebElement oneWayTripRadioButton;

  @FindBy(how = How.ID, using = "ctl00_mainContent_rbtnl_Trip_1")
  public WebElement roundTripRadioButton;

  @FindBy(how = How.ID, using = "ctl00_mainContent_ddl_originStation1_CTXT")
  public WebElement departureInput;

  @FindBy(how = How.ID, using = "ctl00_mainContent_ddl_destinationStation1_CTXT")
  public WebElement arrivalInput;

  @FindBy(how = How.XPATH, using = departureCityXpath)
  public WebElement departureCity;

  @FindBy(how = How.CSS, using = arrivalCityCss)
  public WebElement arrivalCity;

  @FindBy(how = How.NAME, using = "ctl00$mainContent$view_date1")
  public WebElement departureDateInput;


  @FindBy(how = How.CSS, using = "a[class*='ui-state-highlight']")
  public WebElement defaultDepartureDate;

  @FindBy(how = How.ID, using = "Div1")
  public WebElement enableDisableArrivalController;

  @FindBy(how = How.ID, using = "divpaxinfo")
  public WebElement numberAndKindOfPassengersInput;

  @FindBy(how = How.ID, using = "hrefIncAdt")
  public WebElement incrementNumberOfAdultsButton;

  @FindBy(how = How.ID, using = "spanAudlt")
  public WebElement numberOfAdultsNumber;

  @FindBy(how = How.ID, using = "btnclosepaxoption")
  public WebElement doneButtonForNumberOfPassengers;

  @FindBy(how = How.ID, using = "ctl00_mainContent_chk_SeniorCitizenDiscount")
  public WebElement seniorCitizenDiscountButton;

  @FindBy(how = How.ID, using = "ctl00_mainContent_btn_FindFlights")
  public WebElement findFlightsButton;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
