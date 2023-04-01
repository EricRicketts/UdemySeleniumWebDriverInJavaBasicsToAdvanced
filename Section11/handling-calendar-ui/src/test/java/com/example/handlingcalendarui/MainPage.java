package com.example.handlingcalendarui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

// page_url = https://www.jetbrains.com/
public class MainPage {
  @FindBy(how = How.ID, using = "form-field-travel_comp_date")
  public WebElement travelDateInput;

  @FindBy(how = How.CSS, using = "div.flatpickr-current-month")
  public WebElement currentCalendarMonth;

  @FindBy(how = How.CSS, using = "div.dayContainer")
  public WebElement dayOfMonthContainer;

  @FindBy(how = How.CSS, using = "div.dayContainer > span")
  public List<WebElement> allDatesForTravel;

  @FindBy(how = How.CSS, using = "div.elementor-social-icons-wrapper.elementor-grid")
  public WebElement socialMediaIconsDivElement;
  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
