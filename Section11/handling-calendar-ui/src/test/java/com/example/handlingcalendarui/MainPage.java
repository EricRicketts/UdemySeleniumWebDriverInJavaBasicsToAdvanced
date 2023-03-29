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

  @FindBy(how = How.CLASS_NAME, using = ".flatpickr-day")
  public List<WebElement> allDatesForTravel;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
