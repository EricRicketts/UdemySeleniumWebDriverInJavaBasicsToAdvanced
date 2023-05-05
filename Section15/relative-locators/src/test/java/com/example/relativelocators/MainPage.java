package com.example.relativelocators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.CSS, using = "footer > div > p")
  public WebElement footer;

  @FindBy(how = How.CSS, using = "input.form-control[name$='name']")
  public WebElement nameInput;

  @FindBy(how = How.XPATH, using = "//label[@for='dateofBirth']")
  public WebElement dateOfBirthLabel;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
