package com.example.alertspractice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.ID, using = "name")
  public WebElement nameInput;

  @FindBy(how = How.ID, using = "alertbtn")
  public WebElement alertButton;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
