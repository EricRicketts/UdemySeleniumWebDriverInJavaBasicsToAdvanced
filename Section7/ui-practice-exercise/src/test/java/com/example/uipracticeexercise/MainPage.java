package com.example.uipracticeexercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.NAME, using = "name")
  public WebElement nameInput;

  @FindBy(how = How.NAME, using = "email")
  public WebElement emailInput;

  @FindBy(how = How.ID, using = "exampleInputPassword1")
  public WebElement passwordInput;

  @FindBy(how = How.ID, using = "exampleCheck1")
  public WebElement loveIceCreamCheckbox;

  @FindBy(how = How.ID, using = "exampleFormControlSelect1")
  public WebElement selectGender;

  @FindBy(how = How.XPATH, using = "//input[@type='radio']")
  public List<WebElement> employmentStatusRadioButtons;

  @FindBy(how = How.CSS, using = "input[type='date']")
  public WebElement dateOfBirthInput;

  @FindBy(how = How.CSS, using = "input[type='submit']")
  public WebElement submitButton;

  @FindBy(how = How.XPATH, using = "//div[contains(@class, 'alert')]")
  public WebElement successAlert;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
