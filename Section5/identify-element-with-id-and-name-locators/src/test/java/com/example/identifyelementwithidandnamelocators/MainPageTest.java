package com.example.identifyelementwithidandnamelocators;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPageTest {

  private final String webDriversFolderPC = "C:\\Program Files\\WebDrivers\\";
  private final String chromeDriverWindows = "chromedriver.exe";
  private final String chromeDriverProperty = "webdriver.chrome.driver";

  private final String getWebDriversFolderMac = "/usr/local/bin/";
  private final String chromeDriverMac = "chromedriver";
  private final String url = "https://rahulshettyacademy.com/locatorspractice/";
  private WebDriver driver;
  private MainPage mainPage;

  private String expectedUsername, expectedEmail, expectedPassword,
      expectedH2Text, expectedPhoneNumber, expectedInfoMsgText;

  private WebElement inputForUserName, inputForPassword, signInButton;
  private WebElement forgotPasswordLink;
  private Duration duration;

  private void setDriverLocationAndDriverSystemProperty() {
    String windowsOSPattern = "Windows";
    Pattern regex = Pattern.compile(windowsOSPattern);
    String os = System.getProperty("os.name");
    Matcher matchWindows = regex.matcher(os);

    if (matchWindows.find()) {
      System.setProperty(chromeDriverProperty, webDriversFolderPC + chromeDriverWindows);
    } else {
      System.setProperty(chromeDriverProperty, getWebDriversFolderMac + chromeDriverMac);
    }
  }
  @BeforeEach
  public void setUp() {
    duration = Duration.ofSeconds(10);
    // if the drivers are located in system PATH variable this method call is not needed
    // as any system will search the PATH variable locations in the search for a webdriver
    setDriverLocationAndDriverSystemProperty();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    /*
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      It is best not to use implicit waits, explicit waits are more specific to a given situation
      and better defined in general.  But the above code is provided as an example on how to code
      implicit waits
     */
    driver.get(url);

    // just in case you return to the code and have not looked at Java in a while
    // the MainPage and MainPageTest classes are in the same package so there is no
    // need for an import of MainPage
    mainPage = new MainPage(driver);
    expectedUsername = "EricRicketts";
    expectedEmail = "eric_ricketts@icloud.com";
    expectedPassword = "foo123bar@#$";
    expectedH2Text = "Forgot password";
    expectedPhoneNumber = "919-449-5529";
    expectedInfoMsgText = "Please use temporary password \'rahulshettyacademy\' to Login.";
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  /*
    Note unique selectors are the preferred choice for locating an element, used arrayed selectors
    as a last resort.  They are shown here for instructional purposes.
  */
  @Test
  @DisplayName("fill out input user name and password and submit using Selenium locators only")
  public void testFillOutUsernameAndPasswordAndSubmitWithLocators() {
    // In this method
    inputForUserName = driver.findElement(By.id("inputUsername"));
    inputForPassword = driver.findElement(By.name("inputPassword"));
    signInButton = driver.findElement(By.cssSelector("button.submit.signInBtn"));
    forgotPasswordLink = driver.findElement(By.linkText("Forgot your password?"));

    // now that we have the web elements 
    inputForUserName.sendKeys(expectedUsername);
    inputForPassword.sendKeys(expectedPassword);

    assertEquals(expectedUsername, inputForUserName.getAttribute("value"));
    assertEquals(expectedPassword, inputForPassword.getAttribute("value"));

    signInButton.click();
    // In the Udemy video in Section 5 (the section which introduces locators) an implicit wait is used
    // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds((10)));  in doing some research on waits
    // in Selenium the advice was not to use implicit waits, but explicit waits which I have done below
    // hopefully I can add a comment at a later date and explain why this is a better practice.  My initial
    // impression is the wait can be driver locator specific with an explicit reference while implicit waits
    // are global and may not be appropriate for each situation.
    WebDriverWait wait = new WebDriverWait(driver, duration);
    WebElement errorParagraph = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.error"))
    );

    assertEquals("* Incorrect username or password", errorParagraph.getText());

    forgotPasswordLink.click();

    WebElement resetPasswordName = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Name']"))
    );

    WebElement resetPasswordEmail = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='Email']"))
    );

    resetPasswordName.sendKeys(expectedUsername);
    resetPasswordEmail.sendKeys(expectedEmail);

    assertEquals(expectedUsername, resetPasswordName.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmail.getAttribute("value"));

    resetPasswordName.clear();
    resetPasswordEmail.clear();

    WebElement resetPasswordNameXpathArray = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text'][1]"))
    );

    WebElement resetPasswordEmailXpathArray = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text'][2]"))
    );

    resetPasswordNameXpathArray.sendKeys(expectedUsername);
    resetPasswordEmailXpathArray.sendKeys(expectedEmail);

    assertEquals(expectedUsername, resetPasswordNameXpathArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathArray.getAttribute("value"));

    resetPasswordNameXpathArray.clear();
    resetPasswordEmailXpathArray.clear();

    WebElement resetPasswordNameCssArray = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='text']:nth-child(2)"))
    );

    WebElement resetPasswordEmailCssArray = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='text']:nth-child(3)"))
    );

    resetPasswordNameCssArray.sendKeys(expectedUsername);
    resetPasswordEmailCssArray.sendKeys(expectedEmail);

    assertEquals(expectedUsername, resetPasswordNameCssArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailCssArray.getAttribute("value"));

    resetPasswordNameCssArray.clear();
    resetPasswordEmailCssArray.clear();

    WebElement resetPasswordH2 = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("form > h2"))
    );

    assertEquals(expectedH2Text, resetPasswordH2.getText());

    WebElement resetPasswordNameXpathTags = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("form > input:nth-child(2)"))
    );

    WebElement resetPasswordEmailXpathTags = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("form > input:nth-child(3)"))
    );

    WebElement resetPasswordPhoneNumberXpathTags = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("form > input:nth-child(4)"))
    );

    resetPasswordNameXpathTags.sendKeys(expectedUsername);
    resetPasswordEmailXpathTags.sendKeys(expectedEmail);
    resetPasswordPhoneNumberXpathTags.sendKeys(expectedPhoneNumber);

    assertEquals(expectedUsername, resetPasswordNameXpathTags.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathTags.getAttribute("value"));
    assertEquals(expectedPhoneNumber, resetPasswordPhoneNumberXpathTags.getAttribute("value"));

    driver.findElement(By.cssSelector("button.reset-pwd-btn")).click();

    WebElement resetPasswordInformationalMessage = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.infoMsg"))
    );

    assertEquals(expectedInfoMsgText, resetPasswordInformationalMessage.getText());
  }

  // the goal of this test method is to exercise the sign in options of the form and rahul shetty academy's
  // selenium practice page.  This is the same goal as the preceding test method the difference begin the MainPage
  // locators are used which take the form of object attributes.
  @Test
  @DisplayName("fill out input user name and password an submit using the defined annotations")
  public void testFillOutInputUsernameAndPasswordAndSubmitUsingAnnotations() {
    // Selenium provides the sendKeys methods to enter content into editable text fields or password fields

    // locate the username and password fields and then enter content in each of those fields
    mainPage.inputUserName.sendKeys(expectedUsername);
    mainPage.inputPassword.sendKeys(expectedPassword);

    // assert that the values entered are captured by the elements
    assertEquals(expectedUsername, mainPage.inputUserName.getAttribute("value"));
    assertEquals(expectedPassword, mainPage.inputPassword.getAttribute("value"));

    // click the Sign-In button
    mainPage.signInButton.click();

    // instantiate a WebDriverWait instance with the duration instance defined in the setUp method
    // this is an explicit duration forcing the WebDriverWait to wait for that amount of time
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // after Selenium clicks the Sign-in button wait for the error to appear
    // along with the reset password and email text fields
    WebElement errorParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.errorParagraph));

    WebElement resetPasswordName = wait.until(
        ExpectedConditions.visibilityOf(mainPage.forgotPasswordName)
    );

    WebElement resetPasswordEmail = wait.until(
        ExpectedConditions.visibilityOf(mainPage.forgotPasswordEmail)
    );

    //  Enter content in the reset password and email fields, assert
    resetPasswordName.sendKeys(expectedUsername);
    resetPasswordEmail.sendKeys(expectedEmail);

    assertEquals(expectedUsername, resetPasswordName.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmail.getAttribute("value"));

    resetPasswordName.clear();
    resetPasswordEmail.clear();

    WebElement resetPasswordNameXpathArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameXpathArray)
    );

    WebElement resetPasswordEmailXpathArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailXpathArray)
    );

    resetPasswordNameXpathArray.sendKeys(expectedUsername);
    resetPasswordEmailXpathArray.sendKeys(expectedEmail);

    assertEquals(expectedUsername, resetPasswordNameXpathArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathArray.getAttribute("value"));

    resetPasswordNameXpathArray.clear();
    resetPasswordEmailXpathArray.clear();

    WebElement resetPasswordNameCssArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameCssArray)
    );

    WebElement resetPasswordEmailCssArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailCssArray)
    );

    resetPasswordNameCssArray.sendKeys(expectedUsername);
    resetPasswordEmailCssArray.sendKeys(expectedEmail);

    assertEquals(expectedUsername, resetPasswordNameCssArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailCssArray.getAttribute("value"));

    resetPasswordNameCssArray.clear();
    resetPasswordEmailCssArray.clear();

    WebElement resetPasswordH2 = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordH2));

    assertEquals(expectedH2Text, resetPasswordH2.getText());

    WebElement resetPasswordNameXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameXpathTags)
    );

    WebElement resetPasswordEmailXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailPathTags)
    );

    WebElement resetPasswordPhoneNumberXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordPhoneNumberXpathTags)
    );

    resetPasswordNameXpathTags.sendKeys(expectedUsername);
    resetPasswordEmailXpathTags.sendKeys(expectedEmail);
    resetPasswordPhoneNumberXpathTags.sendKeys(expectedPhoneNumber);

    assertEquals(expectedUsername, resetPasswordNameXpathTags.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathTags.getAttribute("value"));
    assertEquals(expectedPhoneNumber, resetPasswordPhoneNumberXpathTags.getAttribute("value"));

    mainPage.resetLoginButton.click();

    WebElement resetPasswordInformationalMessage = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordInformationalMessage)
    );

    assertEquals(expectedInfoMsgText, resetPasswordInformationalMessage.getText());
  }
}
