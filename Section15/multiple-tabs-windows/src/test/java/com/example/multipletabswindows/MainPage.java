package com.example.multipletabswindows;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
  @FindBy(xpath = "//*[@data-test-marker='Developer Tools']")
  public WebElement seeDeveloperToolsButton;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
