package com.example.advancedlocatorsidentification;

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

  private final String correctPassword = "rahulshettyacademy";

  private final String successfulLoginMessageText = "You are successfully logged in.";

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
  @DisplayName("fill out input user name and password an submit using the defined annotations")
  public void testFillOutInputUsernameAndPasswordAndSubmitUsingAnnotations() throws InterruptedException {
    // we are attempting to log in, but we do not know the correct password
    // so, we should get an error message
    mainPage.inputUserName.sendKeys(expectedUsername);
    mainPage.inputPassword.sendKeys(expectedPassword);

    assertEquals(expectedUsername, mainPage.inputUserName.getAttribute("value"));
    assertEquals(expectedPassword, mainPage.inputPassword.getAttribute("value"));

    mainPage.signInButton.click();

    WebDriverWait wait = new WebDriverWait(driver, duration);
    WebElement errorParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.errorParagraph));

    // error message for incorrect log in
    assertEquals("* Incorrect username or password", errorParagraph.getText());

    // select the forgot password link to navigate to the next view were
    // we can get a temporary password
    mainPage.forgotPasswordLink.click();

    // if we wanted to for a manual wait for 1 second we could use java: Thread.sleep(1000);
    // the units are in milliseconds, so the wait would be 1 second.  Obviously, explicit waits
    // with conditions are better, as shown below

    // these are the elements where the user enters the new name and email
    // the wait is an instance of WebDriverWait, so the duration of the wait
    // has already been defined
    WebElement resetPasswordName = wait.until(
        ExpectedConditions.visibilityOf(mainPage.forgotPasswordName)
    );

    WebElement resetPasswordEmail = wait.until(
        ExpectedConditions.visibilityOf(mainPage.forgotPasswordEmail)
    );

    // enter the new username and email
    resetPasswordName.sendKeys(expectedUsername);
    resetPasswordEmail.sendKeys(expectedEmail);

    // assert the values entered for the username and email have been
    // captured by the elements
    assertEquals(expectedUsername, resetPasswordName.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmail.getAttribute("value"));

    // clear the name and email fields
    resetPasswordName.clear();
    resetPasswordEmail.clear();

    // acquire the name and email using xpath array notation
    WebElement resetPasswordNameXpathArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameXpathArray)
    );

    WebElement resetPasswordEmailXpathArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailXpathArray)
    );

    // input values for the name and email
    resetPasswordNameXpathArray.sendKeys(expectedUsername);
    resetPasswordEmailXpathArray.sendKeys(expectedEmail);

    // assert the values entered for name and email are captured by the elements
    assertEquals(expectedUsername, resetPasswordNameXpathArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathArray.getAttribute("value"));

    // clear the name and email fields
    resetPasswordNameXpathArray.clear();
    resetPasswordEmailXpathArray.clear();

    // get name and email fields using css array technique
    WebElement resetPasswordNameCssArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameCssArray)
    );

    WebElement resetPasswordEmailCssArray = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailCssArray)
    );

    // fill in the name and email fields
    resetPasswordNameCssArray.sendKeys(expectedUsername);
    resetPasswordEmailCssArray.sendKeys(expectedEmail);

    // assert that the entered name and emails were captured by the elements
    assertEquals(expectedUsername, resetPasswordNameCssArray.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailCssArray.getAttribute("value"));

    // clear the fields
    resetPasswordNameCssArray.clear();
    resetPasswordEmailCssArray.clear();

    // get the forgot password h2 element
    WebElement resetPasswordH2 = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordH2));

    // assert on the text of the captured h2 element
    assertEquals(expectedH2Text, resetPasswordH2.getText());

    //  get the name, password, and phone number fields by using xpath tags
    WebElement resetPasswordNameXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordNameXpathTags)
    );

    WebElement resetPasswordEmailXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordEmailXpathTags)
    );

    WebElement resetPasswordPhoneNumberXpathTags = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordPhoneNumberXpathTags)
    );

    // fill in the name, password, and phone number fields
    resetPasswordNameXpathTags.sendKeys(expectedUsername);
    resetPasswordEmailXpathTags.sendKeys(expectedEmail);
    resetPasswordPhoneNumberXpathTags.sendKeys(expectedPhoneNumber);

    // assert on the name, password, and phone number fields
    assertEquals(expectedUsername, resetPasswordNameXpathTags.getAttribute("value"));
    assertEquals(expectedEmail, resetPasswordEmailXpathTags.getAttribute("value"));
    assertEquals(expectedPhoneNumber, resetPasswordPhoneNumberXpathTags.getAttribute("value"));

    // click the reset login button which will bring up an information message
    // contained in a <p>
    mainPage.resetLoginButton.click();

    // get the reset informational message paragraph
    WebElement resetPasswordInformationalMessage = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getForgotPasswordInformationalMessage)
    );

    // assert on the informational paragraph text
    assertEquals(expectedInfoMsgText, resetPasswordInformationalMessage.getText());

    // click the goto login button
    mainPage.goToLoginButton.click();

    // what we are going to try now is before filling out the username and password
    // fields and clicking the sign-in button we are going to wait for the left
    // overlay to disappear and the right overlay to appear
    // it turns out all I needed to do was to make sure the left overlay disappeared
    boolean leftOverlayVisible = true;
    while (leftOverlayVisible) {
      boolean leftOverlayNotVisible = wait.until(ExpectedConditions.invisibilityOf(mainPage.leftOverlay));
      if (leftOverlayNotVisible) leftOverlayVisible = false;
    }

    // get the username, password (by css regex), and sign in buttons (by xpath regex)
    inputForUserName = wait.until(ExpectedConditions.elementToBeClickable(mainPage.inputUserName));
    inputForPassword = wait.until(ExpectedConditions.elementToBeClickable(mainPage.getInputPasswordByCssRegex));

    // fill out the username and password fields
    inputForUserName.sendKeys(expectedUsername);
    inputForPassword.sendKeys(correctPassword);

    WebElement signInButtonXpathRegex = wait.until(
        ExpectedConditions.elementToBeClickable(mainPage.signInButtonXpathRegex)
    );

    // had to put in an explicit wait because Selenium was clicking the wrong element, error message shown below
    // org.openqa.selenium.ElementClickInterceptedException: element click intercepted: Element <button class="submit signInBtn" type="submit">...</button>
    // is not clickable at point (1128, 610). Other element would receive the click: <div class="overlay-panel overlay-right">...</div>
    //  the brute force way of ensuring the right overlay is out of the way is to insert a Thread.sleep(#ms)

    signInButtonXpathRegex.click();

    WebElement successfulLoginHeading = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successfulLoginHeading)
    );

    WebElement successfulLoginMessage = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successfulLoginMessage)
    );

    assertEquals("Hello EricRicketts,", successfulLoginHeading.getText());
    assertEquals(successfulLoginMessageText, successfulLoginMessage.getText());
  }
}
