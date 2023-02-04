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
    // In this method, we are going to use the selectors as demonstrated in the video
    // just for extra practice.  In future tests more than likely I will be using the @FindBy
    // annotation as this makes the code more self-explanatory and more compact

    // we have the id, name, cssSelector, and linkText locators working here
    // to find the elements
    inputForUserName = driver.findElement(By.id("inputUsername"));
    inputForPassword = driver.findElement(By.name("inputPassword"));
    signInButton = driver.findElement(By.cssSelector("button.submit.signInBtn"));
    forgotPasswordLink = driver.findElement(By.linkText("Forgot your password?"));

    // now that we have the web elements we fill out the input fields
    inputForUserName.sendKeys(expectedUsername);
    inputForPassword.sendKeys(expectedPassword);

    // assert the name and password elements have captured the entries
    assertEquals(expectedUsername, inputForUserName.getAttribute("value"));
    assertEquals(expectedPassword, inputForPassword.getAttribute("value"));

    // click the sign-in button
    signInButton.click();
    // In the Udemy video in Section 5 (the section which introduces locators) an implicit wait is used
    // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds((10)));  in doing some research on waits
    // in Selenium the advice was not to use implicit waits, but explicit waits which I have done below
    // hopefully I can add a comment at a later date and explain why this is a better practice.  My initial
    // impression is the wait can be driver locator specific with an explicit reference while implicit waits
    // are global and may not be appropriate for each situation.
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // after a wait period the error paragraph should display
    WebElement errorParagraph = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.error"))
    );

    // assert on the text content of the error paragraph
    assertEquals("* Incorrect username or password", errorParagraph.getText());

    // now that we realize we do not have the correct password, click on the
    // forgot password link
    forgotPasswordLink.click();

    // transition to the other screen and find the name element, remember the wait object
    // is an instance of the WebDriverWait class, use xpath and placeholder attribute
    WebElement resetPasswordName = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Name']"))
    );

    // wait until the email input present then capture the element
    // use css placeholder attribute
    WebElement resetPasswordEmail = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='Email']"))
    );

    // enter name and email
    resetPasswordName.sendKeys(expectedUsername);
    resetPasswordEmail.sendKeys(expectedEmail);

    // assert on name and email elements that they have captured the entered values
    assertEquals(expectedUsername, resetPasswordName.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmail.getAttribute("value"));

    // clear name and email
    resetPasswordName.clear();
    resetPasswordEmail.clear();

    // select name and email elements using xpath array notation
    WebElement resetPasswordNameXpathArray = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text'][1]"))
    );

    WebElement resetPasswordEmailXpathArray = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text'][2]"))
    );

    // enter data for the name and email elements
    resetPasswordNameXpathArray.sendKeys(expectedUsername);
    resetPasswordEmailXpathArray.sendKeys(expectedEmail);

    // assert on the values captured by the name and email elements
    assertEquals(expectedUsername, resetPasswordNameXpathArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathArray.getAttribute("value"));

    // clear the name and email fields
    resetPasswordNameXpathArray.clear();
    resetPasswordEmailXpathArray.clear();

    // capture the name and email fields using nth-child css selectors
    WebElement resetPasswordNameCssArray = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='text']:nth-child(2)"))
    );

    WebElement resetPasswordEmailCssArray = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='text']:nth-child(3)"))
    );

    // enter the text into each of the elements
    resetPasswordNameCssArray.sendKeys(expectedUsername);
    resetPasswordEmailCssArray.sendKeys(expectedEmail);

    // assert on the entered text for each of the name and email elements
    assertEquals(expectedUsername, resetPasswordNameCssArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailCssArray.getAttribute("value"));


    // clear the name and email elements
    resetPasswordNameCssArray.clear();
    resetPasswordEmailCssArray.clear();

    // select the h2 paragraph which has the Forgot password text
    WebElement resetPasswordH2 = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("form > h2"))
    );

    // assert on the text of the h2 element
    assertEquals(expectedH2Text, resetPasswordH2.getText());

    // though labeled xpath tags these locators are css hierarchical tags for name, password
    // and phone number
    WebElement resetPasswordNameXpathTags = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("form > input:nth-child(2)"))
    );

    WebElement resetPasswordEmailXpathTags = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("form > input:nth-child(3)"))
    );

    WebElement resetPasswordPhoneNumberXpathTags = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("form > input:nth-child(4)"))
    );

    // fill out the name, password, and phone number fields
    resetPasswordNameXpathTags.sendKeys(expectedUsername);
    resetPasswordEmailXpathTags.sendKeys(expectedEmail);
    resetPasswordPhoneNumberXpathTags.sendKeys(expectedPhoneNumber);

    // assert the values captured by the name, email, and phone number fields
    assertEquals(expectedUsername, resetPasswordNameXpathTags.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathTags.getAttribute("value"));
    assertEquals(expectedPhoneNumber, resetPasswordPhoneNumberXpathTags.getAttribute("value"));

    // click the password reset button
    driver.findElement(By.cssSelector("button.reset-pwd-btn")).click();

    // find the informational paragraph on the new password
    WebElement resetPasswordInformationalMessage = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.infoMsg"))
    );

    // assert on the text of the informational message
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

    // assert that the entered text was captured by the name and email elements
    assertEquals(expectedUsername, resetPasswordName.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmail.getAttribute("value"));

    // clear the name and email fields
    resetPasswordName.clear();
    resetPasswordEmail.clear();

    // select the name and email elements again by xpath array
    WebElement resetPasswordNameXpathArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameXpathArray)
    );

    WebElement resetPasswordEmailXpathArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailXpathArray)
    );

    // populate the name and email fields
    resetPasswordNameXpathArray.sendKeys(expectedUsername);
    resetPasswordEmailXpathArray.sendKeys(expectedEmail);

    // assert on the content of the name and email fields
    assertEquals(expectedUsername, resetPasswordNameXpathArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathArray.getAttribute("value"));

    // clear the name and email fields
    resetPasswordNameXpathArray.clear();
    resetPasswordEmailXpathArray.clear();

    // select the name and email fields by css array
    WebElement resetPasswordNameCssArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameCssArray)
    );

    WebElement resetPasswordEmailCssArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailCssArray)
    );

    // populate the name and email fields
    resetPasswordNameCssArray.sendKeys(expectedUsername);
    resetPasswordEmailCssArray.sendKeys(expectedEmail);

    // assert on the text content of the name and email fields
    assertEquals(expectedUsername, resetPasswordNameCssArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailCssArray.getAttribute("value"));

    // clear the fields
    resetPasswordNameCssArray.clear();
    resetPasswordEmailCssArray.clear();

    // select the h2 element which contains the Forgot password title
    WebElement resetPasswordH2 = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordH2));

    // assert on h2 title text content
    assertEquals(expectedH2Text, resetPasswordH2.getText());

    // using xpath tags select the name, email, and phone number elements
    WebElement resetPasswordNameXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameXpathTags)
    );

    WebElement resetPasswordEmailXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailPathTags)
    );

    WebElement resetPasswordPhoneNumberXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordPhoneNumberXpathTags)
    );

    // fill out the name, email, and phone number elements
    resetPasswordNameXpathTags.sendKeys(expectedUsername);
    resetPasswordEmailXpathTags.sendKeys(expectedEmail);
    resetPasswordPhoneNumberXpathTags.sendKeys(expectedPhoneNumber);

    // assert on the content of the name, email, and phone number elements
    assertEquals(expectedUsername, resetPasswordNameXpathTags.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathTags.getAttribute("value"));
    assertEquals(expectedPhoneNumber, resetPasswordPhoneNumberXpathTags.getAttribute("value"));

    // click the rest login button
    mainPage.resetLoginButton.click();

    // select the paragraph which contains the informational message about the password reset
    WebElement resetPasswordInformationalMessage = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordInformationalMessage)
    );

    // assert on the paragraph text content which contains the information message
    // about the password reset
    assertEquals(expectedInfoMsgText, resetPasswordInformationalMessage.getText());
  }
}
