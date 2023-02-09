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

  private String extractPasswordUsingSplit(WebDriver driver) {
    Pattern pattern = Pattern.compile("'([\\w]+)'");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement forgotPasswordLink = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.linkText("Forgot your password?"))
    );
    forgotPasswordLink.click();

    WebElement resetLoginButton = wait.until(
        ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("button.reset-pwd-btn")))
    );

    resetLoginButton.click();

    WebElement informationalMessage = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.infoMsg"))
    );

    String passwordText = informationalMessage.getText();
    Matcher matcher = pattern.matcher(passwordText);

    String[] passwordTextArrayOfWords = passwordText.split("\'");
    String passwordFromRegex = "";
    if (matcher.find()) passwordFromRegex = matcher.group(1);

    String passwordFromSplit = passwordTextArrayOfWords[1];
    if (passwordFromRegex.equals(passwordFromSplit)) {
      return passwordFromRegex;
    } else {
      return passwordFromSplit;
    }
  }
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

  @Test(enabled = false)
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

  @Test(enabled = false)
  public void testGetPassword() throws InterruptedException {
    boolean rightOverlayInvisible = true;
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

    while (rightOverlayInvisible) {
      WebElement rightOverlay = wait.until(ExpectedConditions.elementToBeClickable(mainPage.rightOverlayPanel));
      boolean rightOverlayVisible = !(rightOverlay == null);
      if (rightOverlayVisible) rightOverlayInvisible = false;
    }

    mainPage.usernameInput.sendKeys(username);
    mainPage.passwordInput.sendKeys(password);

    // though I do not like to do this I had to put the delay in
//    Thread.sleep(1_000);
    mainPage.signInButton.click();

    WebElement successfulLoginParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successfulLoginParagraph)
    );

    Assert.assertEquals(successfulLoginParagraph.getText(), successfulLoginText);
  }

  @Test(enabled = false)
  public void testGetPasswordMethod() throws InterruptedException {
    String usernamePlaceholder = "Username";
    String expectedLoginHeading = "Hello EricRicketts,";
    String successfulLoginText = "You are successfully logged in.";
    boolean rightOverlayInvisible = true;
    String password = extractPasswordUsingSplit(driver);

    WebElement goToLoginButton = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.go-to-login-btn"))
    );

    goToLoginButton.click();

    WebElement usernameInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.usernameInput)
    );

    WebElement passwordInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.passwordInput)
    );

    WebElement signInButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.signInButton)
    );

    // I was trying techniques where I would not have to put in explicit sleeps, in this case I attempt
    while (rightOverlayInvisible) {
      WebElement rightOverlay = wait.until(ExpectedConditions.elementToBeClickable(mainPage.rightOverlayPanel));
      boolean rightOverlayVisible = !(rightOverlay == null);
      if (rightOverlayVisible) rightOverlayInvisible = false;
    }
    usernameInput.sendKeys(username);
    passwordInput.sendKeys(password);

    // as with the testGetPassword method I had to add in a delay
    // it would be unambiguous which element would be selected
    // Thread.sleep(1000);
    signInButton.click();

    WebElement informationalMessage = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.login-container > p"))
    );

    Assert.assertEquals(informationalMessage.getText(), successfulLoginText);

    WebElement successfulLoginParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successfulLoginParagraph)
    );

    WebElement logoutButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.getLogoutButton)
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
}
