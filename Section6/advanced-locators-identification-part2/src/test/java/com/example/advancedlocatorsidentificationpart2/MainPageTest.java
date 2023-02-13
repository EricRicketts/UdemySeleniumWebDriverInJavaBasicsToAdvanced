package com.example.advancedlocatorsidentificationpart2;

import org.example.SetWebDriverLocation;
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

import org.example.SetWebDriverLocation.*;

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

  @BeforeMethod
  public void setUp() {
    duration = Duration.ofSeconds(10);
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
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
    // I will be introducing comments for testLoginAndLogOut method
    String successfulLoginText = "You are successfully logged in.";
    String usernamePlaceholder = "Username";
    String expectedLoginHeading = "Hello EricRicketts,";

    // the page should already be loaded, but just in case I wait for the
    // username and password inputs to be visible, along with the sign-in button
    WebElement usernameInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.usernameInput)
    );

    WebElement passwordInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.passwordInput)
    );

    WebElement signInButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.signInButton)
    );

    // fill out the username and password fields, the password with the known correct password
    usernameInput.sendKeys(username);
    passwordInput.sendKeys(correctPassword);

    // verify username and correct password entry
    Assert.assertEquals(usernameInput.getAttribute("value"), username);
    Assert.assertEquals(passwordInput.getAttribute("value"), correctPassword);

    // click the sign-in button since the correct password was entered, this should take us
    // to the successful login screen
    signInButton.click();

    // wait for the right overlay to be gone, since it is the visible overlay
    boolean rightOverlayGone = wait.until(ExpectedConditions.invisibilityOf(mainPage.rightOverlayPanel));

    // get the successful login paragraph, the logout button, and the login heading
    WebElement successfulLoginParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successfulLoginParagraph)
    );

    WebElement logoutButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.logoutButton)
    );

    WebElement loginHeading = wait.until(
        ExpectedConditions.visibilityOf(mainPage.loginHeading)
    );

    if (rightOverlayGone) {
      Assert.assertEquals(successfulLoginParagraph.getText(), successfulLoginText);
      Assert.assertEquals(loginHeading.getText(), expectedLoginHeading);
    }

    logoutButton.click();

    // wait for the logout button to disappear before making assertions
    boolean logoutButtonGone = wait.until(ExpectedConditions.invisibilityOf(logoutButton));

    if (logoutButtonGone) {
      Assert.assertTrue(usernameInput.isDisplayed());
      Assert.assertEquals(usernamePlaceholder, usernameInput.getAttribute("placeholder"));
    }
  }

  @Test
  public void testGetPassword() throws InterruptedException {
    // in this method we get the temporary password and use this as a precursor
    // to developing a method to extract the temporary password from the informational message
    boolean rightOverlayInvisible = true;
    String informationMessageText = "Please use temporary password 'rahulshettyacademy' to Login.";
    String successfulLoginText = "You are successfully logged in.";
    String password;

    // get the forgot password link, click it and wait for the right overlay to clear
    WebElement forgotPasswordLink = wait.until(
        ExpectedConditions.visibilityOf(mainPage.forgotPasswordLink)
    );

    forgotPasswordLink.click();

    boolean rightOverlayGone = wait.until(ExpectedConditions.invisibilityOf(mainPage.rightOverlayPanel));

    // once the right overlay is gone, click the rest password button
    // this will display the information paragraph which contains the temporary password
    WebElement resetPasswordButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.resetPasswordButton)
    );

    if (rightOverlayGone) resetPasswordButton.click();

    // get the informational message paragraph
    WebElement informationMessage = wait.until(
        ExpectedConditions.visibilityOf(mainPage.informationMessage)
    );

    // check the text in the paragraph is what we expect
    Assert.assertEquals(informationMessage.getText(), informationMessageText);

    // look behind the characters [a-z] to find a ' and look ahead of the characters [a-z] to find a '
    // if we get a match, the password becomes the matched group of characters
    String regexString = "(?<=')[a-z]+(?=')";
    Pattern pattern = Pattern.compile(regexString);
    Matcher matcher = pattern.matcher(informationMessage.getText());
    boolean matcherFound = matcher.find();
    if (matcherFound) {
      password = matcher.group();
    } else {
      password = "foobar";
    }

    // go back to the login page
    mainPage.goToLoginButton.click();

    // wait for the left overlay to disappear
    boolean leftOverlayGone = wait.until(ExpectedConditions.invisibilityOf(mainPage.leftOverlayPanel));

    // if the left overlay is no longer visible then the username and password inputs should be visible
    if (leftOverlayGone) Assert.assertTrue(mainPage.passwordInput.isDisplayed());

    // now that we have the correct password sign in by entering the username and correct password
    mainPage.usernameInput.sendKeys(username);
    mainPage.passwordInput.sendKeys(password);

    // verify the elements hold the values before signing in
    Assert.assertEquals(mainPage.usernameInput.getAttribute("value"), username);
    Assert.assertEquals(mainPage.passwordInput.getAttribute("value"), password);

    mainPage.signInButton.click();

    rightOverlayGone = wait.until(
      ExpectedConditions.invisibilityOf(mainPage.rightOverlayPanel)
    );

    WebElement successfulLoginParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successfulLoginParagraph)
    );

    if (rightOverlayGone) Assert.assertEquals(successfulLoginParagraph.getText(), successfulLoginText);
  }

  @Test
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
