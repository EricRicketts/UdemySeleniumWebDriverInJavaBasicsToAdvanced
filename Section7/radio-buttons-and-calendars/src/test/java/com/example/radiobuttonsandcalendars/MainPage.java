package com.example.radiobuttonsandcalendars;

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

  @FindBy(how = How.NAME, using = "ctl00$mainContent$rbtnl_Trip")
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

  @FindBy(how = How.ID, using = "ctl00_mainContent_view_date2")
  public WebElement arrivalDateInput;

  @FindBy(how = How.CSS, using = "a.ui-state-default.ui-state-highlight")
  public WebElement defaultDepartureDate;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
