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
  @DisplayName("fill out input user name and password and submit using Selenium locators only")
  public void testFillOutUsernameAndPasswordAndSubmitWithLocators() {
    inputForUserName = driver.findElement(By.id("inputUsername"));
    inputForPassword = driver.findElement(By.name("inputPassword"));
    signInButton = driver.findElement(By.cssSelector("button.submit.signInBtn"));
    forgotPasswordLink = driver.findElement(By.linkText("Forgot your password?"));


    inputForUserName.sendKeys(expectedUsername);
    inputForPassword.sendKeys(expectedPassword);

    assertEquals(expectedUsername, inputForUserName.getAttribute("value"));
    assertEquals(expectedPassword, inputForPassword.getAttribute("value"));

    signInButton.click();

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

  @Test
  @DisplayName("fill out input user name and password an submit using the defined annotations")
  public void testFillOutInputUsernameAndPasswordAndSubmitUsingAnnotations() {
    inputForUserName = mainPage.inputUserName;
    inputForPassword = mainPage.inputPassword;
    signInButton = mainPage.signInButton;
    forgotPasswordLink = mainPage.forgotPasswordLink;

    inputForUserName.sendKeys(expectedUsername);
    inputForPassword.sendKeys(expectedPassword);

    assertEquals(expectedUsername, inputForUserName.getAttribute("value"));
    assertEquals(expectedPassword, inputForPassword.getAttribute("value"));

    signInButton.click();

    WebDriverWait wait = new WebDriverWait(driver, duration);
    WebElement errorParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.errorParagraph));

    assertEquals("* Incorrect username or password", errorParagraph.getText());

    forgotPasswordLink.click();

    WebElement resetPasswordName = wait.until(
        ExpectedConditions.visibilityOf(mainPage.forgotPasswordName)
    );

    WebElement resetPasswordEmail = wait.until(
        ExpectedConditions.visibilityOf(mainPage.forgotPasswordEmail)
    );

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
