package com.example.advancedlocatorsidentificationpart2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

  @FindBy(how = How.ID, using = "inputUsername")
  WebElement usernameInput;

  @FindBy(how = How.NAME, using = "inputPassword")
  WebElement passwordInput;

  @FindBy(how = How.CSS, using = "button[class*='submit signIn']")
  WebElement signInButton;

  @FindBy(how = How.CSS, using = "div.login-container p")
  WebElement successfulLoginParagraph;

  @FindBy(how = How.XPATH, using = "//button[@class='logout-btn']")
  WebElement logoutButton;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
