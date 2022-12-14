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

  @FindBy(how = How.LINK_TEXT, using = "Forgot your password?")
  WebElement forgotPasswordLink;

  @FindBy(how = How.CSS, using = "div.login-container p")
  WebElement successfulLoginParagraph;

  @FindBy(how = How.XPATH, using = "//button[text()='Log Out']")
  WebElement logoutButton;

  @FindBy(how = How.TAG_NAME, using = "h2")
  WebElement loginHeading;

  @FindBy(how = How.CLASS_NAME, using = "reset-pwd-btn")
  WebElement resetPasswordButton;

  @FindBy(how = How.CLASS_NAME, using = "infoMsg")
  WebElement informationMessage;

  @FindBy(how = How.CSS, using = "button.go-to-login-btn")
  WebElement goToLoginButton;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
