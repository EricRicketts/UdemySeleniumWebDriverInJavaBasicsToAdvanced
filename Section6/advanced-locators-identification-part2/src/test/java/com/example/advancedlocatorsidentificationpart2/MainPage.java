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

  // this is a CSS regular expression, we recognize this by
  // *= after the attribute and before the '', in this case the *=
  // means contained anywhere in the attribute
  @FindBy(how = How.CSS, using = "button[class*='submit s']")
  WebElement signInButton;

  @FindBy(how = How.LINK_TEXT, using = "Forgot your password?")
  WebElement forgotPasswordLink;

  @FindBy(how = How.CSS, using = "div.login-container p")
  WebElement successfulLoginParagraph;

  // this xpath syntax identifies an element by its
  // text, obviously the content between the opening
  // and closing tags
  @FindBy(how = How.XPATH, using = "//button[text()='Log Out']")
  WebElement logoutButton;

  // find the logout button by using xpath regular expressions
  // key parts of the syntax are contains(@attribute, 'text')]
  @FindBy(how = How.XPATH, using = "//button[contains(@class, 'logout-')]")
  WebElement getLogoutButton;

  @FindBy(how = How.TAG_NAME, using = "h2")
  WebElement loginHeading;

  @FindBy(how = How.CLASS_NAME, using = "reset-pwd-btn")
  WebElement resetPasswordButton;

  @FindBy(how = How.CLASS_NAME, using = "infoMsg")
  WebElement informationMessage;

  @FindBy(how = How.CSS, using = "button.go-to-login-btn")
  WebElement goToLoginButton;

  @FindBy(how = How.CSS, using = "div.overlay-panel.overlay-left")
  WebElement leftOverlayPanel;

  @FindBy(how = How.CSS, using = "div.overlay-panel.overlay-right")
  WebElement rightOverlayPanel;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
