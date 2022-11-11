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
  public WebElement getForgotPasswordEmailPathTags;

  @FindBy(how = How.XPATH, using = "//form/input[3]")
  public WebElement getForgotPasswordPhoneNumberXpathTags;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
