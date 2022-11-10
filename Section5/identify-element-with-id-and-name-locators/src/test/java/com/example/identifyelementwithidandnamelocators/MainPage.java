package com.example.identifyelementwithidandnamelocators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {

  @FindBy(how = How.ID, using = "inputUsername")
  public WebElement inputUserName;

  @FindBy(how = How.NAME, using = "inputPassword")
  public WebElement inputPassword;

  @FindBy(how = How.CSS, using = "button.submit.signInBtn")
  public WebElement signInButton;

  @FindBy(how = How.CSS, using = "p.error")
  public WebElement errorParagraph;

  @FindBy(how = How.LINK_TEXT, using = "Forgot your password?")
  public WebElement forgotYourPasswordLink;

  @FindBy(how = How.XPATH, using = "//input[@placeholder='Name']")
  public WebElement forgotPasswordName;

  @FindBy(how = How.CSS, using = "input[placeholder='Email']")
  public WebElement forgotPasswordEmail;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
