package com.example.assignment8;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
  @FindBy(how = How.CSS, using = "input#autocomplete")
  public WebElement inputForDynamicDropdown;

  @FindBy(how = How.ID, using = "ui-id-1")
  public WebElement pulldownList;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
