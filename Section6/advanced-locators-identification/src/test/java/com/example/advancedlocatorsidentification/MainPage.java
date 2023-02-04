package com.example.advancedlocatorsidentification;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

  @FindBy(how = How.ID, using = "inputUsername")
  public WebElement inputUserName;

  @FindBy(how = How.NAME, using = "inputPassword")
  public WebElement inputPassword;

  @FindBy(how = How.CSS, using = "input[type*=pass]")
  public WebElement getInputPasswordByCssRegex;

  @FindBy(how = How.CSS, using = "button.submit.signInBtn")
  public WebElement signInButton;

  @FindBy(how = How.XPATH, using = "//button[contains(@class,'submit signInBtn')]")
  public WebElement signInButtonXpathRegex;

  @FindBy(how = How.CSS, using = "p.error")
  public WebElement errorParagraph;

  @FindBy(how = How.LINK_TEXT, using = "Forgot your password?")
  public WebElement forgotPasswordLink;

  @FindBy(how = How.XPATH, using = "//input[@placeholder='Name']")
  public WebElement forgotPasswordName;

  @FindBy(how = How.CSS, using = "input[placeholder='Email']")
  public WebElement forgotPasswordEmail;

  @FindBy(how = How.XPATH, using = "//input[@type='text'][1]")
  public WebElement getForgotPasswordNameXpathArray;

  @FindBy(how = How.XPATH, using = "//input[@type='text'][2]")
  public WebElement getForgotPasswordEmailXpathArray;

  @FindBy(how = How.CSS, using = "input[type='text']:nth-child(2)")
  public WebElement getForgotPasswordNameCssArray;

  @FindBy(how = How.CSS, using = "input[type='text']:nth-child(3)")
  public WebElement getForgotPasswordEmailCssArray;

  @FindBy(how = How.XPATH, using = "//form/h2")
  public WebElement getForgotPasswordH2;

  @FindBy(how = How.XPATH, using = "//form/input[1]")
  public WebElement getForgotPasswordNameXpathTags;

  @FindBy(how = How.XPATH, using = "//form/input[2]")
  public WebElement getForgotPasswordEmailXpathTags;

  @FindBy(how = How.XPATH, using = "//form/input[3]")
  public WebElement getForgotPasswordPhoneNumberXpathTags;

  @FindBy(how = How.CSS, using = "button.reset-pwd-btn")
  public WebElement resetLoginButton;

  @FindBy(how = How.XPATH, using = "//form/p[@class='infoMsg']")
  public WebElement getForgotPasswordInformationalMessage;

  @FindBy(how = How.CSS, using = "button.go-to-login-btn")
  public WebElement goToLoginButton;

  @FindBy(how = How.XPATH, using = "//div[@class='login-container']/h2")
  public WebElement successfulLoginHeading;

  @FindBy(how = How.XPATH, using = "//div[@class='login-container']/p")
  public WebElement successfulLoginMessage;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
