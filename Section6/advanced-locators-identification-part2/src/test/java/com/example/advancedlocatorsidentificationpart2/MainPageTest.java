package com.example.advancedlocatorsidentificationpart2;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;

  private final String username = "EricRicketts";

  private final String correctPassword = "rahulshettyacademy";

  private WebDriverWait wait;

  private Duration duration;

  private void setDriverLocationAndDriverSystemProperty() {
    String windowsOSPattern = "Windows";
    String webDriversFolderPC = "C:\\Program Files\\WebDrivers\\";
    String chromeDriverWindows = "chromedriver.exe";

    String getWebDriversFolderMac = "/usr/local/bin/";
    String chromeDriverMac = "chromedriver";

    String chromeDriverProperty = "webdriver.chrome.driver";

    Pattern regex = Pattern.compile(windowsOSPattern);
    String os = System.getProperty("os.name");
    Matcher matchWindows = regex.matcher(os);

    if (matchWindows.find()) {
      System.setProperty(chromeDriverProperty, webDriversFolderPC + chromeDriverWindows);
    } else {
      System.setProperty(chromeDriverProperty, getWebDriversFolderMac + chromeDriverMac);
    }
  }

  @BeforeMethod
  public void setUp() {
    duration = Duration.ofSeconds(10);
    setDriverLocationAndDriverSystemProperty();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get("https://rahulshettyacademy.com/locatorspractice/");

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testLoginAndLogOut() {
    String successfulLoginText = "You are successfully logged in.";
    String usernamePlaceholder = "Username";
    String expectedLoginHeading = "Hello EricRicketts,";

    WebElement usernameInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.usernameInput)
    );

    WebElement passwordInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.passwordInput)
    );

    WebElement signInButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.signInButton)
    );

    usernameInput.sendKeys(username);
    passwordInput.sendKeys(correctPassword);

    Assert.assertEquals(usernameInput.getAttribute("value"), username);
    Assert.assertEquals(passwordInput.getAttribute("value"), correctPassword);

    signInButton.click();

    WebElement successfulLoginParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successfulLoginParagraph)
    );

    WebElement logoutButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.logoutButton)
    );

    WebElement loginHeading = wait.until(
        ExpectedConditions.visibilityOf(mainPage.loginHeading)
    );

    Assert.assertEquals(successfulLoginParagraph.getText(), successfulLoginText);
    Assert.assertEquals(loginHeading.getText(), expectedLoginHeading);

    logoutButton.click();

    Assert.assertTrue(usernameInput.isDisplayed());
    Assert.assertEquals(usernameInput.getAttribute("placeholder"), usernamePlaceholder);
  }

  @Test
  public void getPassword() {
    String informationMessageText = "Please use temporary password 'rahulshettyacademy' to Login.";
    String successfulLoginText = "You are successfully logged in.";
    String password;

    WebElement forgotPasswordLink = wait.until(
        ExpectedConditions.visibilityOf(mainPage.forgotPasswordLink)
    );

    forgotPasswordLink.click();

    WebElement resetPasswordButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.resetPasswordButton)
    );

    resetPasswordButton.click();

    WebElement informationMessage = wait.until(
        ExpectedConditions.visibilityOf(mainPage.informationMessage)
    );

    Assert.assertEquals(informationMessage.getText(), informationMessageText);

    String regexString = "(?<=')[a-z]+(?=')";
    Pattern pattern = Pattern.compile(regexString);
    Matcher matcher = pattern.matcher(informationMessage.getText());
    boolean matcherFound = matcher.find();
    if (matcherFound) {
      password = matcher.group();
    } else {
      password = "foobar";
    }

    mainPage.goToLoginButton.click();

    Assert.assertTrue(mainPage.passwordInput.isDisplayed());

    mainPage.usernameInput.sendKeys(username);
    mainPage.passwordInput.sendKeys(password);
    mainPage.signInButton.click();

    WebElement successfulLoginParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successfulLoginParagraph)
    );

    Assert.assertEquals(successfulLoginParagraph.getText(), successfulLoginText);
  }
}
