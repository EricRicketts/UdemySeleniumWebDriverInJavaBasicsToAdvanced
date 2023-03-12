package com.example.assignment_3_synchronization_explicit_wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.CSS, using = "input#username")
  public WebElement usernameInput;

  @FindBy(how = How.CSS, using = "input#password")
  public WebElement passwordInput;

  @FindBy(how = How.CSS, using = "input[value='user']")
  public WebElement userRadioButton;

  @FindBy(how = How.CSS, using = "button#okayBtn")
  public WebElement okayButton;

  @FindBy(how = How.TAG_NAME, using = "select")
  public WebElement userTypeSelect;

  @FindBy(how = How.CSS, using = "input#terms")
  public WebElement termsAndConditionsCheckbox;

  @FindBy(how = How.CSS, using = "input#signInBtn")
  public WebElement signInButton;

  @FindBy(how = How.CSS, using = "div#navbarResponsive a")
  public WebElement checkoutButton;

  @FindBy(how = How.CSS, using = "div.card-footer > button")
  public List<WebElement> addToCartButtons;

  @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Checkout')]")
  public WebElement finalCheckoutButton;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
